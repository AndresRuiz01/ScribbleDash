plugins {
    alias(libs.plugins.scribbledash.android.library)
}

android {
    namespace = "com.devcampus.core.data"
}

dependencies {
    implementation(projects.core.domain)
}