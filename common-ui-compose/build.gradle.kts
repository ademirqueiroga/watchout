plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.admqueiroga.common_ui_compose"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//}

dependencies {
    implementation(project(":data"))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composePreview)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeFoundation)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.coilCompose)

    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.compose}")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4-android:${Versions.compose}")
}