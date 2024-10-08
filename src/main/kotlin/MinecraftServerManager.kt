import module.PathsModule
import java.io.File
import java.io.IOException

class MinecraftServerManager {
    private var serverProcess: Process? = null

    fun startServer(): String {
        return if (serverProcess == null || serverProcess?.isAlive != true) {
            try {
                Thread {
                    val processBuilder = ProcessBuilder(
                        PathsModule.getPath("JAVA_HOME"), "-server",
                        "-Xms" + PathsModule.getPath("MIN_RAM"),
                        "-Xmx" + PathsModule.getPath("MAX_RAM"),
                        "-XX:MaxMetaspaceSize=" + PathsModule.getPath("MAX_MetaspaceSize"),
                        "-jar", PathsModule.getPath("SERVER_JAR"), "nogui"
                    )

                    processBuilder.directory(File(PathsModule.getPath("SERVER_DIRECTORY")!!))
                    processBuilder.redirectOutput(File( PathsModule.getPath("SERVER_DIRECTORY")+"minecraft_server_output.log"))
                    processBuilder.redirectError(File(PathsModule.getPath("SERVER_DIRECTORY")+"minecraft_server_error.log"))

                    serverProcess = processBuilder.start()
                }.start()
                "サーバー起動中"
            } catch (e: Exception) {
                "エラー: ${e.message}"
            }
        } else {
            "サーバー既にあり"
        }
    }

    fun stopServer(): String {
        return if (serverProcess != null && serverProcess!!.isAlive) {
            try {
                serverProcess!!.outputStream.write("stop\n".toByteArray())
                serverProcess!!.outputStream.flush()
                "サーバー停止中"
            } catch (e: IOException) {
                println("IOException occurred while writing to outputStream: ${e.message}")
                "outputStream エラー: ${e.message}"
            } catch (e: Exception) {
                println("Unexpected exception: ${e.message}")
                "unexpected エラー: ${e.message}"
            }
        } else {
            "サーバーなし"
        }
    }
}