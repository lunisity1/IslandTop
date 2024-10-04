package dev.lunisity.islandTop.api.utils

import dev.lunisity.islandTop.IslandTop
import java.util.logging.Logger

class LogUtil {

    companion object {
        fun log(message: String) {
            IslandTop.logger.info(message)
        }

        fun warning(message: String) {
            IslandTop.logger.warning(message)
        }

        fun error(message: String) {
            IslandTop.logger.severe(message)
        }
    }

}