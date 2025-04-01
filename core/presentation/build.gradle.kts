plugins {
    alias(libs.plugins.scribbledash.android.library.compose)
}
android {
    namespace = "com.devcampus.core.presentation"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // api(libs.androidx.material3)
}