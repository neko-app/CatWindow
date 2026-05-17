plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.floatwindow"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.floatwindow"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lint {
        // 屏蔽依赖版本目录提示，自用项目无需强制迁移 TOML
        disable += listOf("GradleDependency")
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core:1.12.0")
    implementation("com.google.android.material:material:1.11.0")
}