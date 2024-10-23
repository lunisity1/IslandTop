package dev.lunisity.islandTop.manager.island

import com.bgsoftware.superiorskyblock.api.island.Island
import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.StrikeData
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class StrikeManager {

    fun hasStrikes(player: Player): Boolean {
        val island = IslandTop.islandManager.getIsland(player) ?: return false
        val entity = IslandTop.islandManager.getIsland(island)
        return entity.hasStrikes()
    }

    fun hasStrikes(player: Player, number: Int): Boolean {
        val island = IslandTop.islandManager.getIsland(player) ?: return false
        val entity = IslandTop.islandManager.getIsland(island)
        return entity.hasStrikes(number)
    }

    fun hasStrikes(island: Island, number: Int): Boolean {
        val entity = IslandTop.islandManager.getIsland(island)
        return entity.hasStrikes(number)
    }

    fun hasStrikes(island: Island): Boolean {
        val entity = IslandTop.islandManager.getIsland(island)
        return entity.hasStrikes()
    }

    fun addStrike(target: Player, executor: Player, reason: String) {
        val island = IslandTop.islandManager.getIsland(target) ?: return
        val entity = IslandTop.islandManager.getIsland(island)

        if (getTotalStrikes(island) >= 4) {
            return
        }

        entity.addStrike(target.uniqueId, executor.uniqueId, reason)
        entity.isModified = true

        IslandTop.islandStorageManager.write(entity)
        val replacer = TextReplacer().with("%target%", target.name).with("%reason%", reason)
        IslandTop.messageCache.sendMessage(executor, "STRIKE-GIVEN", replacer)
    }

    fun removeStrike(target: Player, executor: Player, number: Int) {
        val island = IslandTop.islandManager.getIsland(target) ?: return
        val entity = IslandTop.islandManager.getIsland(island)

        if (!hasStrikes(target)) {
            return
        }

        entity.removeStrike(number)
        entity.isModified = true

        IslandTop.islandStorageManager.write(entity)
        val replacer = TextReplacer().with("%target%", target.name).with("%number%", number)
        IslandTop.messageCache.sendMessage(executor, "STRIKE-TAKEN", replacer)
    }

    fun getAllStrikes(player: Player): List<StrikeData> {
        val island = IslandTop.islandManager.getIsland(player) ?: return emptyList()
        val entity = IslandTop.islandManager.getIsland(island)

        return entity.getAllStrikes()
    }

    fun getAllStrikes(island: Island): List<StrikeData> {
        val entity = IslandTop.islandManager.getIsland(island)
        return entity.getAllStrikes()
    }

    fun getStrikes(player: Player): List<StrikeData> {
        val island = IslandTop.islandManager.getIsland(player) ?: return emptyList()
        val entity = IslandTop.islandManager.getIsland(island)

        return entity.getStrikes(player.uniqueId)
    }

    fun getTotalStrikes(island: Island): Int {
        val entity = IslandTop.islandManager.getIsland(island)
        return entity.getTotalStrikes()
    }

    fun getTotalStrikes(player: Player): Int {
        val island = IslandTop.islandManager.getIsland(player) ?: return 0
        val entity = IslandTop.islandManager.getIsland(island)

        return entity.getTotalStrikes()
    }

    fun getCurrentPercentage(player: Player): Long {
        val strikes = getTotalStrikes(player)

        val strikeOne = IslandTop.mainConfig.getLong("Strikes/Strike-One")
        val strikeTwo = IslandTop.mainConfig.getLong("Strikes/Strike-Two")
        val strikeThree = IslandTop.mainConfig.getLong("Strikes/Strike-Three")
        val strikeFour = IslandTop.mainConfig.getLong("Strikes/Strike-Four")

        return when (strikes) {
            1 -> strikeOne
            2 -> strikeTwo
            3 -> strikeThree
            4 -> strikeFour
            else -> 0
        }
    }

    fun getCurrentPercentage(island: Island): Long {
        val strikes = getTotalStrikes(island)

        val strikeOne = IslandTop.mainConfig.getLong("Strikes/Strike-One")
        val strikeTwo = IslandTop.mainConfig.getLong("Strikes/Strike-Two")
        val strikeThree = IslandTop.mainConfig.getLong("Strikes/Strike-Three")
        val strikeFour = IslandTop.mainConfig.getLong("Strikes/Strike-Four")

        return when (strikes) {
            1 -> strikeOne
            2 -> strikeTwo
            3 -> strikeThree
            4 -> strikeFour
            else -> 0
        }
    }

    fun calculate(island: Island): Long {
        if (!hasStrikes(island)) {
            return IslandTop.trophyManager.getTotalTrophies(island)
        }

        val percentage = getCurrentPercentage(island)
        val trophies = IslandTop.trophyManager.getTotalTrophies(island)
        val remainder = ((percentage / 100.0) * trophies)
        return trophies - remainder.toLong()
    }

    fun calculate(player: Player): Long {
        if (!hasStrikes(player)) {
            return IslandTop.trophyManager.getTotalTrophies(player)
        }

        val percentage = getCurrentPercentage(player)
        val trophies = IslandTop.trophyManager.getTotalTrophies(player)
        val remainder = ((percentage / 100.0) * trophies)
        return trophies - remainder.toLong()
    }

    fun viewStrikes(player: Player) {
        IslandTop.messageCache.sendMessage(player, "STRIKE-LOADING", TextReplacer().with("%player%", player.name))

        if (!hasStrikes(player)) {
            IslandTop.messageCache.sendMessage(player, "NO-STRIKES-FOUND", TextReplacer().with("%player%", player.name))
            return
        }

        val format: String = "\n&c&lStrike %number%:\n  &7Issuer: &f%executor%\n  &7Offender: &f%target%\n  &7Reason: &f%reason%"

        val strikes = getAllStrikes(player)

        val builder: StringBuilder = StringBuilder()

        for (strike in strikes) {
            val target = Bukkit.getPlayer(strike.target) ?: Bukkit.getOfflinePlayer(strike.target)
            val executor = Bukkit.getPlayer(strike.executor) ?: Bukkit.getOfflinePlayer(strike.executor)
            val replacer = TextReplacer()
                .with("%number%", strike.number)
                .with("%target%", target.name)
                .with("%reason%", strike.reason)
                .with("%executor%", executor.name)

            builder.append(replacer.apply(format))
        }

        val textReplacer = TextReplacer()
            .with("%player%", player.name)
            .with("%strikes%", builder.toString())

        IslandTop.messageCache.sendMessage(player, "STRIKE-VIEW", textReplacer)
    }

}