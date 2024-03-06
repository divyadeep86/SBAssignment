plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlinx-serialization")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.sbtakehomeassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sbtakehomeassignment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources  = true
        /*
        unitTests.isReturnDefaultValues = true*/
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    //Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation (libs.androidx.paging.compose)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.savedstate)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.lifecycle.runtime.compose)

    //Coil for image loading
    implementation (libs.coil.compose)

    //DaggerHilt
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Network
    val retrofitVersion = "2.9.0"
    val jsonConverterVersion = "0.8.0"
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation (libs.retrofit)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //   implementation "com.squareup.retrofit2:converter-gson:${RetrofitVersion}"
    implementation (libs.retrofit2.kotlinx.serialization.converter)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.androidx.core.testing)
    testImplementation(libs.mockito.core)
    testImplementation (libs.mockito.kotlin)
  //  testImplementation (libs.mockk.v1138)
    testImplementation(libs.mockwebserver)
}

kapt {
    correctErrorTypes= true
}
