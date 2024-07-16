plugins {
    id("com.android.library")
    id("maven-publish")
    alias(libs.plugins.kotlin)
}

android {
    namespace = "concerrox.ripple"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        viewBinding = true
    }
}

publishing {
    publications {
        register<MavenPublication>("main") {
            groupId = "io.github.concerrox"
            artifactId = "ui.ripple"
            version = "1.0.0-SNAPSHOT"
            afterEvaluate {
                artifact(tasks.getByName("bundleReleaseAar"))
            }
//            pom {
//                name = "Ripple Components for Android"
//                description = ""
//                url = "https://github.com/ConcerroX/ripple-components-android"
//                inceptionYear = "2024"
////                artifact("$buildDir/outputs/aar/ui.ripple-components-release.aar")
//                scm {
//                    url = "https://github.com/ConcerroX/ripple-components-android"
//                    connection =
//                        "scm:git@https://github.com/ConcerroX/ripple-components-android.git"
//                    developerConnection = "scm:git@https://github.com/ConcerroX/ripple-components-android.git"
//                }
//                licenses {
//                    license {
//                        name = "The Apache Software License, Version 2.0"
//                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
//                        distribution = "repo"
//                        comments = "A business-friendly OSS license"
//                    }
//                }
//                developers {
//                    developer {
//                        id = "concerrox"
//                        name = "ConcerroX"
//                        email = "concerrox@outlook.com"
//                        url = "https://github.com/ConcerroX"
//                    }
//                }
//                issueManagement {
//                    system = "Github"
//                    url = "https://github.com/ConcerroX/ripple-components-android/issues"
//                }
//            }
        }
    }
}

dependencies {
    //noinspection UseTomlInstead
    implementation("com.google.android.material:material:1.11.0")
    //noinspection UseTomlInstead
    implementation("io.arrow-kt:arrow-core:1.2.1")
}

