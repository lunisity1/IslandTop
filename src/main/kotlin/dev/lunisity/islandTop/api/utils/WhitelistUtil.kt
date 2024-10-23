package dev.lunisity.islandTop.api.utils

import dev.lunisity.islandTop.IslandTop
import org.bukkit.Material
import org.bukkit.entity.EntityType

class WhitelistUtil {

    companion object {
        private val rawFarmingWhitelist = IslandTop.mainConfig.getStringList("Whitelist/Farming")
        val farmingWhitelist = rawFarmingWhitelist.map { Material.valueOf(it) }
        private val farmingWorldWhitelist = IslandTop.mainConfig.getStringList("Allowed-Worlds/Farming")

        fun isValidCrop(crop: Material): Boolean {
            return farmingWhitelist.contains(crop)
        }

        fun isFarmingWorld(world: String): Boolean {
            return farmingWorldWhitelist.contains(world)
        }

        private val rawFishingWhitelist = IslandTop.mainConfig.getStringList("Whitelist/Fishing")
        private val fishingWorldWhitelist = IslandTop.mainConfig.getStringList("Allowed-Worlds/Fishing")

        fun isValidFish(fish: String): Boolean {
            return rawFishingWhitelist.contains(fish.uppercase())
        }

        fun isFishingWorld(world: String): Boolean {
            return fishingWorldWhitelist.contains(world)
        }

        private val rawMiningWhitelist = IslandTop.mainConfig.getStringList("Whitelist/Mining")
        val miningWhitelist = rawMiningWhitelist.map { Material.valueOf(it) }
        private val miningWorldWhitelist = IslandTop.mainConfig.getStringList("Allowed-Worlds/Mining")

        fun isValidOre(ore: Material): Boolean {
            return miningWhitelist.contains(ore)
        }

        fun isMiningWorld(world: String): Boolean {
            return miningWorldWhitelist.contains(world)
        }

        private val rawMobWhitelist = IslandTop.mainConfig.getStringList("Whitelist/Mobs")
        val mobWhitelist = rawMobWhitelist.map { EntityType.valueOf(it) }
        private val mobWorldWhitelist = IslandTop.mainConfig.getStringList("Allowed-Worlds/Mobs")

        fun isValidMob(mob: EntityType): Boolean {
            return mobWhitelist.contains(mob)
        }

        fun isMobWorld(world: String): Boolean {
            return mobWorldWhitelist.contains(world)
        }


    }

}