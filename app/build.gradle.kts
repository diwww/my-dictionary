plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
//    kotlin("kapt") version "1.5.31"
}


android {
    compileSdkVersion(31)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
        applicationId("org.maxsur.mydictionary")
        versionName("1.0")
        versionCode(1)
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
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
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("com.github.terrakok:cicerone:7.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.google.dagger:dagger:2.38.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.github.moxy-community:moxy:2.2.2")
    implementation("com.github.moxy-community:moxy-androidx:2.2.2")
    implementation("com.github.moxy-community:moxy-ktx:2.2.2")
    kapt("com.github.moxy-community:moxy-compiler:2.2.2")
    kapt("com.google.dagger:dagger-compiler:2.38.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}