package dev.lunisity.islandTop.commands.admin.strikes

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.islandTop.commands.admin.strikes.impl.StrikeGiveCommand
import dev.lunisity.islandTop.commands.admin.strikes.impl.StrikeTakeCommand
import dev.lunisity.islandTop.commands.admin.strikes.impl.StrikeViewCommand
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class StrikeAdminCommand : AbstractCommand<IslandTop>("strikeadmin") {

    init {
        withSubCommand(StrikeGiveCommand(), StrikeTakeCommand(), StrikeViewCommand())
    }

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        if (!player.hasPermission("islandtop.admin")) {
            IslandTop.messageCache.sendMessage(player, "NO-PERMISSION")
            return
        }
    }

}