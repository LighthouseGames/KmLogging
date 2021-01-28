# KmLogging
[ ![ver](https://img.shields.io/bintray/v/lighthousegames/multiplatform/kmlogging?color=blue&label=jcenter) ](https://bintray.com/lighthousegames/multiplatform/kmlogging/_latestVersion)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.20-blue.svg?logo=kotlin)](http://kotlinlang.org)
![kmm](https://img.shields.io/badge/multiplatform-Android%20iOS%20JS-blue)
[![License](https://img.shields.io/badge/license-Apache--2.0-blue)](http://www.apache.org/licenses/LICENSE-2.0)

Kotlin multiplatform logging

## Features:

* very little overhead when logging is disabled. Building the message string and running the code to calculate it is not executed when disabled.
* easy to disable logging in production/release builds
* can add alternative or additional logging such as Crashlytics or remotely configurable logging
* each logger can have a different log level
* support for different loggers on each platform
* no configuration necessary when using the builtin PlatformLogger

### Setup

```kotlin
commonMain {
    dependencies {
        api("org.lighthousegames:kmlogging:$logging_version")
    }
}
```

## Usage

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
```kotlin
KmLogging.setLoggers(PlatformLogger(FixedLogLevel(true)))
```

### Log to another system such as Crashlytics
Let's say we only want to send the more important logs to Crashlytics to give some context to crashes so we only want to log info level and above. 
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


## Usage in iOS Swift 
By default the kotlin multiplatform toolchain will not export all KmLogging classes and those that are will be prefaced with Logging. 
So if you want to use classes from swift code you will need to direct the plugin to export the logging library in your build.gradle.kts:

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
