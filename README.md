# Kotlin Multiplatform Logging  <img src="https://upload.wikimedia.org/wikipedia/commons/7/74/Kotlin-logo.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/d/db/Android_robot_2014.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/6/66/Apple_iOS_logo.svg" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/6/6a/JavaScript-logo.png" width="30">  <img src="https://upload.wikimedia.org/wikipedia/commons/1/18/OpenJDK_logo.svg" width="80">

[![ver](https://img.shields.io/bintray/v/lighthousegames/multiplatform/kmlogging?color=blue&label=JCenter)](https://bintray.com/lighthousegames/multiplatform/kmlogging/_latestVersion)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.4.30-blue.svg?logo=kotlin)](http://kotlinlang.org)
![kmm](https://img.shields.io/badge/Multiplatform-Android%20iOS%20JS%20JVM-blue)
[![License](https://img.shields.io/badge/License-Apache--2.0-blue)](http://www.apache.org/licenses/LICENSE-2.0)

Kotlin multiplatform logging library targeting Android, iOS, JVM and JS.

## Features

* Uses the native logging facility on each platform: Log on Android, os_log on iOS, SLF4J on JVM and console on JavaScript.
* High performance. Very little overhead when logging is disabled. When disabled, only one boolean is evaluated and no function calls. Building the message string and running the code to calculate it is not executed.
* No configuration necessary. By default the PlatformLogger is enabled in debug builds and disabled in release builds.
* Can add additional loggers such as Crashlytics or replace/extend the builtin PlatformLogger with something else
* Can provide custom/configurable log level control on builtin PlatformLogger such as changing the log level from Remote Config
* Each logger can log at a different level.
* All platforms can use the same set of loggers by configuring in common code or can use different ones on each platform by configuring in platform specific code.

## Setup

The library is available from the JCenter repository with the current version of ![ver](https://img.shields.io/bintray/v/lighthousegames/multiplatform/kmlogging?color=blue&label=JCenter)
You should use at least version `1.4.30` of the kotlin multiplatform plugin. Place the following in the commonMain section.

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

## Setup for non-multiplatform

So, you want the best and easiest to use kotlin logging library but are not yet ready for multiplatform development then just include on the following in your `dependencies` section:

```gradle
    implementation("org.lighthousegames:logging-android:$logging_version")
    implementation("org.lighthousegames:logging-jvm:$logging_version")
    implementation("org.lighthousegames:logging-js:$logging_version")
```

## Usage

Create an instance of logging class by using the convenience function `logging()`. 
On Android, iOS and JVM the class from where `logging()` was called will be used as the tag in the logs. For JS or when a specific tag is desired it can be supplied i.e `val log = logging("mytag")` or `val log = KmLog("mytag")`

```kotlin
class MyClass {
    fun easyPeasy() {
        log.i { "use traditional Android short function name" }
    }

    fun easyPeasyLemonSqueesy() {
        log.info { "use longer more explicit function name" }
    }

    companion object {
        val log = logging()     // or can instantiate KmLog()
    }
}
```

## Configuration
With no configuration, logging is enabled for Android and iOS for all log levels in debug builds and disabled for release builds. For JavaScript and JVM, logging is enabled by default for all levels.

### Turn on logging for release builds
If logging is desired for release builds. Use the supplied `PlatformLogger` and supply it a log level controller that is enabled for all log levels.

```kotlin
KmLogging.setLoggers(PlatformLogger(FixedLogLevel(true)))
```

### Changing log levels after setup
If log levels are desired to be changed after they are setup then a `MutableLogLevelController` such as `DynamicLogLevel` should be used. 
The following example will change the `PlatformLogger` to allow it to have its log level changed at runtime. 
In the example below the level initially is set to Info level and then at some time later it is changed to Verbose level.

```kotlin
KmLogging.setLogLevel(LogLevel.Info)
KmLogging.setLoggers(PlatformLogger(DynamicLogLevel))
// level can be changed at any time ...
KmLogging.setLogLevel(LogLevel.Verbose)
```
## Miscellaneous

* When calling `KmLogging.setLoggers()` the existing loggers are removed and the supplied ones are added in. 
* If the existing ones should remain then `KmLogging.addLoggers()` should be used.
* `PlatformLogger` uses Log on Android, os_log on iOS, SLF4j on JVM and console on JS.
* If a custom logger is created that changes the log level dynamically such as from a Remote Config change then `KmLogging.setupLoggingFlags()` should be called when the logger's log levels were changed to calculate which log levels are enabled. KmLogging maintains variables for each log level corresponding to whether any logger is enabled at that level. This is done for performance reason so only a single boolean check will be done at runtime to minimize the overhead when running in production. The android sample app demonstrates this.

## Logging to another system such as Crashlytics
If logging is only desired at certain levels that can be setup. For example, if only the more important logs should be sent to Crashlytics to give some context to crashes then only log info level and above. 
That can be easily done by by defining and adding in a logger to do that. The sample android app implement this.

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

## Usage on JVM and JS
JVM and JS do not have a notion of release and debug builds. So, if you want the `PlatformLogger` to be disabled in a "release" environment 
then you can call `Platform.setRelease(true)` on these platforms and it will disable logging after that function call if you are using the default loggers i.e. no custom configuration.
It will be up to you to figure out the difference between a release environment and a debug environment.

## Usage on JVM
You will need to include a dependency on the logging library of your choice that is compatible with SLF4J. See the sample app which uses `logback`. 
Since SLF4J controls the log level using its own configurations it is advisable to retain the use of FixedLogLevel in the default configuration as the log level controller.
The log level controller in KmLogging controls the possibility of logging occurring with SLF4J controlling whether a particular log usage is output or not.

## Quick migration of Android Log calls
Once you have adopted KmLogging, what do you do with all your existing Android code that is using the Log class? 
The first quick and easy step is to switch all your code to using org.lighthousegames.logging.Log class which mimics the Android Log class 
but sends all output through KmLogging so you can turn it on and off at the same time as with all other KmLog usages and have all its benefits. 
To do this simply replace all occurrences of `import android.util.Log` in your code base with `import org.lighthousegames.logging.Log`

