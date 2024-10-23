package dev.lunisity.islandTop.listener.islands

import com.bgsoftware.superiorskyblock.api.events.IslandKickEvent
import com.bgsoftware.superiorskyblock.api.events.IslandLeaveEvent
import com.bgsoftware.superiorskyblock.api.events.IslandQuitEvent
import dev.lunisity.islandTop.IslandTop
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class LeaveListener : Listener {

    @EventHandler
    fun onLeave(event: IslandQuitEvent) {
        val player = event.player
        val island = event.island

        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)
        userEntity.island = null
        userEntity.isModified = true
        IslandTop.userStorageManager.write(userEntity)

        val islandEntity = IslandTop.islandStorageManager.read(island.uniqueId) ?: return
        islandEntity.removeAllTrackedStats(player.uniqueId)
        islandEntity.members.remove(player.uniqueId)
        islandEntity.isModified = true

        IslandTop.islandStorageManager.write(islandEntity)
    }

    @EventHandler
    fun onKick(event: IslandKickEvent) {
        val player = event.player
        val island = event.island

        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)
        userEntity.island = null
        userEntity.isModified = true
        IslandTop.userStorageManager.write(userEntity)

        val islandEntity = IslandTop.islandStorageManager.read(island.uniqueId)
        islandEntity.removeAllTrackedStats(player.uniqueId)
        islandEntity.members.remove(player.uniqueId)
        islandEntity.isModified = true

        IslandTop.islandStorageManager.write(islandEntity)
    }

}