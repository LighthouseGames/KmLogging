plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    ios {
    }
    js(BOTH) {
        browser {
        }
    }
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("co.touchlab:stately-concurrency:1.1.10")
            }
        }

        val androidMain by getting

        val jvmMain by getting {
            dependencies {
                implementation("org.slf4j:slf4j-api:1.7.30")
            }
        }

        val iosMain by getting

        val jsMain by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 14
        consumerProguardFiles("proguard.txt")
    }
}

tasks {
    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn(dokkaHtml)
        from(dokkaHtml.get().outputDirectory)
    }
}

extra["artifactId"] = "kmlogging"
extra["artifactVersion"] = "1.1.1"
extra["libraryName"] = "KmLogging: Kotlin Multiplatform Logging"
extra["libraryDescription"] = "KmLogging is a high performance, extensible and easy to use logging library for Kotlin Multiplatform development"
extra["gitUrl"] = "https://github.com/LighthouseGames/KmLogging"

apply(from = "publish.gradle.kts")

