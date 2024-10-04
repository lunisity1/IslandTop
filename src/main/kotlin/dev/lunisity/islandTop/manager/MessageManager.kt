package dev.lunisity.islandTop.manager

import dev.lunisity.borealis.economy.EcoUtil
import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.api.data.TrackedType
import dev.lunisity.islandTop.api.storage.entity.TrackedUserEntity
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class MessageManager {

    fun getCropTopMessage(): String {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap
        val format = IslandTop.mainConfig.getString("Formats/CropTop")
        val builder = StringBuilder()

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.CROP) }
        val subList = if (top9List.size > 9) {
            top9List.subList(0, 9)
        } else {
            top9List
        }

        for ((index, user) in subList.withIndex()) {
            val player = Bukkit.getPlayer(user.key) ?: Bukkit.getOfflinePlayer(user.key)
            val value = user.getTrackedStat(TrackedType.CROP)

            val replacer = TextReplacer()
                .with("%position%", index + 1)
                .with("%player%", player.name)
                .with("%value%", EcoUtil.formatNumber(value.toDouble()))

            builder.appendLine(replacer.apply(format))
        }

        return builder.toString()
    }

    fun getFishTopMessage(): String {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap
        val format = IslandTop.mainConfig.getString("Formats/FishTop")
        val builder = StringBuilder()

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.FISH) }
        val subList = if (top9List.size > 9) {
            top9List.subList(0, 9)
        } else {
            top9List
        }

        for ((index, user) in subList.withIndex()) {
            val player = Bukkit.getPlayer(user.key) ?: Bukkit.getOfflinePlayer(user.key)
            val value = user.getTrackedStat(TrackedType.FISH)

            val replacer = TextReplacer()
                .with("%position%", index + 1)
                .with("%player%", player.name)
                .with("%value%", EcoUtil.formatNumber(value.toDouble()))

            builder.appendLine(replacer.apply(format))
        }

        return builder.toString()
    }

    fun getLogTopMessage(): String {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap
        val format = IslandTop.mainConfig.getString("Formats/LogTop")
        val builder = StringBuilder()

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.LOG) }
        val subList = if (top9List.size > 9) {
            top9List.subList(0, 9)
        } else {
            top9List
        }

        for ((index, user) in subList.withIndex()) {
            val player = Bukkit.getPlayer(user.key) ?: Bukkit.getOfflinePlayer(user.key)
            val value = user.getTrackedStat(TrackedType.LOG)

            val replacer = TextReplacer()
                .with("%position%", index + 1)
                .with("%player%", player.name)
                .with("%value%", EcoUtil.formatNumber(value.toDouble()))

            builder.appendLine(replacer.apply(format))
        }

        return builder.toString()
    }

    fun getOreTopMessage(): String {
        val top9: MutableMap<UUID, TrackedUserEntity> = IslandTop.userStorageManager.cache!!.asMap
        val format = IslandTop.mainConfig.getString("Formats/OreTop")
        val builder = StringBuilder()

        val top9List: MutableList<TrackedUserEntity> = top9.values.toMutableList()
        top9List.sortByDescending { it.getTrackedStat(TrackedType.ORE) }
        val subList = if (top9List.size > 9) {
            top9List.subList(0, 9)
        } else {
            top9List
        }

        for ((index, user) in subList.withIndex()) {
            val player = Bukkit.getPlayer(user.key) ?: Bukkit.getOfflinePlayer(user.key)
            val value = user.getTrackedStat(TrackedType.ORE)

            val replacer = TextReplacer()
                .with("%position%", index + 1)
                .with("%player%", player.name)
                .with("%value%", EcoUtil.formatNumber(value.toDouble()))

            builder.appendLine(replacer.apply(format))
        }

        return builder.toString()
    }

}