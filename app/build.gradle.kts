plugins {

    // Android
    alias(libs.plugins.androidApplication)

    // Kotlin
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {

    // Navigation
    implementation(projects.navigator)

    // Koin
    implementation(platform(libs.koinBom))
    implementation(libs.koinCore)
    implementation(libs.koinAndroid)
    implementation(libs.koinAndroidCompose)

    // Compose
    implementation(platform(libs.composeBom))

    implementation(libs.composeUi)
    implementation(libs.composeUiGraphics)
    implementation(libs.composeUiTooling)

    implementation(libs.composeNavigation)
    implementation(libs.composeMaterial)

    // KotlinX
    implementation(libs.kotlinSerializationJson)
}

android {
    namespace = "neilt.mobile.navigator.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "neilt.mobile.navigator.app"

        minSdk = 24
        targetSdk = 35

        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}