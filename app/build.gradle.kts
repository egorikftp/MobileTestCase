plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.egoriku.mobiletestcase"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions  {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.database)
    implementation(projects.network)

    implementation(libs.android.material.compose.adapter)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.coil)
    implementation(libs.coil.compose)

    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.koin)
    implementation(libs.kotlinx.coroutines)

    implementation(libs.material)

    implementation(libs.viewbinding.delegate)

    testImplementation(libs.testing.junit)
    testImplementation(libs.testing.androidx.core)
    testImplementation(libs.testing.androidx.test.junit)
    testImplementation(libs.testing.androidx.test.runner)
    testImplementation(libs.testing.kotlinx.coroutines)
    testImplementation(libs.testing.mockito.kotlin)
    testImplementation(libs.testing.robolectric)
    testImplementation(libs.testing.room)
}