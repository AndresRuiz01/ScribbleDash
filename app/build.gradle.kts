plugins {
    alias(libs.plugins.scribbledash.android.application.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.devcampus.scribbledash"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = "scribble"
            keyPassword = "scribble"
            storeFile = file("./keystore")
            storePassword = "scribble"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
    }

}

dependencies {

    // Use the Compose bundle
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.koin.androidx.compose)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material.icons.extended)

    // Crypto
    implementation(libs.androidx.security.crypto.ktx)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.bundles.compose.test)

    // Debug dependencies
    debugImplementation(libs.bundles.compose.debug)

    implementation(libs.androidx.core.splashscreen)

    // Timber
    implementation(libs.timber)

    implementation(libs.bundles.koin)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)

}