package org.lighthousegames.logging

/**
 * All logging levels are either enabled or disabled.
 */
class FixedLogLevel(private val isLogging: Boolean) : LogLevelController {
    override fun isLoggingVerbose() = isLogging
    override fun isLoggingDebug() = isLogging
    override fun isLoggingInfo() = isLogging
    override fun isLoggingWarning() = isLogging
    override fun isLoggingError() = isLogging
}