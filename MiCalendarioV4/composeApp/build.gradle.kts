import org.gradle.kotlin.dsl.implementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.sqldelight)
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
            implementation(libs.ktor.clientOkHttp)
            implementation(libs.androidx.splashscreen)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.sqldelight.runtime)
            implementation(libs.ktor.ktorClientCore)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serializationKotlinxJson)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
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
            // Add these pickFirst directives
            pickFirsts += "META-INF/INDEX.LIST"
            pickFirsts += "META-INF/io.netty.versions.properties"
            pickFirsts += "META-INF/ktor-http.kotlin_module"
            pickFirsts += "META-INF/ktor-http-cio.kotlin_module"
            pickFirsts += "META-INF/ktor-utils.kotlin_module"
            pickFirsts += "META-INF/kotlinx-coroutines-core.kotlin_module"
            pickFirsts += "META-INF/kotlinx-datetime.kotlin_module"
            pickFirsts += "META-INF/kotlinx-io.kotlin_module"

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
