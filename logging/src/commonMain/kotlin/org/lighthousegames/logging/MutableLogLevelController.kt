package org.lighthousegames.logging

interface MutableLogLevelController : LogLevelController {
    /**
     * Sets the log level to a particular level.
     * This log level and all less granular log levels will be enabled
     */
    fun setLogLevel(level: LogLevel)
}
