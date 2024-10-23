package dev.lunisity.islandTop.commands.chat

import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class MobTopCommand : AbstractCommand<IslandTop>("mobtop-chat") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val mobTopMessage = IslandTop.messageManager.getLogTopMessage()

        val replacer = TextReplacer()
            .with("%entries%", mobTopMessage)
            .with("%type%", "Mob")
            .with("%color%", "&4")

        IslandTop.messageCache.sendMessage(player, "TYPE-TOP", replacer)
    }

}