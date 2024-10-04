package dev.lunisity.islandTop.listener.islands

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent
import dev.lunisity.islandTop.IslandTop
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class DisbandListener : Listener {

    @EventHandler
    fun onDisband(event: IslandDisbandEvent) {
        val player = event.player
        val island = event.island

        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)
        userEntity.island = null
        userEntity.isModified = true
        IslandTop.userStorageManager.write(userEntity)

        val islandEntity = IslandTop.islandStorageManager.read(island.uniqueId)
        islandEntity.removeAllTrackedStats(player.uniqueId)
        islandEntity.isModified = true

        IslandTop.islandStorageManager.write(islandEntity)
        IslandTop.islandStorageManager.invalidate(island.uniqueId)
    }

}