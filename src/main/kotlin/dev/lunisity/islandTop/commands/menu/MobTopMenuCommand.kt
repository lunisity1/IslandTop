package dev.lunisity.islandTop.commands.menu

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.menus.MobTopMenu
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class MobTopMenuCommand : AbstractCommand<IslandTop>("mobtop") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val menu = MobTopMenu(player)
        menu.open(player)
    }

}