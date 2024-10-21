import java.io.IOException

abstract class ServerManager() {
    var serverProcess: Process? = null
    fun startServer(): String {
        return if(this.serverProcess == null || this.serverProcess?.isAlive != true){
            try{
                Thread{
                    this.serverProcess = startProcessBuilderCommand().start()
                }.start()
                "サーバー起動中"
            }catch (e: Exception){
                "エラー: ${e.message}"
            }
        }else{
            "サーバー既にあり"
        }
    }

    fun stopServer(): String{
        return if (serverProcess != null && serverProcess!!.isAlive) {
            try{
                stopProcessBuilderCommand()
                "サーバー停止中"
            }catch (e: IOException){
                "outputStreamエラー ${e.message}"
            }
        }else{
            "サーバーなし"
        }
    }

    abstract fun startProcessBuilderCommand(): ProcessBuilder
    abstract fun stopProcessBuilderCommand()
}