plugins {
    id("com.android.application") version "7.1.3" apply false
    id("com.android.library") version "7.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    kotlin("plugin.serialization") version "1.6.10" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("com.google.devtools.ksp") version "1.6.10-1.0.4" apply false
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}