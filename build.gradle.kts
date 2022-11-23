
buildscript {
    repositories {
        gradlePluginPortal()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
    }
}


allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}
