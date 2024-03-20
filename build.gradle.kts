buildscript {
    extra.apply {
        set("composeVersion", "1.4.4")
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("io.gitlab.arturbosch.detekt") version "1.21.0" apply false
    id("com.google.dagger.hilt.android") version "2.45" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}