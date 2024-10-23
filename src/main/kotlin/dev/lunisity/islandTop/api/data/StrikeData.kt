package dev.lunisity.islandTop.api.data

import java.util.UUID

data class StrikeData(val target: UUID, val executor: UUID, val reason: String, val number: Int)
