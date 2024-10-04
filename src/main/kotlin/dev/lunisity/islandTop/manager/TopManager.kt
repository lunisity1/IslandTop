package dev.lunisity.islandTop.manager

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.storage.entity.TrackedUserEntity
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

        for ((index, user) in subList.withIndex()) {
            newMap[user.key] = user.getTrackedStat(TrackedType.FISH)
        }

        return newMap
    }

    fun getTop5Log(): MutableMap<UUID, Long> {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.LOG) }
        val subList = if (top9List.size > 5) {
            top9List.subList(0, 5)
        } else {
            top9List
        }

        val newMap = mutableMapOf<UUID, Long>()

        for ((index, user) in subList.withIndex()) {
            newMap[user.key] = user.getTrackedStat(TrackedType.LOG)
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

        for ((index, user) in subList.withIndex()) {
            newMap[user.key] = user.getTrackedStat(TrackedType.ORE)
        }

        return newMap
    }

}