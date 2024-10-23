package dev.lunisity.islandTop.manager

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI
import com.bgsoftware.superiorskyblock.api.island.Island
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.storage.entity.TrackedIslandEntity
import dev.lunisity.islandTop.api.storage.entity.TrackedUserEntity
import dev.lunisity.islandTop.api.utils.MathUtil
import java.util.UUID

class TopManager {

    fun getTop5Crop(): MutableMap<UUID, Long> {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.CROP) }
        val subList = if (top9List.size > 5) {
            top9List.subList(0, 5)
        } else {
            top9List
        }

        val newMap = mutableMapOf<UUID, Long>()

        for (user in subList) {
            newMap[user.key] = user.getTrackedStat(TrackedType.CROP)
        }

        return newMap
    }

    fun getTop5Fish(): MutableMap<UUID, Long> {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.FISH) }
        val subList = if (top9List.size > 5) {
            top9List.subList(0, 5)
        } else {
            top9List
        }

        val newMap = mutableMapOf<UUID, Long>()

        for (user in subList) {
            newMap[user.key] = user.getTrackedStat(TrackedType.FISH)
        }

        return newMap
    }

    fun getTop5Mob(): MutableMap<UUID, Long> {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.MOB) }
        val subList = if (top9List.size > 5) {
            top9List.subList(0, 5)
        } else {
            top9List
        }

        val newMap = mutableMapOf<UUID, Long>()

        for (user in subList) {
            newMap[user.key] = user.getTrackedStat(TrackedType.MOB)
        }

        return newMap
    }

    fun getTop5Ore(): MutableMap<UUID, Long> {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.ORE) }
        val subList = if (top9List.size > 5) {
            top9List.subList(0, 5)
        } else {
            top9List
        }

        val newMap = mutableMapOf<UUID, Long>()

        for (user in subList) {
            newMap[user.key] = user.getTrackedStat(TrackedType.ORE)
        }

        return newMap
    }

    fun getTop5Island(): MutableMap<Island, Long> {
        val top5: MutableMap<UUID, TrackedIslandEntity> = IslandTop.islandStorageManager.cache!!.asMap

        val top5List: MutableList<TrackedIslandEntity> = top5.values.toMutableList()
        top5List.sortByDescending { it.getTotalTrophies() }
        val subList = if (top5List.size > 5) {
            top5List.subList(0, 5)
        } else {
            top5List
        }

        val newMap = mutableMapOf<Island, Long>()

        for (island in subList) {
            val islandObject = SuperiorSkyblockAPI.getIslandByUUID(island.island!!)
            newMap[islandObject] = MathUtil.round(island.getTotalTrophies(), 2)
        }

        return newMap
    }

}