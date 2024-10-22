import module.PathsModule

class FactorioServerManager: ServerManager() {
    override fun startProcessBuilderCommand(): ProcessBuilder {
        val processBuilder = ProcessBuilder(
            PathsModule.getPath("FACTORIO_SERVER_DIRECTORY"),"--start-server", PathsModule.getPath("FACTORIO_SAVE_LOCATION")
        )

        return processBuilder
    }

    override fun stopProcessBuilderCommand() {
        serverProcess?.outputStream?.write("save\n".toByteArray())
        serverProcess?.outputStream?.write("quit\n".toByteArray())
        serverProcess?.outputStream?.flush()
    }
}