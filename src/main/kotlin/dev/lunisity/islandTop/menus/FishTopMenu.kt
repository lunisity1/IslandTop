package dev.lunisity.islandTop.menus

import dev.lunisity.borealis.text.color.ColorAPI
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.novaconfig.builder.ItemBuilder
import dev.lunisity.novamenu.abstracts.AbstractMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.collections.iterator

class FishTopMenu(val player: Player) : AbstractMenu(IslandTop.instance, IslandTop.menuConfig, "FishTop") {

    init {
        setPageItems()
    }

    fun setPageItems() {
        val top5 = IslandTop.topManager.getTop5Fish()

        if (top5.isEmpty()) {
            return
        }

        var slot = 11
        var index = 1
        for (entry in top5) {
            val target = Bukkit.getPlayer(entry.key) ?: Bukkit.getOfflinePlayer(entry.key)

            val item = ItemBuilder.of(Material.PLAYER_HEAD).setOwner(target.name)
                .setName("&3&l#$index &b${target.name}")
                .setLore(listOf("&b${entry.value} &7Fish")).itemStack

            setItem(slot, item) { e ->
                e.isCancelled = true

                val stat = IslandTop.playerManager.getStat(target as Player, TrackedType.FISH)

                player.sendMessage(ColorAPI.apply("&a${target.name} &7has &a$stat &7fish!"))
            }
            slot++
            index++
        }
    }

}