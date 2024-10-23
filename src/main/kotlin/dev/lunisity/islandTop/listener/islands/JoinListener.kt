package dev.lunisity.islandTop.listener.islands

import com.bgsoftware.superiorskyblock.api.events.IslandJoinEvent
import dev.lunisity.islandTop.IslandTop
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class JoinListener : Listener {

    @EventHandler
    fun onJoin(event: IslandJoinEvent) {
        val member = event.player
        val island = event.island

        val islandEntity = IslandTop.islandStorageManager.read(island.uniqueId) ?: return
        islandEntity.addMember(member.uniqueId)
        islandEntity.isModified = true

        IslandTop.islandStorageManager.write(islandEntity)
    }

}