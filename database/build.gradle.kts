plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.koin)

    ksp(libs.room.compiler)
    api(libs.room.ktx)
    implementation(libs.room.paging)

    testImplementation(libs.testing.junit)
    testImplementation(libs.testing.room)

    androidTestImplementation(libs.testing.androidx.core)
    androidTestImplementation(libs.testing.androidx.test.junit)
    androidTestImplementation(libs.testing.androidx.test.runner)
    androidTestImplementation(libs.testing.kotlinx.coroutines)
}