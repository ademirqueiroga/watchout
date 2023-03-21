plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp").version("1.7.10-1.0.6") // Or latest version of KSP
}

android {
    namespace = "com.admqueiroga.android"
    compileSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(project(":data"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation(Dependencies.pagingCommonKtx)
    api(Dependencies.room)
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomPaging)
    ksp(Dependencies.moshiCodeGen)
    ksp(Dependencies.roomCompiler)
}