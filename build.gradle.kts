
buildscript {
    repositories {
        gradlePluginPortal()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.4")
    }
}


allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}
