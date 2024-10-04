import module.PathsModule

class MinecraftServerManager {
    private var serverProcess: Process? = null
    fun startServer(): String {
        return if(serverProcess == null || serverProcess?.isAlive != true){
            val processBuilder = ProcessBuilder("java","-server",
                "-Xms" + PathsModule.getPath("MIN_RAM"),
                "-Xmx" + PathsModule.getPath("MAX_RAM"),
                "-XX:MaxMetaspaceSize=" + PathsModule.getPath("MAX_MetaspaceSize"),
                PathsModule.getPath("JAVA_PARAMETERS"),
                "-jar",PathsModule.getPath("SERVER_JAR"),"nogui")
            processBuilder.inheritIO()
            serverProcess = processBuilder.start()
            ("サーバー起動")
        }else{
            ("サーバー既にあり")
        }
    }

    fun stopServer(): String{
        return if(serverProcess != null && serverProcess?.isAlive == true){
            serverProcess?.outputStream?.write("stop\n".toByteArray())
            serverProcess?.outputStream?.flush()
            ("サーバー停止")
        }else{
            ("サーバーなし")
        }
    }
}