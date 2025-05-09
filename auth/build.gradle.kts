plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.immortalidiot.auth"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "AUTH_URL",
                "\"http://10.0.2.2:8080/auth/\""
            )
        }
        debug {
            buildConfigField(
                "String",
                "AUTH_URL",
                "\"http://10.0.2.2:8080/auth/\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":navigation"))

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)

    implementation(libs.kotlinx.serialization)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
}
