package dev.lunisity.islandTop.menus

import com.bgsoftware.superiorskyblock.api.island.Island
import dev.lunisity.borealis.text.color.ColorAPI
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.utils.LogUtil
import dev.lunisity.novaconfig.builder.ItemBuilder
import dev.lunisity.novamenu.abstracts.AbstractMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.Map.entry

class CropTopMenu(val player: Player) : AbstractMenu(IslandTop.instance, IslandTop.menuConfig, "CropTop") {

    init {
        setPageItems()
    }

    fun setPageItems() {
        val top5 = IslandTop.topManager.getTop5Crop()

        if (top5.isEmpty()) {
            return
        }

        var slot = 11
        var index = 1
        for (entry in top5) {
            val target = Bukkit.getPlayer(entry.key) ?: Bukkit.getOfflinePlayer(entry.key)

            val item = ItemBuilder.of(Material.PLAYER_HEAD).setOwner(target.name)
                .setName("&2&l#$index &a${target.name}")
                .setLore(listOf("&a${entry.value} &7Crops")).itemStack

            setItem(slot, item) { e ->
                e.isCancelled = true

                val stat = IslandTop.playerManager.getStat(target as Player, TrackedType.CROP)

                player.sendMessage(ColorAPI.apply("&a${target.name} &7has &a$stat &7crops!"))
            }
            slot++
            index++
        }
    }


}