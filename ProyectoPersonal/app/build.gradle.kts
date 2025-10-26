plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)


}

android {
    namespace = "com.example.proyectopersonal"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.proyectopersonal"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
//    val room_version = "2.6.1" // Use the latest version
//    annotationProcessor(libs.androidx.room.compiler)
//    annotationProcessor ("androidx.room:room-compiler:$room_version")
//
//
//
//    implementation("androidx.room:room-runtime:$room_version")
//    implementation("androidx.room:room-ktx:$room_version") // Optional for Kotlin extensions
//    ksp("androidx.room:room-compiler:$room_version")
    //Para usar viewModel:
    //implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

    //Fin viewModel

    // ... other dependencies
    //implementation("androidx.navigation:navigation-compose:2.7.7") // Use the latest stable version


//    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.appcompat)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.gson)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
//    implementation(libs.androidx.compiler)
    //Splashscreen:
//    implementation(libs.androidx.splashscreen)
    implementation("androidx.core:core-splashscreen:1.0.1")
//Dependencias de Google maps:
//    implementation("com.google.android.gms:play-services-auth:20.5.0")
//    implementation("com.google.maps.android:maps-compose:2.11.4")
//    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    //Fin maps
//OTROS:
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.transport.api)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.coil.compose)
    // implementation(libs.glide.compose)

    // Kotlin Serialization (JSON)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
//    ksp("androidx.room:room-compiler:2.6.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

//    Para Mensajes push desde Firebase y Biometric
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.biometric)

    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.storge)
    implementation(libs.firebase.auth)
    implementation(libs.kotlin.coroutines.play.services)

    implementation(libs.firestore.ktx)
    implementation(libs.firebase.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}