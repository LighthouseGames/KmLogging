# Kotlin Multiplatform Logging  <img src="https://upload.wikimedia.org/wikipedia/commons/7/74/Kotlin-logo.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/d/db/Android_robot_2014.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/6/66/Apple_iOS_logo.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/6/6a/JavaScript-logo.png" width="30">

[![ver](https://img.shields.io/bintray/v/lighthousegames/multiplatform/kmlogging?color=blue&label= JCenter)](https://bintray.com/lighthousegames/multiplatform/kmlogging/_latestVersion)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.4.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
![kmm](https://img.shields.io/badge/Multiplatform-Android%20iOS%20JS-blue)
[![License](https://img.shields.io/badge/License-Apache--2.0-blue)](http://www.apache.org/licenses/LICENSE-2.0)

Kotlin multiplatform logging library targeting Android, iOS, and JS.

## Features

* very little overhead when logging is disabled. Building the message string and running the code to calculate it is not executed when disabled.
* easy to disable logging in production/release builds
* can add alternative or additional logging such as Crashlytics or remotely configurable logging
* each logger can have a different log level
* support for different loggers on each platform
* no configuration necessary when using the builtin PlatformLogger

## Setup

You need to use at least version `1.4.21` of the kotlin multiplatform plugin. Place the following in the commonMain section.

build.gradle.kts

```kotlin
sourceSets {
	val commonMain by getting {
		dependencies {
			api("org.lighthousegames:logging:$logging_version")
		}
	}
}
```
build.gradle

```gradle
sourceSets {
    commonMain {
        dependencies {
            api "org.lighthousegames:logging:$logging_version"
        }
    }
}
```

## Usage

The library is available from the JCenter repository with the current version ![ver](https://img.shields.io/bintray/v/lighthousegames/multiplatform/kmlogging?color=blue&label=JCenter)

```kotlin
class MyClass {
    fun easyPeasy() {
        log.i { "use traditional Android short function name" }
    }

    fun easyPeasyLemonSqueesy() {
        log.info { "use longer more explicit function name" }
    }

    companion object {
        val log = logging()
    }
}
```

## Configuration
With no configuration, logging is enabled for Android and iOS for all log levels in debug builds and disabled for release builds. For JavaScript, logging is enabled by default for all levels.

### Log for release builds
If logging is desired for release builds. Use the supplied `PlatformLogger` and supply it a log level controller that is enabled for all log levels.

```kotlin
KmLogging.setLoggers(PlatformLogger(FixedLogLevel(true)))
```

### Log to another system such as Crashlytics
If logging is only desired at certain levels that can be setup. For example, if only the more important logs should be sent to Crashlytics to give some context to crashes then only log info level and above. 
That can be easily done by by defining and adding in a logger to do that:

```kotlin
class CrashlyticsLogger : Logger {
    override fun verbose(tag: String?, msg: String) {}

    override fun debug(tag: String?, msg: String) {}

    override fun info(tag: String?, msg: String) {
        FirebaseCrashlytics.getInstance().log(msg)
    }

    override fun warn(tag: String?, msg: String, t: Throwable?) {
        FirebaseCrashlytics.getInstance().log(msg)
    }

    override fun error(tag: String?, msg: String, t: Throwable?) {
        FirebaseCrashlytics.getInstance().log(msg)
    }

    override fun isLoggingVerbose(): Boolean = false

    override fun isLoggingDebug(): Boolean = false

    override fun isLoggingInfo(): Boolean = true

    override fun isLoggingWarning(): Boolean = true

    override fun isLoggingError(): Boolean = true
}

// at App creation time configure logging:

KmLogging.addLogger(CrashlyticsLogger())
``` 


## Usage in iOS and Swift 
By default the kotlin multiplatform toolchain will not export all KmLogging classes and those that are will be prefaced with the stringLogging. 
If you want to use classes from Swift code you will need to direct the plugin to export the logging library in your `build.gradle.kts`:

```kotlin
    ios {
        binaries {
            framework {
                baseName = "my-shared-module-name"
                export("org.lighthousegames:logging:$logging_version")
            }
        }
    }
```
Note: logging must also be included as an api dependency. See https://kotlinlang.org/docs/reference/mpp-build-native-binaries.html

The code to figure out what class KmLog was instantiated from does not work from within Swift, so you will always want to pass in the class name:

```swift
class MyClass {
    let log = KmLog(tag: "MyClass")
}
```
