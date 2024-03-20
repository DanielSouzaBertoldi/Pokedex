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

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("io.coil-kt:coil-compose:2.2.0")
    implementation("io.coil-kt:coil-gif:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose Stuff
    implementation(platform("androidx.compose:compose-bom:2023.04.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0-alpha02")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Hilt stuff
    implementation("com.google.dagger:hilt-android:2.45")
    testImplementation("com.google.dagger:hilt-android-testing:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kaptTest("com.google.dagger:hilt-compiler:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.3")

    implementation("androidx.paging:paging-runtime:3.1.1")
    testImplementation("androidx.paging:paging-common:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha18")

    implementation("androidx.room:room-runtime:2.5.1")
    ksp("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    testImplementation("androidx.room:room-testing:2.5.1")
    implementation("androidx.room:room-paging:2.5.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.25.1")
    implementation("com.google.accompanist:accompanist-placeholder:0.25.1")
    implementation("com.google.accompanist:accompanist-pager:0.25.1")

    // This is just for debugging reasons. It will be deleted later on.
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")

    // DataStore
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
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