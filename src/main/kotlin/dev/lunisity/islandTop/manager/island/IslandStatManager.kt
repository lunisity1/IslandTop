package dev.lunisity.islandTop.manager.island

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI
import com.bgsoftware.superiorskyblock.api.island.Island
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.storage.entity.TrackedIslandEntity
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.UUID

class IslandStatManager {

    fun getIsland(player: Player): Island? {
        if (!hasIsland(player)) return null
        val island = IslandTop.userStorageManager.read(player.uniqueId).island!!
        return SuperiorSkyblockAPI.getIslandByUUID(island)
    }

    fun getIsland(player: OfflinePlayer): Island? {
        if (!hasIsland(player)) return null
        val island = IslandTop.userStorageManager.read(player.uniqueId).island!!
        return SuperiorSkyblockAPI.getIslandByUUID(island)
    }

    fun getIsland(island: Island?): TrackedIslandEntity {
        return IslandTop.islandStorageManager.read(island!!.uniqueId)
    }

    fun hasIsland(player: Player): Boolean {
        return IslandTop.userStorageManager.read(player.uniqueId).island != null
    }

    fun hasIsland(player: OfflinePlayer): Boolean {
        return IslandTop.userStorageManager.read(player.uniqueId).island != null
    }

    fun getMembers(island: Island): MutableList<UUID> {
        val islandEntity = getIsland(island)
        return islandEntity.retrieveMembers()
    }

    fun getAverage(player: Player): Long {
        val island = getIsland(player)
        val islandEntity = getIsland(island)

        return islandEntity.getAverage()
    }

    fun getAverage(player: OfflinePlayer): Long {
        val island = getIsland(player)
        val islandEntity = getIsland(island)

        return islandEntity.getAverage()
    }

    fun getStat(player: Player, type: TrackedType): Long {
        val island = getIsland(player)
        val islandEntity = getIsland(island)

        return islandEntity.getTrackedStat(player.uniqueId, type)
    }

    fun getTotalStats(player: Player): Long {
        val island = getIsland(player)
        val islandEntity = getIsland(island)

        return islandEntity.getTotalTrackedStats(player.uniqueId)
    }

    fun getTotalStats(island: Island): Long {
        val islandEntity = getIsland(island)

        return islandEntity.getTotalTrackedStats()
    }

    fun getTotalStats(island: Island, type: TrackedType): Long {
        val islandEntity = getIsland(island)

        return islandEntity.getTrackedStat(type)
    }

    fun addStat(player: Player, type: TrackedType, amount: Long) {
        val island = getIsland(player)
        val islandEntity = getIsland(island)

        islandEntity.addTrackedStat(player.uniqueId, type, amount)
        islandEntity.isModified = true

        IslandTop.islandStorageManager.write(islandEntity)
    }

    fun removeStat(player: Player, type: TrackedType, amount: Long) {
        val island = getIsland(player)
        val islandEntity = getIsland(island)

        islandEntity.removeTrackedStat(player.uniqueId, type, amount)
        islandEntity.isModified = true

        IslandTop.islandStorageManager.write(islandEntity)
    }

}