import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.sqldelight.androidDriver)
            implementation(libs.ktor.clientOkhttp)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.hilt)
            implementation(libs.hilt.compiler)
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.hilt.android.v2572)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.sqldelight.runtime)
            implementation(libs.ktor.ktorClientCore)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serializationKotlinxJson)
            implementation(libs.kotlinx.coroutines.core)
            // Koin is a good multiplatform alternative for DI
            implementation(libs.koin.core)
            // Fechas multiplataforma
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            // Ktor común
            implementation(libs.ktor.client.core.v234)
            implementation(libs.ktor.client.content.negotiation.v234)
            implementation(libs.ktor.serialization.kotlinx.json.v234)

            // SQLDelight runtime
            implementation(libs.runtime.v202)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.sqldelight.sqliteDriver)
            implementation(libs.ktor.clientCio)
        }
    }
}

android {
    namespace = "com.example.micalendariov4"
//    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.micalendariov4"
//        minSdk = libs.versions.android.minSdk.get().toInt()
        minSdk = 26
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.example.micalendariov4.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.example.micalendariov4"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("CalendarDatabase") {
            packageName = "com.example.micalendariov4.database"
        }
    }
}
