plugins {

    // Android
    alias(libs.plugins.androidApplication)

    // Kotlin
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "neilt.mobile.section_navigation_compose"
    compileSdk = 35

    defaultConfig {
        applicationId = "neilt.mobile.section_navigation_compose"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}