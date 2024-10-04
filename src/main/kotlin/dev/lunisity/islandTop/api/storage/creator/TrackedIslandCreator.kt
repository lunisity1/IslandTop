package dev.lunisity.islandTop.api.storage.creator

import dev.lunisity.aurora.json.creator.InstanceCreator
import dev.lunisity.islandTop.api.storage.entity.TrackedIslandEntity
import java.util.UUID

class TrackedIslandCreator : InstanceCreator<TrackedIslandEntity> {

    override fun create(key: UUID): TrackedIslandEntity? {
        return TrackedIslandEntity(key)
    }

    override fun canCreate(key: UUID): Boolean {
        return false
    }

    override fun canUnload(key: TrackedIslandEntity): Boolean {
        val members = key.getIsland()!!.getIslandMembers()

        for (member in members) {
            if (member.isOnline) {
                return false
            }
        }

        return true
    }

}