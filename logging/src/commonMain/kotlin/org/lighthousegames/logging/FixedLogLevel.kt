package org.lighthousegames.logging

class FixedLogLevel(val isLogging: () -> Boolean) : LogLevelController {
    override fun isLoggingVerbose() = isLogging()
    override fun isLoggingDebug() = isLogging()
    override fun isLoggingInfo() = isLogging()
    override fun isLoggingWarning() = isLogging()
    override fun isLoggingError() = isLogging()
}