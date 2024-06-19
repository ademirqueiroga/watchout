plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp").version("2.0.0-1.0.21") // Or latest version of KSP
}

android {
    namespace = "com.admqueiroga.android"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            buildConfigField("String", "TMDB_TOKEN", "\"${System.getenv("TMDB_API_TOKEN")}\"")
//            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "TMDB_TOKEN", "\"${System.getenv("TMDB_API_TOKEN")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(project(":data"))
    implementation("androidx.core:core-ktx:1.13.1")
    implementation(Dependencies.pagingCommonKtx)
    api(Dependencies.room)
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomPaging)
    ksp(Dependencies.moshiCodeGen)
    ksp(Dependencies.roomCompiler)
}