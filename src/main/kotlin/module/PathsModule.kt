package module

import java.io.InputStream
import java.util.Properties

object PathsModule {
    private val properties = Properties()

    init {
        loadProperties()
    }

    private fun loadProperties(){
        val inputStream: InputStream = this::class.java.getResourceAsStream("/local.properties")
            ?: throw IllegalArgumentException("file not found")
        properties.load(inputStream)
    }

    fun getPath(key: String): String?{
        return properties.getProperty(key)
    }
}

