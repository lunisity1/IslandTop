package dev.lunisity.islandTop.commands.menu

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.menus.FishTopMenu
import dev.lunisity.islandTop.menus.LogTopMenu
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class LogTopMenuCommand : AbstractCommand<IslandTop>("logtop") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val menu = LogTopMenu(player)
        menu.open(player)
    }

}