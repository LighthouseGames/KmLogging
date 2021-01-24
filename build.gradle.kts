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

    sourceSets {
        val commonMain by getting {
        }

        val androidMain by getting {
        }

        val iosMain by getting

        val jsMain by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
}

extra["artifactId"] = "kmlogging"
extra["artifactVersion"] = "0.5.2"
extra["libraryName"] = "Kotlin Multiplatform Logging"
extra["libraryDescription"] = "Kotlin Multiplatform Logging"
extra["gitUrl"] = "https://gitlab.com/olekdia/common/libraries/multiplatform-common"

apply(from = "publish.gradle.kts")
