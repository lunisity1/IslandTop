package dev.lunisity.islandTop.listener.stats

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.utils.LogUtil
import dev.lunisity.islandTop.api.utils.WhitelistUtil
import org.bukkit.entity.EntityType
import org.bukkit.entity.Fish
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent

class FishingListener : Listener {

    @EventHandler
    fun onFish(event: PlayerFishEvent) {
        LogUtil.error("PlayerFishEvent triggered")

        if (event.isCancelled) {
            LogUtil.error("Event was cancelled!")
            return
        }

        // Add log to check the event state
        if (event.state != PlayerFishEvent.State.CAUGHT_FISH) {
            LogUtil.error("Player state is ${event.state}, not CAUGHT_FISH!")
            return
        }

        val player = event.player
        val world = player.world
        val caughtEntity = event.caught ?: return

        if (caughtEntity !is Fish) {
            LogUtil.error("Caught entity is not a fish! It's a ${caughtEntity.type.name}")
            return
        }

        val fishType = caughtEntity.type.name
        LogUtil.error("Caught fish type: $fishType")

        if (!WhitelistUtil.isValidFish(fishType)) {
            LogUtil.error("Fish type $fishType is not in the whitelist!")
            return
        }

        if (!WhitelistUtil.isFishingWorld(world.name)) {
            LogUtil.error("Fishing in world ${world.name} is not allowed!")
            return
        }

        LogUtil.error("A player caught a fish! ${player.name}")

        IslandTop.playerManager.addStat(player, TrackedType.FISH, 1)
        if (IslandTop.islandManager.hasIsland(player)) {
            IslandTop.islandManager.addStat(player, TrackedType.FISH, 1)
        }
    }

}