plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

android {
    namespace = "concerrox.ripple"
    compileSdk = 34
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
}

publishing {
    repositories {
        mavenCentral {
            credentials {

            }
        }
    }
    publications {
        register<MavenPublication>("main") {
            afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }
            groupId = "io.github.concerrox"
            artifactId = "ripple"
            version = "1.0.0-SNAPSHOT"
            pom {
                name = "Ripple Components for Android"
                description = ""
                url = "https://github.com/ConcerroX/ripple-components-android"
                inceptionYear = "2024"
//                artifact("$buildDir/outputs/aar/ripple-components-release.aar")
                scm {
                    url = "https://github.com/ConcerroX/ripple-components-android"
                    connection =
                        "scm:git@https://github.com/ConcerroX/ripple-components-android.git"
                    developerConnection = "scm:git@https://github.com/ConcerroX/ripple-components-android.git"
                }
                licenses {
                    license {
                        name = "The Apache Software License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                        distribution = "repo"
                        comments = "A business-friendly OSS license"
                    }
                }
                developers {
                    developer {
                        id = "concerrox"
                        name = "ConcerroX"
                        email = "concerrox@outlook.com"
                        url = "https://github.com/ConcerroX"
                    }
                }
                issueManagement {
                    system = "Github"
                    url = "https://github.com/ConcerroX/ripple-components-android/issues"
                }
            }
        }
    }
}

signing {
    sign(publishing.publications.getByName("main"))
}

dependencies {
    //noinspection UseTomlInstead
    implementation("com.google.android.material:material:1.11.0")
}