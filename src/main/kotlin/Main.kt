import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

fun main(args: Array<String>) {
    val botToken = System.getenv("DISCORD_TOKEN")
    val jda = JDABuilder.createDefault(botToken)
        .setActivity(Activity.playing("ゲーム再起動管理"))
        .addEventListeners(BotLister())
        .build()
}