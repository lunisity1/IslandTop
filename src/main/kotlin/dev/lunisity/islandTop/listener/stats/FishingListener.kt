package dev.lunisity.islandTop.listener.stats

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.data.TrophyType
import dev.lunisity.islandTop.api.utils.WhitelistUtil
import dev.lunisity.islandTop.manager.island.IslandStatManager
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.inventory.ItemStack

class FishingListener : Listener {

    @EventHandler
    fun onFish(event: PlayerFishEvent) {
        if (event.isCancelled) {
            return
        }

        if (event.state != PlayerFishEvent.State.CAUGHT_FISH) {
            return
        }

        val player = event.player
        val world = player.world
        val caughtEntity = event.caught ?: return

        if (caughtEntity !is Item) {
            return
        }

        val itemStack: ItemStack = caughtEntity.itemStack
        val fishType = itemStack.type.name

        if (!WhitelistUtil.isValidFish(fishType)) {
            return
        }

        if (!WhitelistUtil.isFishingWorld(world.name)) {
            return
        }

        IslandTop.playerManager.addStat(player, TrackedType.FISH, 1)
        if (IslandTop.islandManager.hasIsland(player)) {
            IslandTop.islandManager.addStat(player, TrackedType.FISH, 1)
            IslandTop.trophyManager.addTrophy(player, TrophyType.FISH, TrophyType.FISH.value)
        }
    }

}