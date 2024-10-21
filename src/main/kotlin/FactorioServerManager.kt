import module.PathsModule

class FactorioServerManager: ServerManager() {
    override fun startProcessBuilderCommand(): ProcessBuilder {
        return ProcessBuilder(
            PathsModule.getPath("FACTORIO_SERVER_DIRECTORY"),"--start-server", PathsModule.getPath("FACTORIO_SAVE_LOCATION")
        )
    }

    override fun stopProcessBuilderCommand() {
        serverProcess?.outputStream?.write("save\n".toByteArray())
        serverProcess?.outputStream?.write("quit\n".toByteArray())
    }
}