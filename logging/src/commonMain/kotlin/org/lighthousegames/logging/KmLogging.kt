package org.lighthousegames.logging

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object KmLogging {
    private val loggers = ArrayList<Logger>()

    private var isLoggingVerbose = true
    private var isLoggingDebug = true
    private var isLoggingInfo = true
    private var isLoggingWarning = true
    private var isLoggingError = true
    private var logLevel: LogLevel? = null

    init {
        loggers.add(PlatformLogger(FixedLogLevel(Platform.isDebug)))
    }

    /**
     * Clears the existing loggers and adds in the new ones.
     */
    fun setLoggers(vararg loggers: Logger) {
        this.loggers.clear()
        for (logger in loggers) {
            this.loggers.add(logger)
            setLogLevelFor(logger)
        }
        setupLoggingFlags()
    }

    /**
     * Removes all loggers. For use by native platforms that cannot use the vararg setLoggers.
     */
    fun clear() {
        loggers.clear()
    }

    /**
     * Adds loggers. For use by native platforms that cannot use the vararg setLoggers.
     */
    fun addLogger(logger: Logger) {
        loggers.add(logger)
        setLogLevelFor(logger)
        setupLoggingFlags()
    }

    private fun setLogLevelFor(logger: Logger) {
        val currentLevel = logLevel
        if (currentLevel != null) {
            if (logger is MutableLogLevelController)
                logger.setLogLevel(currentLevel)
            else if (logger is PlatformLogger && logger.logLevel is MutableLogLevelController)
                logger.logLevel.setLogLevel(currentLevel)
        }
    }

    /**
     * Convenience method that sets the log level of all loggers whose LogLevelController is a MutableLogLevelController.
     * Any logger that is added after this setting will also get set to this log level.
     * Setting the level to null will remove the log level setting and all current loggers and those subsequently added
     * will not have its log level changed.
     */
    fun setLogLevel(level: LogLevel?) {
        logLevel = level
        if (level != null) {
            for (logger in loggers) {
                setLogLevelFor(logger)
            }
            setupLoggingFlags()
        }
    }

    /**
     * Recalculate all of the flags associated with whether each log level is on or off.
     * Useful for when a logger's log level is dynamically set such as by a remote config service.
     */
    fun setupLoggingFlags() {
        isLoggingVerbose = false
        isLoggingDebug = false
        isLoggingInfo = false
        isLoggingWarning = false
        isLoggingError = false
        for (logger in loggers) {
            if (logger.isLoggingVerbose())
                isLoggingVerbose = true
            if (logger.isLoggingDebug())
                isLoggingDebug = true
            if (logger.isLoggingInfo())
                isLoggingInfo = true
            if (logger.isLoggingWarning())
                isLoggingWarning = true
            if (logger.isLoggingError())
                isLoggingError = true
        }
    }

    fun isLoggingVerbose() = isLoggingVerbose
    fun isLoggingDebug() = isLoggingDebug
    fun isLoggingInfo() = isLoggingInfo
    fun isLoggingWarning() = isLoggingWarning
    fun isLoggingError() = isLoggingError

    fun verbose(tag: String, msg: String) {
        for (logger in loggers) {
            if (logger.isLoggingVerbose())
                logger.verbose(tag, msg)
        }
    }

    fun debug(tag: String, msg: String) {
        for (logger in loggers) {
            if (logger.isLoggingDebug())
                logger.debug(tag, msg)
        }
    }

    fun info(tag: String, msg: String) {
        for (logger in loggers) {
            if (logger.isLoggingInfo())
                logger.info(tag, msg)
        }
    }

    fun warn(tag: String, msg: String, t: Throwable? = null) {
        for (logger in loggers) {
            if (logger.isLoggingWarning())
                logger.warn(tag, msg, t)
        }
    }

    fun error(tag: String, msg: String, t: Throwable? = null) {
        for (logger in loggers) {
            if (logger.isLoggingError())
                logger.error(tag, msg, t)
        }
    }

    fun createTag(): String {
        for (logger in loggers) {
            if (logger is PlatformLogger)
                return logger.createTag()
        }
        return ""
    }
}