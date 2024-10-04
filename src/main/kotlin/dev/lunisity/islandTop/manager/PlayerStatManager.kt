package dev.lunisity.islandTop.manager

import dev.lunisity.borealis.economy.EcoUtil
import dev.lunisity.borealis.text.MathUtil
import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.storage.entity.TrackedUserEntity
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class PlayerStatManager {

    fun getStat(player: Player, type: TrackedType): Long {
        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)

        return userEntity.getTrackedStat(type)
    }

    fun getTotalStats(player: Player): Long {
        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)

        return userEntity.getTotalTrackedStats()
    }

    fun addStat(player: Player, type: TrackedType, amount: Long) {
        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)

        userEntity.addTrackedStat(type, amount)
        userEntity.isModified = true

        IslandTop.userStorageManager.write(userEntity)
    }

    fun removeStat(player: Player, type: TrackedType, amount: Long) {
        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)

        userEntity.removeTrackedStat(type, amount)
        userEntity.isModified = true

        IslandTop.userStorageManager.write(userEntity)
    }

    fun resetStat(player: Player, type: TrackedType) {
        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)

        userEntity.resetTrackedStat(type)
        userEntity.isModified = true

        IslandTop.userStorageManager.write(userEntity)
    }

    fun hasStat(player: Player, type: TrackedType, amount: Long): Boolean {
        val userEntity = IslandTop.userStorageManager.read(player.uniqueId)

        return userEntity.hasTrackedStat(type, amount)
    }

}