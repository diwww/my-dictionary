buildscript {
    repositories {
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
        google()
        mavenCentral()
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}