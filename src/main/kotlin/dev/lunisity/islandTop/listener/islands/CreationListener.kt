package dev.lunisity.islandTop.listener.islands

import com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.storage.entity.TrackedIslandEntity
import dev.lunisity.islandTop.api.storage.entity.TrackedUserEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CreationListener : Listener {

    @EventHandler
    fun onCreation(event: IslandCreateEvent) {
        val player = event.player
        val island = event.island
        val members = island.getIslandMembers()

        val userEntity: TrackedUserEntity = IslandTop.userStorageManager.read(player.uniqueId)
        userEntity.island = island.uniqueId
        userEntity.isModified = true
        IslandTop.userStorageManager.write(userEntity)

        val islandEntity: TrackedIslandEntity = TrackedIslandEntity(island.uniqueId)
        islandEntity.island = island.uniqueId
        islandEntity.leader = island.owner.uniqueId
        islandEntity.members = members.map { it.uniqueId }.toMutableList()

        for (member in members) {
            val memberEntity: TrackedUserEntity = IslandTop.userStorageManager.read(member.uniqueId)
            memberEntity.island = island.uniqueId

            memberEntity.isModified = true
            IslandTop.userStorageManager.write(memberEntity)
        }

        islandEntity.isModified = true

        IslandTop.islandStorageManager.write(islandEntity)
    }

}