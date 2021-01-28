# Kotlin Multiplatform Logging  <img src="https://upload.wikimedia.org/wikipedia/commons/7/74/Kotlin-logo.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/d/db/Android_robot_2014.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/6/66/Apple_iOS_logo.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/6/6a/JavaScript-logo.png" width="30">

[![ver](https://img.shields.io/bintray/v/lighthousegames/multiplatform/kmlogging?color=blue&label=JCenter)](https://bintray.com/lighthousegames/multiplatform/kmlogging/_latestVersion)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.4.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
![kmm](https://img.shields.io/badge/Multiplatform-Android%20iOS%20JS-blue)
[![License](https://img.shields.io/badge/License-Apache--2.0-blue)](http://www.apache.org/licenses/LICENSE-2.0)

Kotlin multiplatform logging library targeting Android, iOS, and JS.

## Features

* No configuration necessary. Automatically uses the builtin PlatformLogger which uses Log in Android, os_log in iOS, and console in JS. By default logging is enabled in debug builds and disabled in release builds
* Very little overhead when logging is disabled. When disabled, only an if condition of a boolean variable is evaluated. Building the message string and running the code to calculate it is not executed.
* Can  easily add additional logging such as Crashlytics or remotely configurable logging
* Each logger can log at a different level.
* All platforms can use the same set of loggers by configuring in common code or can use different ones on each platform by configuring in platform specific code.


## Setup

The library is available from the JCenter repository with the current version of ![ver](https://img.shields.io/bintray/v/lighthousegames/multiplatform/kmlogging?color=blue&label=JCenter)
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

Create an instance of the `KmLog` class or create it using the convenience function `logging()`. 
On Android and iOS the class where it was instantiated will be used as the tag in the logs. For JS or when a specific tag is desired it can be supplied i.e `val log = logging("mytag")` or `val log = KmLog("mytag")`

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

### Turn on logging for release builds
If logging is desired for release builds. Use the supplied `PlatformLogger` and supply it a log level controller that is enabled for all log levels.

```kotlin
KmLogging.setLoggers(PlatformLogger(FixedLogLevel(true)))
```

Notes: 

* When calling `KmLogging.setLoggers()` the existing loggers are removed and the supplied ones are added in. 
* If the existing ones should remain then `KmLogging.addLoggers()` should be used.
* `PlatformLogger` uses Log on Android, os_log on iOS, and console on JS.

### Miscellaneous
If a custom logger is created that changes the log level dynamically such as from a Remote Config change then `KmLogging.setupLoggingFlags()` should be called when the logger's log levels were changed to calculate which log levels are enabled. KmLogging maintains variables for each log level corresponding to whether any logger is enabled at that level. This is done for performance reason so only a single boolean check will be done at runtime to minimize the overhead when running in production.

### Logging to another system such as Crashlytics
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

// at App creation time configure logging
// use addLogger to keep the existing loggers

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
