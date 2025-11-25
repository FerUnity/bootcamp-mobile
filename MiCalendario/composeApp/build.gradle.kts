import com.android.build.api.dsl.Packaging
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.testImplementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.sqlDelight)
//    id("app.cash.sqldelight")
//    id("com.android.application")
//    id("dagger.hilt.android.plugin")
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
            implementation(libs.kotlinx.coroutines.core)
            // Fechas multiplataforma
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            // Ktor común
            implementation(libs.ktor.client.core.v234)
            implementation(libs.ktor.client.content.negotiation.v234)
            implementation(libs.ktor.serialization.kotlinx.json.v234)

            // SQLDelight runtime
            implementation(libs.runtime.v202)

            implementation(libs.hilt.android.v2572)
//

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

sqldelight {
    databases {
        create("AppDatabaseExample") {
            packageName.set("com.example.micalendario")
        }
    }
}

android {
    namespace = "com.example.micalendario"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.micalendario"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
//        isCoreLibraryDesugaringEnabled = true
//        coreLibraryDesugaringEnabled true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }


    /*    composeOptions {
            kotlinCompilerExtensionVersion = compose_version
            kotlinCompilerVersion = "1.5.10"
        }*/


    fun Packaging.() {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
//    implementation(libs.androidx.ui)
    debugImplementation(compose.uiTooling)
    implementation (libs.androidx.core.ktx)
    implementation (libs.material)
  /*  implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_version")*/
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.testExt.junit)
    androidTestImplementation(libs.androidx.espresso.core)
 /*   androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")*/

    //Navigation
    implementation(libs.androidx.hilt.navigation.fragment)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)

    //rxJava
    implementation(libs.rxandroid)
    implementation(libs.rxjava)

    //noinspection UseTomlInstead
    implementation("com.google.accompanist:accompanist-insets:0.30.1")

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //Hilt
    implementation(libs.hilt.android.v2572)
    implementation(libs.hilt.compiler)
    implementation(libs.androidx.hilt.lifecycle.viewmodel) // This line is duplicated, consider removing one if not intended
    implementation(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android.gradle.plugin)
}

compose.desktop {
    application {
        mainClass = "com.example.micalendario.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.example.micalendario"
            packageVersion = "1.0.0"
        }
    }
}
