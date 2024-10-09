import module.PathsModule
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import java.lang.Exception

class BotLister : ListenerAdapter() {
    private val minecraftServerManager = MinecraftServerManager()
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.contentDisplay == "!restart") {
            val restartSelectMenu = StringSelectMenu.create("restart-select")
                .setPlaceholder("select game")
                .addOption("Satisfactory", "satisfactory")
                .build()

            event.channel.sendMessage("再起動するゲーム")
                .setActionRow(restartSelectMenu)
                .queue()
        }
        if (event.message.contentDisplay == "!minecraft"){
            val stopSelectMenu = StringSelectMenu.create("minecraft-select")
                .setPlaceholder("select menu")
                .addOption("サーバー起動","minecraft_start")
                .addOption("サーバー停止","minecraft_stop")
                .build()

            event.channel.sendMessage("マイクラ鯖メニュー")
                .setActionRow(stopSelectMenu)
                .queue()

        }
    }

    override fun onStringSelectInteraction(event: StringSelectInteractionEvent) {
        val selectedOption = event.selectedOptions[0].value
        val interactionId = event.componentId

        when(interactionId){
            "restart-select" -> restartHandler(event, selectedOption)
            "minecraft-select" -> minecraftHandler(event, selectedOption)
            else -> event.reply("これは みえてはいけないはずだよ")
        }
    }

    private fun restartHandler(event: StringSelectInteractionEvent,selectedGame: String){
        event.reply("$selectedGame 再起動中").queue()

        val batFilePath = PathsModule.getPath(selectedGame)

        try {
            val process = ProcessBuilder("cmd.exe", "/c", "start", "\"\"", "/b", batFilePath).start()
            process.waitFor()

        } catch (e: Exception) {
            event.channel.sendMessage("エラー: ${e.message}").queue()

        }
    }

    private fun minecraftHandler(event: StringSelectInteractionEvent,selectedOption: String){
        if (selectedOption == "minecraft_start"){
            event.reply(minecraftServerManager.startServer()).queue()
        }
        if (selectedOption == "minecraft_stop"){
            event.reply(minecraftServerManager.stopServer()).queue()
        }
    }
}
