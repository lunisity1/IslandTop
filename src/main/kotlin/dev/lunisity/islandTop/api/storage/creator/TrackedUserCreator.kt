package dev.lunisity.islandTop.api.storage.creator

import dev.lunisity.aurora.json.creator.InstanceCreator
import dev.lunisity.islandTop.api.storage.entity.TrackedUserEntity
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class TrackedUserCreator : InstanceCreator<TrackedUserEntity> {

    override fun create(key: UUID): TrackedUserEntity {
        return TrackedUserEntity(key)
    }

    override fun canCreate(key: UUID): Boolean {
        return true
    }

    override fun canUnload(key: TrackedUserEntity): Boolean {
        val uuid: UUID = key.key

        val player: Player? = Bukkit.getPlayer(uuid)

        return player == null || !player.isOnline;
    }

}