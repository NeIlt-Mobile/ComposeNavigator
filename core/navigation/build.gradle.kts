plugins {
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}

dependencies {
    implementation(libs.composeNavigation)
}

android {
    namespace = "neilt.mobile.core.navigation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
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
