package dev.lunisity.islandTop.listener.stats

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.data.TrophyType
import dev.lunisity.islandTop.api.utils.WhitelistUtil
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent

class MobListener : Listener {

    @EventHandler
    fun onLogBreak(event: EntityDamageByEntityEvent) {
        if (event.isCancelled) {
            return
        }

        if (event.damager !is Player) return

        val player = event.damager as Player
        val entity = event.entity
        val world = entity.world

        if (!WhitelistUtil.isValidMob(entity.type)) {
            return
        }

        if (!WhitelistUtil.isMobWorld(world.name)) {
            return
        }

        IslandTop.playerManager.addStat(player, TrackedType.MOB, 1)
        if (IslandTop.islandManager.hasIsland(player)) {
            IslandTop.islandManager.addStat(player, TrackedType.MOB, 1)
            IslandTop.trophyManager.addTrophy(player, TrophyType.MOB, TrophyType.MOB.value)
        }
    }

}