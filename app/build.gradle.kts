plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
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
        val pUseStubRepository: String by project
        val pToken: String by project
        val pFolderId: String by project

        getByName("debug") {
            buildConfigField("boolean", "useStubRepository", pUseStubRepository)
            buildConfigField("String", "token", pToken)
            buildConfigField("String", "folderId", pFolderId)
        }
        getByName("release") {
            buildConfigField("boolean", "useStubRepository", pUseStubRepository)
            buildConfigField("String", "token", pToken)
            buildConfigField("String", "folderId", pFolderId)

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
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.github.terrakok:cicerone:7.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.google.dagger:dagger:2.38.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.github.moxy-community:moxy:2.2.2")
    implementation("com.github.moxy-community:moxy-androidx:2.2.2")
    implementation("com.github.moxy-community:moxy-ktx:2.2.2")
    implementation("com.google.code.gson:gson:2.9.0")
    kapt("com.github.moxy-community:moxy-compiler:2.2.2")
    kapt("com.google.dagger:dagger-compiler:2.38.1")
    implementation("androidx.room:room-runtime:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")
    implementation("androidx.room:room-rxjava2:2.4.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}