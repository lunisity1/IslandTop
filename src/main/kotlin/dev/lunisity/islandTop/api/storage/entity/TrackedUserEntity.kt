package dev.lunisity.islandTop.api.storage.entity

import com.bgsoftware.superiorskyblock.api.island.Island
import com.google.gson.annotations.Expose
import dev.lunisity.aurora.json.entity.impl.BasicStorageEntity
import dev.lunisity.islandTop.api.data.TrackedType
import lombok.Getter
import lombok.Setter
import java.util.UUID

@Getter @Setter
class TrackedUserEntity(key: UUID) : BasicStorageEntity(key) {
    @Expose
    var island: UUID? = null
    @Expose
    val trackedStats: MutableMap<TrackedType, Long> = mutableMapOf()

    fun getTrackedStat(type: TrackedType): Long {
        return trackedStats[type] ?: 0L
    }

    fun getTotalTrackedStats(): Long {
        return trackedStats.values.sum()
    }

    fun addTrackedStat(type: TrackedType, amount: Long) {
        trackedStats[type] = getTrackedStat(type) + amount
    }

    fun removeTrackedStat(type: TrackedType, amount: Long) {
        trackedStats[type] = getTrackedStat(type) - amount
    }

    fun resetTrackedStat(type: TrackedType) {
        trackedStats[type] = 0L
    }

    fun hasTrackedStat(type: TrackedType, amount: Long): Boolean {
        return (getTrackedStat(type)) >= amount
    }
}