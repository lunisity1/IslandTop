package dev.lunisity.islandTop.commands.admin.strikes.impl

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.novacommands.abstracts.SubCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class StrikeViewCommand : SubCommand("view", "list") {

    // strikeadmin view player

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        if (!player.hasPermission("islandtop.admin")) {
            IslandTop.messageCache.sendMessage(player, "NO-PERMISSION")
            return
        }

        if (context.length < 2) {
            IslandTop.messageCache.sendMessage(player, "INVALID-USAGE")
            return
        }

        if (!context.isPlayer(1)) {
            IslandTop.messageCache.sendMessage(player, "INVALID-USAGE")
            return
        }

        val target = context.asPlayer(1)

        IslandTop.strikeManager.viewStrikes(target)
    }

}