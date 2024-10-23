package dev.lunisity.islandTop.commands.admin.strikes.impl

import dev.lunisity.islandTop.IslandTop
import dev.lunisity.novacommands.abstracts.SubCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class StrikeTakeCommand : SubCommand("take", "remove") {

    // strikeadmin take player number

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        if (!player.hasPermission("islandtop.admin")) {
            IslandTop.messageCache.sendMessage(player, "NO-PERMISSION")
            return
        }

        if (context.length < 3) {
            IslandTop.messageCache.sendMessage(player, "INVALID-USAGE")
            return
        }

        if (!context.isPlayer(1)) {
            IslandTop.messageCache.sendMessage(player, "INVALID-USAGE")
            return
        }

        if (!context.isInt(2)) {
            IslandTop.messageCache.sendMessage(player, "INVALID-USAGE")
            return
        }

        val target = context.asPlayer(1)
        val number = context.asInt(2)

        IslandTop.strikeManager.removeStrike(target, player, number)
    }

    override fun onTabComplete(context: CommandContext): List<String> {
        val player = context.sender as Player

        if (!player.hasPermission("islandtop.admin")) {
            return emptyList()
        }

        if (context.length == 2) {
            if (!IslandTop.strikeManager.hasStrikes(player)) {
                return emptyList()
            }

            val list = mutableListOf<String>()
            for (strike in IslandTop.strikeManager.getStrikes(player)) {
                list.add(strike.number.toString())
            }

            return list
        }

        return super.onTabComplete(context)
    }
}