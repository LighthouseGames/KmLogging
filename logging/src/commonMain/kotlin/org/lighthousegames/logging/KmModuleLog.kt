package org.lighthousegames.logging

/**
 * Wrapper for KmLog that allows library modules to also use KmLogging and be able to enable or disable its logging independently
 * of the application that is also using KmLogging.
 *
 * Example usage with code implemented in ChartsLogging.kt
 * ```
 * object ChartsLogging {
 *     var enabled = true
 * }
 *
 * fun moduleLogging(tag: String? = null): KmModuleLog {
 *     // string passed into createTag should be the name of the class that this function is implemented in
 *     // if it is a top level function then the class name is the file name with Kt appended
 *     val t = tag ?: KmLogging.createTag("ChartsLoggingKt").first
 *     return KmModuleLog(logging(t), ChartsLogging::enabled)
 * }
 * ```
 */
class KmModuleLog(val log: KmLog, val isModuleLogging: () -> Boolean) {

    inline fun v(msg: () -> Any?) {
        if (isModuleLogging())
            log.verbose(msg)
    }

    inline fun v(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.verbose(tag, msg)
    }

    inline fun d(msg: () -> Any?) {
        if (isModuleLogging())
            log.debug(msg)
    }

    inline fun d(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.debug(tag, msg)
    }

    inline fun i(msg: () -> Any?) {
        if (isModuleLogging())
            log.info(msg)
    }

    inline fun i(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.info(tag, msg)
    }

    inline fun w(msg: () -> Any?) {
        if (isModuleLogging())
            log.warn(msg)
    }

    inline fun w(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (isModuleLogging())
            log.warn(err, tag, msg)
    }

    inline fun e(msg: () -> Any?) {
        if (isModuleLogging())
            log.error(msg)
    }

    inline fun e(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (isModuleLogging())
            log.error(err, tag, msg)
    }
}