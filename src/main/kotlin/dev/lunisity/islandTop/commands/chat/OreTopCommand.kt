package dev.lunisity.islandTop.commands.chat

import dev.lunisity.borealis.text.TextReplacer
import dev.lunisity.islandTop.IslandTop
import dev.lunisity.novacommands.abstracts.AbstractCommand
import dev.lunisity.novacommands.context.CommandContext
import org.bukkit.entity.Player

class OreTopCommand : AbstractCommand<IslandTop>("oretop-chat") {

    override fun execute(context: CommandContext) {
        val player = context.sender as Player

        val oreTopMessage = IslandTop.messageManager.getOreTopMessage()

        val replacer = TextReplacer()
            .with("%entries%", oreTopMessage)
            .with("%type%", "Ore")
            .with("%color%", "&5")

        IslandTop.messageCache.sendMessage(player, "TYPE-TOP", replacer)
    }

}