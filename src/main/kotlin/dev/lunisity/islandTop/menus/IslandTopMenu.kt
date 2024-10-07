package dev.lunisity.islandTop.menus

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI
import dev.lunisity.borealis.text.color.ColorAPI
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.utils.MathUtil
import dev.lunisity.islandTop.manager.island.IslandStatManager
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
        for (entry in top5) {
            val target = Bukkit.getPlayer(entry.key.owner.uniqueId) ?: Bukkit.getOfflinePlayer(entry.key.owner.uniqueId)

            val lore = mutableListOf(
                "&a&lTotal Trophies&r&a: ",
                "&f${entry.value}",
                " ",
                "&a&lMembers&r&a: ",
            )

            val members = IslandTop.islandManager.getMembers(entry.key)
            if (members.isEmpty()) {
                player.sendMessage("No members found for island ${entry.key.name}")
            } else {
                for (member in members) {
                    val memberPlayer = Bukkit.getPlayer(member) ?: Bukkit.getOfflinePlayer(member)
                    val rank = SuperiorSkyblockAPI.getPlayer(member).playerRole.name
                    player.sendMessage(ColorAPI.apply("&7Adding member: &f${memberPlayer.name} &7with rank: &f$rank"))

                    lore.add("&8- &f${memberPlayer.name} &7($rank)")
                }
            }

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

            var logs = IslandTop.islandManager.getTotalStats(entry.key, TrackedType.LOG)
            if (logs == 0L) {
                logs = 0
            }

            lore.add(" ")
            lore.add("&a&lStats&r&a: ")
            lore.add("&8- &a&lCrops&r&a: &f${MathUtil.formatNumber(crops)}")
            lore.add("&8- &d&lOres&r&d: &f${MathUtil.formatNumber(ores)}")
            lore.add("&8- &b&lFish&r&b: &f${MathUtil.formatNumber(fish)}")
            lore.add("&8- &e&lLogs&r&e: &f${MathUtil.formatNumber(logs)}")

            val item = ItemBuilder.of(Material.PLAYER_HEAD).setOwner(target.name)
                .setName("&2&lIsland &a${entry.key.name} &7(#$index)")
                .setLore(lore)
                .itemStack

            setItem(slot, item) { e ->
                e.isCancelled = true

                val stat = IslandTop.islandManager.getTotalStats(entry.key)

                player.sendMessage(ColorAPI.apply("&a${entry.key.name} &7has &a${MathUtil.formatNumber(stat)} &7total value!"))
            }
            slot++
            index++
        }
    }

}