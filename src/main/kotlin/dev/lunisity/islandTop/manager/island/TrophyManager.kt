package dev.lunisity.islandTop.manager.island

import com.bgsoftware.superiorskyblock.api.island.Island
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrophyType
import org.bukkit.entity.Player

class TrophyManager {

    fun getTrophy(player: Player, type: TrophyType): Long {
        val island = IslandTop.islandManager.getIsland(player) ?: return 0
        val entity = IslandTop.islandManager.getIsland(island)

        return entity.getTrophy(player.uniqueId, type)
    }

    fun getTotalTrophies(player: Player): Long {
        val island = IslandTop.islandManager.getIsland(player) ?: return 0
        val entity = IslandTop.islandManager.getIsland(island)

        return entity.getTotalTrophies()
    }

    fun getTotalTrophies(island: Island): Long {
        val entity = IslandTop.islandManager.getIsland(island)
        return entity.getTotalTrophies()
    }

    fun setTrophy(player: Player, type: TrophyType, amount: Long) {
        val island = IslandTop.islandManager.getIsland(player) ?: return
        val entity = IslandTop.islandManager.getIsland(island)

        entity.setTrophy(player.uniqueId, type, amount)
        entity.isModified = true

        IslandTop.islandStorageManager.write(entity)
    }

    fun addTrophy(player: Player, type: TrophyType, amount: Long) {
        val island = IslandTop.islandManager.getIsland(player) ?: return
        val entity = IslandTop.islandManager.getIsland(island)

        entity.addTrophy(player.uniqueId, type, amount)
        entity.isModified = true

        IslandTop.islandStorageManager.write(entity)
    }

    fun removeTrophy(player: Player, type: TrophyType, amount: Long) {
        val island = IslandTop.islandManager.getIsland(player) ?: return
        val entity = IslandTop.islandManager.getIsland(island)

        entity.removeTrophy(player.uniqueId, type, amount)
        entity.isModified = true

        IslandTop.islandStorageManager.write(entity)
    }



}