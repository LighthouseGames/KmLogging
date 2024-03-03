package org.lighthousegames.logging

import co.touchlab.stately.concurrency.AtomicReference
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@SharedImmutable
internal val logFactory: AtomicReference<LogFactory?> = AtomicReference(null)

@SharedImmutable
private val loggers: AtomicReference<List<Logger>> = AtomicReference(listOf<Logger>(PlatformLogger(FixedLogLevel(true))))

@ThreadLocal
object KmLogging {
    var isLoggingVerbose = true
    var isLoggingDebug = true
    var isLoggingInfo = true
    var isLoggingWarning = true
    var isLoggingError = true

    init {
        setupLoggingFlags()
    }

    /**
     * Clears the existing loggers and adds in the new ones.
     */
    fun setLoggers(vararg loggers: Logger) {
        val list = ArrayList<Logger>(loggers.size)
        for (logger in loggers) {
            list.add(logger)
        }
        org.lighthousegames.logging.loggers.set(list)
        setupLoggingFlags()
    }

    fun setLogFactory(factory: LogFactory?) {
        logFactory.set(factory)
    }

    /**
     * Removes all loggers. For use by native platforms that cannot use the vararg setLoggers.
     */
    fun clear() {
        loggers.set(emptyList())
    }

    /**
     * Adds loggers. For use by native platforms that cannot use the vararg setLoggers.
     */
    fun addLogger(logger: Logger) {
        val list = ArrayList(loggers.get())
        list.add(logger)
        loggers.set(list)
        setupLoggingFlags()
    }

    /**
     * Removes loggers. For use by native platforms that cannot use the vararg setLoggers.
     */
    fun removeLogger(logger: Logger) {
        val list = ArrayList(loggers.get())
        list.remove(logger)
        loggers.set(list)
        setupLoggingFlags()
    }

    /*
     * Convenience method that sets the log level of the PlatformLogger if there is one.
     */
    fun setLogLevel(level: LogLevel) {
        val list = ArrayList(loggers.get())
        list.forEachIndexed { index, logger ->
            if (logger is PlatformLogger) {
                list[index] = PlatformLogger(VariableLogLevel(level))
            }
        }
        loggers.set(list)
        setupLoggingFlags()
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
        for (logger in loggers.get()) {
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

    fun verbose(tag: String, msg: String) {
        for (logger in loggers.get()) {
            if (logger.isLoggingVerbose())
                logger.verbose(tag, msg)
        }
    }

    fun debug(tag: String, msg: String) {
        for (logger in loggers.get()) {
            if (logger.isLoggingDebug())
                logger.debug(tag, msg)
        }
    }

    fun info(tag: String, msg: String) {
        for (logger in loggers.get()) {
            if (logger.isLoggingInfo())
                logger.info(tag, msg)
        }
    }

    fun warn(tag: String, msg: String, t: Throwable? = null) {
        for (logger in loggers.get()) {
            if (logger.isLoggingWarning())
                logger.warn(tag, msg, t)
        }
    }

    fun error(tag: String, msg: String, t: Throwable? = null) {
        for (logger in loggers.get()) {
            if (logger.isLoggingError())
                logger.error(tag, msg, t)
        }
    }

    fun createTag(fromClass: String? = null): Pair<String, String> {
        for (logger in loggers.get()) {
            if (logger is TagProvider)
                return logger.createTag(fromClass)
        }
        return Pair("", "")
    }

    override fun toString(): String {
        return "KmLogging(verbose:$isLoggingVerbose debug:$isLoggingDebug info:$isLoggingInfo warn:$isLoggingWarning error:$isLoggingError) ${loggers.get()}"
    }
}