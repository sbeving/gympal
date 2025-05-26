// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt.android) apply false
}

// Fix for the IsolationException related to Kotlin
buildscript {
    dependencies {
        // Make sure Kotlin version matches what's in libs.versions.toml
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
    }
}