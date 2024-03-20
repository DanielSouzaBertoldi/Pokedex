plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "daniel.bertoldi.pokedex"
    compileSdk = 33

    defaultConfig {
        applicationId = "daniel.bertoldi.pokedex"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        val composeVersion: String by rootProject.extra
        kotlinCompilerExtensionVersion = composeVersion
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.lifecycle.ktx)
    implementation(libs.activity.compose)
    implementation(libs.coroutines)
    implementation(libs.bundles.coil)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.androidx.test.espresso)

    // Compose Stuff
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    // Hilt stuff
    implementation(libs.bundles.hilt)
    testImplementation(libs.hilt.android.testing)
    kapt(libs.hilt.compiler)
    kaptTest(libs.hilt.compiler)

    implementation(libs.bundles.retrofit)

    implementation(libs.bundles.moshi)
    ksp(libs.moshi.kt.codegen)

    implementation(libs.bundles.navigation)
    androidTestImplementation(libs.navigation.testing)

    implementation(libs.paging.runtime)
    testImplementation(libs.paging.common)
    implementation(libs.paging.compose)

    implementation(libs.bundles.room)
    testImplementation(libs.room.testing)
    ksp(libs.room.compiler)

    implementation(libs.bundles.accompanist)

    // This is just for debugging reasons. It will be deleted later on.
    implementation(libs.logging.interceptor)

    // DataStore
    implementation(libs.bundles.datastore)

    implementation(project(":design-system"))
}

kapt {
    correctErrorTypes = true
}

detekt {
    allRules = true
    source = files("src/main/java/")
    config = files("config/detekt.yml")
    buildUponDefaultConfig = true
}