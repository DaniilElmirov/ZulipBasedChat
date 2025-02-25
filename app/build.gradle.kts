plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.elmirov.course"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.elmirov.course"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.elmirov.course.CustomTestRunner"
    }

    buildTypes {
        release {
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
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)

    implementation(libs.cicerone)

    implementation(libs.shimmer)

    implementation(libs.coil)

    implementation(libs.bundles.retrofit)

    implementation(libs.bundles.elmslie)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    //Test
    implementation(libs.androidx.rules)

    testImplementation(libs.bundles.unit.test)

    androidTestImplementation(libs.bundles.ui.test)
    androidTestImplementation(libs.wiremock) {
        exclude(group = "org.apache.httpcomponents", module = "httpclient")
    }

    debugImplementation (libs.fragment.testing)
    debugImplementation(libs.androidx.test.core)
}