import module.PathsModule
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import java.lang.Exception

class BotLister : ListenerAdapter() {
    private val minecraftServerManager = MinecraftServerManager()
    private val factorioServerManager = FactorioServerManager()
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
            val minecraftSelectMenu = StringSelectMenu.create("minecraft-select")
                .setPlaceholder("select menu")
                .addOption("サーバー起動","minecraft_start")
                .addOption("サーバー停止","minecraft_stop")
                .build()

            event.channel.sendMessage("マイクラ鯖メニュー")
                .setActionRow(minecraftSelectMenu)
                .queue()

        }
        if(event.message.contentDisplay == "!factorio"){
            val factorioSelectMenu = StringSelectMenu.create("factorio-select")
                .setPlaceholder("select menu")
                .addOption("サーバー起動","factorio_start")
                .addOption("サーバー停止","factorio_stop")
                .build()

            event.channel.sendMessage("factorio鯖メニュー")
                .setActionRow(factorioSelectMenu)
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
            event.channel.sendMessage(minecraftServerManager.startServer()).queue()
        }
        if (selectedOption == "minecraft_stop"){
            event.channel.sendMessage(minecraftServerManager.stopServer()).queue()
        }
    }
    private fun factorioHandler(event: StringSelectInteractionEvent,selectedOption: String){
        if(selectedOption == "factorio_start"){
            event.channel.sendMessage(factorioServerManager.startServer()).queue()
        }
        if(selectedOption == "minecraft_stop"){
            event.channel.sendMessage(factorioServerManager.startServer()).queue()
        }
    }
}
