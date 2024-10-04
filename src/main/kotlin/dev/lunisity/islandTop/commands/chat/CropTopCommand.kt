package dev.lunisity.islandTop.commands.chat

import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class CropTopCommand : AbstractCommand<IslandTop>("croptop-chat") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val cropTopMessage = IslandTop.messageManager.getCropTopMessage()

        val replacer = TextReplacer()
            .with("%entries%", cropTopMessage)
            .with("%type%", "Crop")
            .with("%color%", "&2")

        IslandTop.messageCache.sendMessage(player, "TYPE-TOP", replacer)
    }

}