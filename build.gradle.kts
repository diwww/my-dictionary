buildscript {
    repositories {
        maven { setUrl("https://artifactory.artifacts.avp.ru:443/artifactory/mobile-maven-libs-release/") }
        google()
        mavenCentral()
    }
    dependencies {
        classpath( "com.android.tools.build:gradle:4.1.3")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

allprojects {
    repositories {
        maven { setUrl("https://artifactory.artifacts.avp.ru:443/artifactory/mobile-maven-libs-release/") }
        google()
        mavenCentral()
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}