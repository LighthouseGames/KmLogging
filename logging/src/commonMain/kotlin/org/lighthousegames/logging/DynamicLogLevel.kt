package org.lighthousegames.logging

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object DynamicLogLevel : MutableLogLevelController {
    private var isLoggingVerbose = true
    private var isLoggingDebug = true
    private var isLoggingInfo = true
    private var isLoggingWarning = true
    private var isLoggingError = true
    private var logLevel = LogLevel.Verbose.level

    override fun isLoggingVerbose() = isLoggingVerbose
    override fun isLoggingDebug() = isLoggingDebug
    override fun isLoggingInfo() = isLoggingInfo
    override fun isLoggingWarning() = isLoggingWarning
    override fun isLoggingError() = isLoggingError

    /**
     * Sets the log level to a particular level and all less granular level logging will be also enabled
     */
    override fun setLogLevel(level: LogLevel) {
        setLogLevel(level.level)
    }

    /**
     * Sets the log level using a bitmask. This allows individual control over each log level type.
     * @param level int composed from or'ing VERBOSE_MASK, DEBUG_MASK, INFO_MASK, WARNING_MASK, ERROR_MASK
     */
    fun setLogLevel(level: Int) {
        if (level != logLevel) {
            logLevel = level
            isLoggingError = level >= ERROR_MASK
            isLoggingWarning = level >= WARN_MASK
            isLoggingInfo = level >= INFO_MASK
            isLoggingDebug = level >= DEBUG_MASK
            isLoggingVerbose = level >= VERBOSE_MASK
            KmLogging.setupLoggingFlags()
        }
    }
}
