package dev.lunisity.islandTop.api.storage.entity

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI
import com.bgsoftware.superiorskyblock.api.island.Island
import com.google.gson.annotations.Expose
import com.mongodb.client.model.Filters.type
import dev.lunisity.aurora.json.entity.impl.BasicStorageEntity
import dev.lunisity.islandTop.api.data.StrikeData
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.data.TrophyType
import lombok.Getter
import lombok.Setter
import org.bukkit.entity.Player
import org.checkerframework.checker.units.qual.t
import java.util.UUID

@Getter @Setter
class TrackedIslandEntity(key: UUID) : BasicStorageEntity(key) {
    @Expose
    var leader: UUID? = null
    @Expose
    var island: UUID? = null
    @Expose
    val trackedStats: MutableMap<TrackedType, MutableMap<UUID, Long>> = mutableMapOf()
    @Expose
    var members: MutableList<UUID> = mutableListOf()
    @Expose
    val trophies: MutableMap<UUID, MutableMap<TrophyType, Long>> = mutableMapOf()
    @Expose
    val strikes: MutableList<StrikeData> = mutableListOf()

    fun getIsland(): Island? {
        return SuperiorSkyblockAPI.getIslandByUUID(island!!)
    }

    fun addMember(uuid: UUID) {
        if (!members.contains(uuid)) {
            members.add(uuid)
        }
    }

    fun retrieveMembers(): MutableList<UUID> {
        if (members.isEmpty()) {
            return mutableListOf()
        }
        return members
    }

    fun getAverage(): Long {
        var total: Long = 0

        for (type in trackedStats.keys) {
            total += getTrackedStat(type)
        }

        return total / trackedStats.keys.size
    }

    fun getTrackedStat(type: TrackedType): Long {
        val typeObject = trackedStats[type] ?: return 0

        var total: Long = 0

        for (stat in typeObject.values) {
            total += stat
        }

        return total
    }

    fun getTrackedStat(uuid: UUID, type: TrackedType): Long {
        val typeObject = trackedStats[type] ?: return 0

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

    fun resetStats() {
        trackedStats.clear()
    }

    fun removeTrackedStat(uuid: UUID, type: TrackedType, amount: Long) {
        val typeObject = trackedStats[type]

        if (typeObject == null) {
            return
        }

        typeObject[uuid] = getTrackedStat(uuid, type) - amount
    }

    fun setTrophy(uuid: UUID, type: TrophyType, amount: Long) {
        if (!trophies.containsKey(uuid)) {
            trophies[uuid] = mutableMapOf()
        }
        trophies[uuid]!![type] = amount
    }

    fun addTrophy(uuid: UUID, type: TrophyType, amount: Long) {
        setTrophy(uuid, type, getTrophy(uuid, type) + amount)
    }

    fun removeTrophy(uuid: UUID, type: TrophyType, amount: Long) {
        setTrophy(uuid, type, getTrophy(uuid, type) - amount)
    }

    fun getTrophy(uuid: UUID, type: TrophyType): Long {
        val trophies = trophies[uuid] ?: return 0
        return trophies[type] ?: return 0
    }

    fun getTrophies(type: TrophyType): Long {
        var total = 0L

        for (uuid in trophies.keys) {
            val trophy = getTrophy(uuid, type)
            total += trophy
        }

        return total
    }

    fun getTotalTrophies(uuid: UUID): MutableMap<TrophyType, Long> {
        var total: MutableMap<TrophyType, Long> = mutableMapOf()
        for (type in trophies[uuid]!!.keys) {
            total[type] = getTrophy(uuid, type)
        }
        return total
    }

    fun getTotalTrophies(): Long {
        var total: Long = 0
        for (uuid in trophies.keys) {
            for (type in trophies[uuid]!!.keys) {
                total += getTrophy(uuid, type)
            }
        }
        return total
    }

    fun hasStrikes(): Boolean {
        return strikes.isNotEmpty()
    }

    fun hasStrikes(number: Int): Boolean {
        return strikes.size <= number
    }

    fun addStrike(target: UUID, executor: UUID, reason: String) {
        val number = when (strikes.size) {
            0 -> 1
            1 -> 2
            2 -> 3
            3 -> 4
            else -> {
                return
            }
        }

        val data = StrikeData(target, executor, reason, number)
        strikes.add(data)
    }

    fun removeStrike(number: Int) {
        if (strikes.isEmpty()) return
        strikes.removeIf { it.number == number }
    }

    fun getStrikes(target: UUID): List<StrikeData> {
        return strikes.filter { it.target == target }
    }

    fun getAllStrikes(): List<StrikeData> {
        return strikes
    }

    fun getTotalStrikes(): Int {
        return strikes.size
    }

}