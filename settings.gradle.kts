pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("coil", "2.2.0")

            library("androidx-core", "androidx.core:core-ktx:1.10.0")
            library( "lifecycle-ktx", "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
            library( "activity-compose", "androidx.activity:activity-compose:1.7.0")
            library("coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
            library("junit", "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
            library("androidx-test", "androidx.test.ext:junit:1.1.5")
            library("androidx-test-espresso", "androidx.test.espresso:espresso-core:3.5.1")

            library("coil-compose", "io.coil-kt", "coil-compose").versionRef("coil")
            library("coil-gif", "io.coil-kt", "coil-gif").versionRef("coil")
            bundle("coil", listOf("coil-compose", "coil-gif"))

            library("compose-bom", "androidx.compose:compose-bom:2023.04.00")
            library("compose-ui", "androidx.compose.ui", "ui").withoutVersion()
            library("compose-material", "androidx.compose.material", "material").withoutVersion()
            library("compose-ui-tooling", "androidx.compose.ui", "ui-tooling").withoutVersion()
            library("compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview").withoutVersion()
            library("compose-ui-test", "androidx.compose.ui:ui-test-junit4:1.5.0-alpha02")
            library("compose-ui-test-manifest", "androidx.compose.ui", "ui-test-manifest").withoutVersion()
            bundle(
                "compose",
                listOf("compose-ui", "compose-material", "compose-ui-tooling-preview")
            )

            version("hilt", "2.45")
            library("hilt-core", "com.google.dagger", "hilt-android").versionRef("hilt")
            library("hilt-navigation-compose", "androidx.hilt:hilt-navigation-compose:1.0.0")
            library("hilt-android-testing", "com.google.dagger", "hilt-android-testing").versionRef("hilt")
            library("hilt-compiler", "com.google.dagger", "hilt-compiler").versionRef("hilt")
            bundle(
                "hilt",
                listOf("hilt-core", "hilt-navigation-compose")
            )

            version("retrofit", "2.9.0")
            library("retrofit-core", "com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            library("retrofit-moshi-converter", "com.squareup.retrofit2", "converter-moshi").versionRef("retrofit")
            bundle(
                "retrofit",
                listOf("retrofit-core", "retrofit-moshi-converter")
            )

            version("moshi", "1.14.0")
            library("moshi-core", "com.squareup.moshi", "moshi").versionRef("moshi")
            library("moshi-kt", "com.squareup.moshi", "moshi-kotlin").versionRef("moshi")
            library("moshi-kt-codegen", "com.squareup.moshi", "moshi-kotlin-codegen").versionRef("moshi")
            bundle(
                "moshi",
                listOf("moshi-core", "moshi-kt")
            )

            version("navigation", "2.5.3")
            library("navigation-ktx", "androidx.navigation", "navigation-fragment-ktx").versionRef("navigation")
            library("navigation-ui-ktx", "androidx.navigation", "navigation-ui-ktx").versionRef("navigation")
            library("navigation-compose", "androidx.navigation", "navigation-compose").versionRef("navigation")
            library("navigation-testing", "androidx.navigation", "navigation-testing").versionRef("navigation")
            bundle(
                "navigation",
                listOf("navigation-ktx", "navigation-ui-ktx", "navigation-compose")
            )

            version("paging", "3.1.1")
            library("paging-runtime", "androidx.paging", "paging-runtime").versionRef("paging")
            library("paging-common", "androidx.paging", "paging-common").versionRef("paging")
            library("paging-compose", "androidx.paging:paging-compose:1.0.0-alpha18")

            version("room", "2.5.1")
            library("room-runtime", "androidx.room", "room-runtime").versionRef("room")
            library("room-ktx", "androidx.room", "room-ktx").versionRef("room")
            library("room-paging", "androidx.room", "room-paging").versionRef("room")
            library("room-testing", "androidx.room", "room-testing").versionRef("room")
            library("room-compiler", "androidx.room", "room-compiler").versionRef("room")
            bundle(
                "room",
                listOf("room-runtime", "room-ktx", "room-paging")
            )

            version("accompanist", "0.25.1")
            library("accompanist-systemuicontroller", "com.google.accompanist", "accompanist-systemuicontroller").versionRef("accompanist")
            library("accompanist-placeholder", "com.google.accompanist", "accompanist-placeholder").versionRef("accompanist")
            library("accompanist-pager", "com.google.accompanist", "accompanist-pager").versionRef("accompanist")
            library("accompanist-navigation-animation", "com.google.accompanist", "accompanist-navigation-animation").versionRef("accompanist")
            bundle(
                "accompanist",
                listOf("accompanist-systemuicontroller", "accompanist-placeholder", "accompanist-pager", "accompanist-navigation-animation")
            )

            library("logging-interceptor", "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")

            library("datastore", "androidx.datastore:datastore:1.0.0")
            library("collections-immutable", "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
            library("serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
            bundle(
                "datastore",
                listOf("datastore", "collections-immutable", "serialization-json")
            )
        }
    }
}
rootProject.name = "Pokedex"
include(":app")
