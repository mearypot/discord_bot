import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent

fun main(args: Array<String>) {
    val botToken = System.getenv("DISCORD_TOKEN")
    val jda = JDABuilder.createLight(botToken, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
        .setActivity(Activity.playing("ゲーム再起動管理"))
        .addEventListeners(BotLister())
        .build()
}