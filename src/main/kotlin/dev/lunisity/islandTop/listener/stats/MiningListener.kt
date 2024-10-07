package dev.lunisity.islandTop.listener.stats

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.data.TrophyType
import dev.lunisity.islandTop.api.utils.LogUtil
import dev.lunisity.islandTop.api.utils.WhitelistUtil
import dev.lunisity.islandTop.manager.island.IslandStatManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class MiningListener : Listener {

    @EventHandler
    fun onLogBreak(event: BlockBreakEvent) {
        if (event.isCancelled) {
            return
        }

        val player = event.player
        val block = event.block
        val world = block.world

        if (!WhitelistUtil.isValidOre(block.type)) {
            return
        }

        if (!WhitelistUtil.isMiningWorld(world.name)) {
            return
        }

        LogUtil.error("A player broke an ore! ${player.name}")

        IslandTop.playerManager.addStat(player, TrackedType.ORE, 1)
        if (IslandTop.islandManager.hasIsland(player)) {
            IslandTop.islandManager.addStat(player, TrackedType.ORE, 1)
            IslandTop.trophyManager.addTrophy(player, TrophyType.ORE, TrophyType.ORE.value)
        }
    }

}