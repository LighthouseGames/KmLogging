pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
    plugins {
        id("org.jetbrains.dokka") version "1.9.10"
        kotlin("multiplatform") version "1.9.20"
    }
}

include(":logging")
include(":sample:shared", ":sample:androidApp", ":sample:webApp", ":sample:jvmApp")
