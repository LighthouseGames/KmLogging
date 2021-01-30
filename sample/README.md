# Kotlin Multiplatform Logging Sample Apps

## Android App demonstrates

* Adding a logger to send logs to Crashlytics
* Modifying builtin PlatformLogger to log based on Remote Config log level value

## Setup of Crashlytics and Remote Config

_IMPORTANT_: To build the sample app and see Crashlytics and Remote Config in operation, you will need to add your own google-services.json into the androidApp directory.
 
In the Firebase console:
* add an app with a packageId of org.lighthousegames.sample.
* In the Remote Config tab, add a parameter labelled log_level and set its default value to a string matching the enum names for LogLevel

Notes:
* Remote Config log levels would be useful if for example you wanted to turn logging on in release builds 
but only for certain users such as QA testers or for all environments prior to production.
* Remote Config doesn't have a way to tell you when they fetch new values so if you wanted a more
dynamic level that would change while the app is running then you would have to call KmLogging.setupLoggingFlags()
from a timer that would run at the same time interval as you have the Remote Config minimumFetchIntervalInSeconds set to. 
* Other paid feature flag systems such as LaunchDarkly, ConfigCat, etc have ways to know when config changes occur
so you will know when to call KmLogging.setupLoggingFlags() to update the log levels.

