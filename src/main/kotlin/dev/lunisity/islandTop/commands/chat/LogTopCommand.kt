package dev.lunisity.islandTop.commands.chat

import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class LogTopCommand : AbstractCommand<IslandTop>("logtop-chat") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val logTopMessage = IslandTop.messageManager.getLogTopMessage()

        val replacer = TextReplacer()
            .with("%entries%", logTopMessage)
            .with("%type%", "Log")
            .with("%color%", "&6")

        IslandTop.messageCache.sendMessage(player, "TYPE-TOP", replacer)
    }

}