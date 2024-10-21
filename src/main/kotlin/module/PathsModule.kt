package module

import java.io.File
import java.io.FileInputStream
import java.util.Properties

object PathsModule {
    private val properties = Properties()

    init {
        loadProperties()
    }

    private fun loadProperties(){
        val jarDir = File(PathsModule::class.java.protectionDomain.codeSource.location.toURI()).parentFile
        val propertiesFile = File(jarDir, "local.properties")

        if (!propertiesFile.exists()) {
            throw IllegalArgumentException("local.properties file not found in the same directory as the JAR.")
        }

        FileInputStream(propertiesFile).use { inputStream ->
            properties.load(inputStream)
        }
    }

    fun getPath(key: String): String?{
        return properties.getProperty(key)
    }
}

