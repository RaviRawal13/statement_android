import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

val key = gradleLocalProperties(rootDir).getProperty("apiKey", "")

val releaseKeyAlias: String = gradleLocalProperties(rootDir).getProperty("RELEASE_KEY_ALIAS", "")
val releaseKeyPassword: String =
    gradleLocalProperties(rootDir).getProperty("RELEASE_KEY_PASSWORD", "")
val releaseStorePassword: String =
    gradleLocalProperties(rootDir).getProperty("RELEASE_STORE_PASSWORD", "")
val releaseStoreFilePath: String =
    gradleLocalProperties(rootDir).getProperty("RELEASE_STORE_FILE", "")
val androidBuildMinSdkVersion: String by project
val androidBuildSdkTarget: String by project
val androidCompileSdkVersion: String by project
val appVersionNameProd: String by project
val appVersionCode: String by project
val appVersionNameDev: String by project

android {
    compileSdkVersion(androidCompileSdkVersion.toInt())
    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = "com.ravirawal.statement"
        minSdkVersion(androidBuildMinSdkVersion.toInt())
        targetSdkVersion(androidBuildSdkTarget.toInt())
        versionCode = appVersionCode.toInt()
        setProperty("archivesBaseName", "${applicationId}-(${versionCode})")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", key)
    }

//    signingConfigs {
//        create("release") {
//            storeFile(file(releaseStoreFilePath))
//            storePassword(releaseStorePassword)
//
//            keyAlias(releaseKeyAlias)
//            keyPassword(releaseKeyPassword)
//
//            isV1SigningEnabled = true
//            isV2SigningEnabled = true
//        }
//    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
//            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions("default")

    productFlavors {
        create("dev") {
            applicationId = "com.ravirawal.statement.dev"
            dimension("default")
            versionName = appVersionNameProd + appVersionNameDev
        }
        create("prod") {
            dimension("default")
            applicationId = "com.ravirawal.statement"
            versionName = appVersionNameProd
        }
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")

    implementation("androidx.core:core-ktx:1.3.2")

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.fragment:fragment:1.2.5")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.1")
    implementation("androidx.navigation:navigation-runtime-ktx:2.3.1")

    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    implementation("androidx.room:room-runtime:2.2.5")
    implementation("androidx.room:room-ktx:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")
    annotationProcessor("androidx.room:room-compiler:2.2.5")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.google.code.gson:gson:2.8.6")

    implementation("com.google.android.material:material:1.2.1")

    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")

    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("androidx.work:work-runtime-ktx:2.4.0")


    implementation("androidx.browser:browser:1.2.0")

    implementation("androidx.paging:paging-runtime:2.1.2")

    implementation("com.google.dagger:dagger:2.29.1")
    implementation("com.google.dagger:dagger-android:2.29.1")
    implementation("com.google.dagger:dagger-android-support:2.29.1")
    kapt("com.google.dagger:dagger-compiler:2.29.1")
    kapt("com.google.dagger:dagger-android-processor:2.29.1")

    implementation("com.facebook.shimmer:shimmer:0.5.0")

    testImplementation("junit:junit:4.13.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    testImplementation("org.mockito:mockito-core:3.3.3")
    androidTestImplementation("org.mockito:mockito-android:2.25.0")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.0")

    testImplementation("androidx.arch.core:core-testing:2.1.0")

//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")
//    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")
}
