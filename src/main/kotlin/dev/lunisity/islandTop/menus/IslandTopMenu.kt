package dev.lunisity.islandTop.menus

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI
import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.data.TrophyType
import dev.lunisity.islandTop.api.utils.MathUtil
import dev.lunisity.novaconfig.builder.ItemBuilder
import dev.lunisity.novamenu.abstracts.AbstractMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class IslandTopMenu(val player: Player) : AbstractMenu(IslandTop.instance, IslandTop.menuConfig, "IslandTop") {

    init {
        setPageItems()
    }

    fun setPageItems() {
        val top5 = IslandTop.topManager.getTop5Island()

        if (top5.isEmpty()) {
            return
        }

        var slot = 11
        var index = 1

        val textReplacer = TextReplacer()

        for (entry in top5) {
            val target = Bukkit.getPlayer(entry.key.owner.uniqueId) ?: Bukkit.getOfflinePlayer(entry.key.owner.uniqueId)

            if (!IslandTop.strikeManager.hasStrikes(entry.key)) {
                textReplacer.with("%strikes%", " ")
            } else {
                val strikes = IslandTop.strikeManager.getCurrentPercentage(entry.key)
                textReplacer.with("%strikes%", "&c(-$strikes%)")
            }

            textReplacer
                .with("%total-value%", MathUtil.formatNumber(IslandTop.strikeManager.calculate(entry.key)))
                .with("%crops-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.CROP)))
                .with("%fish-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.FISH)))
                .with("%mob-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.MOB)))
                .with("%ores-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.ORE)))

            val builder: StringBuilder = StringBuilder()

            val members = IslandTop.islandManager.getMembers(entry.key)
            if (members.isEmpty()) {
                builder.append("No members found for island ${entry.key.name}")
            } else {
                for (member in members) {
                    val memberPlayer = Bukkit.getPlayer(member) ?: Bukkit.getOfflinePlayer(member)
                    val rank = SuperiorSkyblockAPI.getPlayer(member).playerRole.name
                    builder.append("&8- &f${memberPlayer.name} &7($rank)")
                }
            }

            textReplacer.with("%members%", builder.toString())

            var crops = IslandTop.islandManager.getTotalStats(entry.key, TrackedType.CROP)
            if (crops == 0L) {
                crops = 0
            }

            var ores = IslandTop.islandManager.getTotalStats(entry.key, TrackedType.ORE)
            if (ores == 0L) {
                ores = 0
            }

            var fish = IslandTop.islandManager.getTotalStats(entry.key, TrackedType.FISH)
            if (fish == 0L) {
                fish = 0
            }

            var mobs = IslandTop.islandManager.getTotalStats(entry.key, TrackedType.MOB)
            if (mobs == 0L) {
                mobs = 0
            }

            textReplacer.with("%crops%", MathUtil.formatNumber(crops))
                .with("%ores%", MathUtil.formatNumber(ores))
                .with("%fish%", MathUtil.formatNumber(fish))
                .with("%mobs%", MathUtil.formatNumber(mobs))

            val itemLore = IslandTop.menuConfig.getStringList("IslandTop/Content/Lore")
            val name = IslandTop.menuConfig.getString("IslandTop/Content/Name")
            val replacer = TextReplacer().with("%island%", entry.key.name).with("%position%", index)

            val item = ItemBuilder.of(Material.PLAYER_HEAD).setOwner(target.name)
                .setName(replacer.apply(name))
                .setLore(textReplacer.apply(itemLore))
                .itemStack

            setItem(slot, item) { e ->
                e.isCancelled = true

                val value = IslandTop.strikeManager.calculate(entry.key)
                val preStrike = IslandTop.trophyManager.getTotalTrophies(entry.key)

                val summaryReplacer = TextReplacer()
                    .with("%island%", entry.key.name)
                    .with("%total%", MathUtil.formatNumber(value))
                    .with("%pre-strike%", MathUtil.formatNumber(preStrike))
                    .with("%crop-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.CROP)))
                    .with("%ore-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.ORE)))
                    .with("%fish-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.FISH)))
                    .with("%mob-trophies%", MathUtil.formatNumber(IslandTop.trophyManager.getTrophies(entry.key, TrophyType.MOB)))

                IslandTop.messageCache.sendMessage(player, "TROPHY-SUMMARY", summaryReplacer)
            }
            slot++
            index++
        }

    }

}