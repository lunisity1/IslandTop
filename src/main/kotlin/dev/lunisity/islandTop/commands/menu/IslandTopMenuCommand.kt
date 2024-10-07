package dev.lunisity.islandTop.commands.menu

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.menus.CropTopMenu
import dev.lunisity.islandTop.menus.IslandTopMenu
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class IslandTopMenuCommand : AbstractCommand<IslandTop>("istop", "islandtop") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val menu = IslandTopMenu(player)
        menu.open(player)
    }

}