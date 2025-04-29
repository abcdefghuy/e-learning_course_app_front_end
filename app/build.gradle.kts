plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.e_learningcourse"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.e_learningcourse"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.gson)
    implementation(libs.convertor)
    implementation (libs.glide)
    annotationProcessor (libs.glide)
    implementation(libs.security.crypto)
    implementation(libs.logging.interceptor)
    implementation(libs.shimmer)
    implementation(libs.dotlottie)
    implementation(libs.media3exoplayer)
    implementation(libs.media3ui)
    implementation(libs.media3exoplayerdash)
    implementation(libs.media3exoplayerhls)
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:custom-ui:12.1.0")
    implementation("com.cloudinary:cloudinary-android:3.0.2")




}