plugins {
    id("com.android.library")
    kotlin("multiplatform")
//    id("org.jetbrains.dokka")
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    ios {
    }
    js(LEGACY) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    sourceSets {
        val commonMain by getting

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
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(14)
        consumerProguardFiles("proguard.txt")
    }
}

extra["artifactId"] = "kmlogging"
extra["artifactVersion"] = "1.0.0"
extra["libraryName"] = "Kotlin Multiplatform Logging"
extra["libraryDescription"] = "Kotlin Multiplatform Logging"
extra["gitUrl"] = "https://gitlab.com/olekdia/common/libraries/multiplatform-common"

apply(from = "publish.gradle.kts")
