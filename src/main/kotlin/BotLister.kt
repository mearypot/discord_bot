import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import java.lang.Exception

class BotLister : ListenerAdapter(){
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.contentDisplay == "!restart"){
            val selectMenu = StringSelectMenu.create("select game")
                .setPlaceholder("select game")
                .addOption("Satisfactory","satisfactory")
                .build()

            event.channel.sendMessage("再起動するゲーム")
                .setActionRow(selectMenu)
                .queue()
        }
    }

    override fun onStringSelectInteraction(event: StringSelectInteractionEvent) {
        val selectedGame = event.selectedOptions[0].value
        event.reply("$selectedGame 再起動中").queue()

        val batFilePath = when(selectedGame){
            "satisfactory" -> {
                "F:/SteamLibrary/steamapps/common/SatisfactoryDedicatedServer/restarter.bat"
            }
            else -> return
        }

        try {
            val process = ProcessBuilder(batFilePath).start()
            process.waitFor()
            event.channel.sendMessage("$selectedGame 再起動完了").queue()
        }catch (e: Exception){
            event.channel.sendMessage("エラー: ${e.message}").queue()
        }finally {
            event.channel.sendMessage("処理終了").queue()
        }
    }
}