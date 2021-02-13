pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
//        kotlin("plugin.serialization") version "1.1.0"
//        kotlin("android") version "1.4.30"
//        kotlin("multiplatform") version "1.4.30"
        id("org.jetbrains.dokka") version "1.4.20"
    }
}

include(":logging")
include(":sample:shared", ":sample:androidApp", ":sample:webApp", ":sample:jvmApp")
