import module.PathsModule
import okio.IOException
import java.io.File

class MinecraftServerManager: ServerManager() {
    override fun startProcessBuilderCommand(): ProcessBuilder{
        val processBuilder = ProcessBuilder(
            PathsModule.getPath("JAVA_HOME"), "-server",
            "-Xms" + PathsModule.getPath("MIN_RAM"),
            "-Xmx" + PathsModule.getPath("MAX_RAM"),
            "-XX:MaxMetaspaceSize=" + PathsModule.getPath("MAX_MetaspaceSize"),
            "-jar", PathsModule.getPath("SERVER_JAR"), "nogui"
        )
        val serverFileDirection = PathsModule.getPath("MINECRAFT_SERVER_DIRECTORY")
        processBuilder.directory(File(serverFileDirection!!))
        processBuilder.redirectOutput(File(serverFileDirection))
        processBuilder.redirectError(File(serverFileDirection))

        return processBuilder
    }

    override fun stopProcessBuilderCommand(){
        serverProcess?.outputStream?.write("stop\n".toByteArray())
        serverProcess?.outputStream?.flush()
    }
}