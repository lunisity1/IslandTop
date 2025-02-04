package dev.lunisity.islandTop.menus

import dev.lunisity.borealis.text.color.ColorAPI
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.novaconfig.builder.ItemBuilder
import dev.lunisity.novamenu.abstracts.AbstractMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class OreTopMenu(val player: Player) : AbstractMenu(IslandTop.instance, IslandTop.menuConfig, "OreTop") {

    init {
        setPageItems()
    }

    fun setPageItems() {
        val top5 = IslandTop.topManager.getTop5Ore()

        if (top5.isEmpty()) {
            return
        }

        var slot = 11
        var index = 1
        for (entry in top5) {
            val target = Bukkit.getPlayer(entry.key) ?: Bukkit.getOfflinePlayer(entry.key)

            val item = ItemBuilder.of(Material.PLAYER_HEAD).setOwner(target.name)
                .setName("&5&l#$index &d${target.name}")
                .setLore(listOf("&d${entry.value} &7Ores")).itemStack

            setItem(slot, item) { e ->
                e.isCancelled = true

                val stat = IslandTop.playerManager.getStat(target as Player, TrackedType.ORE)

                player.sendMessage(ColorAPI.apply("&a${target.name} &7has &a$stat &7ores!"))
            }
            slot++
            index++
        }
    }

}