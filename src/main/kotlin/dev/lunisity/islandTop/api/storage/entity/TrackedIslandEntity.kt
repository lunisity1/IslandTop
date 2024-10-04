package dev.lunisity.islandTop.api.storage.entity

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI
import com.bgsoftware.superiorskyblock.api.island.Island
import com.google.gson.annotations.Expose
import dev.lunisity.aurora.json.entity.impl.BasicStorageEntity
import dev.lunisity.islandTop.api.data.TrackedType
import lombok.Getter
import lombok.Setter
import java.util.UUID

@Getter @Setter
class TrackedIslandEntity(key: UUID) : BasicStorageEntity(key) {
    @Expose
    var leader: UUID? = null
    @Expose
    var island: UUID? = null
    @Expose
    val trackedStats: MutableMap<TrackedType, MutableMap<UUID, Long>> = mutableMapOf()

    fun getIsland(): Island? {
        return SuperiorSkyblockAPI.getIslandByUUID(island!!)
    }

    fun getAverage(): Long {
        var total: Long = 0

        for (type in trackedStats.keys) {
            total += getTrackedStat(type)
        }

        return total / trackedStats.keys.size
    }

    fun getTrackedStat(type: TrackedType): Long {
        val typeObject = trackedStats[type]

        var total: Long = 0

        for (stat in typeObject!!.values) {
            total += stat
        }

        return total
    }

    fun getTrackedStat(uuid: UUID, type: TrackedType): Long {
        val typeObject = trackedStats[type]

        if (typeObject == null) {
            return 0
        }

        return typeObject[uuid] ?: 0
    }

    fun getTotalTrackedStats(): Long {
        var total: Long = 0

        for (type in trackedStats.keys) {
            total += getTrackedStat(type)
        }

        return total
    }

    fun getTotalTrackedStats(uuid: UUID): Long {
        var total: Long = 0

        for (type in trackedStats.keys) {
            total += getTrackedStat(uuid, type)
        }

        return total
    }

    fun addAllTrackedStats(uuid: UUID, amount: Long) {
        for (type in trackedStats.keys) {
            addTrackedStat(uuid, type, amount)
        }
    }

    fun addTrackedStat(uuid: UUID, type: TrackedType, amount: Long) {
        val typeObject = trackedStats[type]

        if (typeObject == null) {
            trackedStats[type] = mutableMapOf(uuid to amount)
            return
        }

        val total = typeObject[uuid] ?: 0

        typeObject[uuid] = total + amount
    }

    fun removeAllTrackedStats(uuid: UUID) {
        for (type in trackedStats.keys) {
            removeTrackedStat(uuid, type, getTrackedStat(uuid, type))
        }
    }

    fun removeTrackedStat(uuid: UUID, type: TrackedType, amount: Long) {
        val typeObject = trackedStats[type]

        if (typeObject == null) {
            return
        }

        typeObject[uuid] = getTrackedStat(uuid, type) - amount
    }

}