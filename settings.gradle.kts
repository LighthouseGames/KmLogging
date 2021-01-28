pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("plugin.serialization") version "1.4.0"
        kotlin("multiplatform") version "1.4.21"
        id("org.jetbrains.dokka") version "1.4.20"
    }
}

include(":logging")
include(":sample:shared", ":sample:androidApp", ":sample:webApp")
