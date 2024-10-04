package dev.lunisity.islandTop.commands.chat

import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class FishTopCommand : AbstractCommand<IslandTop>("fishtop-chat") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val fishTopMessage = IslandTop.messageManager.getFishTopMessage()

        val replacer = TextReplacer()
            .with("%entries%", fishTopMessage)
            .with("%type%", "Fish")
            .with("%color%", "&3")

        IslandTop.messageCache.sendMessage(player, "TYPE-TOP", replacer)
    }

}