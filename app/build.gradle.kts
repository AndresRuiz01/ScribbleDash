plugins {
    alias(libs.plugins.scribbledash.android.application.compose)
}

android {
    namespace = "com.devcampus.scribbledash"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    // Use the Compose bundle
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

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
}