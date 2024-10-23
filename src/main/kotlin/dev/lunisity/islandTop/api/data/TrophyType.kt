package dev.lunisity.islandTop.api.data

import dev.lunisity.islandTop.IslandTop

enum class TrophyType(
    val type: TrackedType,
    val value: Long
) {

    FISH(TrackedType.FISH, IslandTop.mainConfig.getLong("Trophy/Fish/Value")),
    MOB(TrackedType.MOB, IslandTop.mainConfig.getLong("Trophy/Mob/Value")),
    ORE(TrackedType.ORE, IslandTop.mainConfig.getLong("Trophy/Ore/Value")),
    CROP(TrackedType.CROP, IslandTop.mainConfig.getLong("Trophy/Crop/Value"));

}