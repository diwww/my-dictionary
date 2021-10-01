rootProject.name = "MyDictionary"
include(":app")

applyLocalSettings("local.properties")

/**
 * Loads settings from specified file.
 *
 * @param path - settings file's path.
 */
fun applyLocalSettings(path: String) {
    println("Applying local settings from: '$path'")

    val file = File(path)

    if (!file.exists()) {
        println("File: '$path' was not found. Applying local settings has been SKIPPED!")
        return
    }

    val properties = java.util.Properties()

    val reader = file.reader()
    reader.use(properties::load)

    properties.stringPropertyNames().forEach {
        extra.set(it, properties[it])
    }

    gradle.beforeProject {
        properties.stringPropertyNames().forEach {
            extra.set(it, properties[it])
        }
    }

    println("Applying local settings has been completed successfully.")
    println()
}