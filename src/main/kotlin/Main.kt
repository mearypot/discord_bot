import module.PathsModule
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

fun main(args: Array<String>) {
    val botToken = PathsModule.getPath("DISCORD_TOKEN")
    val jda = JDABuilder.createLight(botToken, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
        .addEventListeners(BotLister())
        .build()

    fun registerSlashCommands(jda: JDA) {
        jda.upsertCommand("restart", "ゲームの再起動メニューを表示")
            .queue()

        jda.upsertCommand("minecraft", "Minecraftサーバー管理メニューを表示")
            .queue()

        jda.upsertCommand("factorio", "Factorioサーバー管理メニューを表示")
            .queue()
    }
}