pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("plugin.serialization")
        kotlin("multiplatform")
//        id("org.jetbrains.dokka") version "1.4.20"
    }
}

include(":logging")
include(":sample:shared", ":sample:androidApp", ":sample:webApp")
