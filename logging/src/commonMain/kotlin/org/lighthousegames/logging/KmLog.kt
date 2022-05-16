package org.lighthousegames.logging

open class KmLog(tag: String) {
    val tagName = tag

    constructor() : this(KmLogging.createTag("KmLog").first)

    open fun verbose(tag: String?, msg: String) {
        if (KmLogging.isLoggingVerbose)
            KmLogging.verbose(tag ?: tagName, msg)
    }
    inline fun verbose(msg: String) = verbose(null, msg)
    inline fun verbose(msg: () -> Any?) = verbose(msg().toString())
    inline fun verbose(tag: String?, msg: () -> Any?) = verbose(tag, msg().toString())

    open fun debug(tag: String?, msg: String) {
        if (KmLogging.isLoggingDebug)
            KmLogging.debug(tag ?: tagName, msg)
    }
    inline fun debug(msg: String) = debug(tagName, msg)
    inline fun debug(msg: () -> Any?) = debug(msg().toString())
    inline fun debug(tag: String?, msg: () -> Any?) = debug(tag, msg().toString())

    open fun info(tag: String?, msg: String) {
        if (KmLogging.isLoggingInfo)
            KmLogging.info(tag ?: tagName, msg)
    }
    inline fun info(msg: String) = info(null, msg)
    inline fun info(msg: () -> Any?) = info(msg().toString())
    inline fun info(tag: String?, msg: () -> Any?) = info(tag, msg().toString())

    open fun warn(tag: String?, msg: String, err: Throwable? = null) {
        if (KmLogging.isLoggingWarning)
            KmLogging.warn(tag ?: tagName, msg)
    }
    inline fun warn(msg: String, err: Throwable? = null) = warn(null, msg, err)
    inline fun warn(msg: () -> Any?) = warn(null, msg().toString(), null)
    inline fun warn(tag: String? = null, err: Throwable? = null, msg: () -> Any?) = warn(tag, msg().toString(), err)

    open fun error(tag: String?, msg: String, err: Throwable? = null) {
        if (KmLogging.isLoggingWarning)
            KmLogging.error(tag ?: tagName, msg)
    }
    inline fun error(msg: String, err: Throwable? = null) = error(null, msg, err)
    inline fun error(msg: () -> Any?) = error(null, msg().toString(), null)
    inline fun error(tag: String? = null, err: Throwable? = null, msg: () -> Any?) = error(tag, msg().toString(), err)


    inline fun v(tag: String?, msg: String) = verbose(tag, msg)
    inline fun v(msg: String) = verbose(msg)
    inline fun v(msg: () -> Any?) = verbose(msg)
    inline fun v(tag: String?, msg: () -> Any?) = verbose(tag, msg)

    inline fun d(tag: String?, msg: String) = debug(tag, msg)
    inline fun d(msg: String) = debug(msg)
    inline fun d(msg: () -> Any?) = debug(msg)
    inline fun d(tag: String?, msg: () -> Any?) = debug(tag, msg)

    inline fun i(tag: String?, msg: String) = info(tag, msg)
    inline fun i(msg: String) = info(msg)
    inline fun i(msg: () -> Any?) = info(msg)
    inline fun i(tag: String?, msg: () -> Any?) = info(tag, msg)

    inline fun w(tag: String?, msg: String, err: Throwable? = null) = warn(tag, msg, err)
    inline fun w(msg: String, err: Throwable? = null) = warn(msg, err)
    inline fun w(msg: () -> Any?) = warn(msg)
    inline fun w(tag: String? = null, err: Throwable? = null, msg: () -> Any?) = warn(tag, err, msg)


    inline fun e(tag: String?, msg: String, err: Throwable? = null) = error(tag, msg, err)
    inline fun e(msg: String, err: Throwable? = null) = error(msg, err)
    inline fun e(msg: () -> Any?) = error(msg)
    inline fun e(tag: String? = null, err: Throwable? = null, msg: () -> Any?) = error(tag, err, msg)
}

/**
 * Create a logging object. This is the primary entry point for logging and should be called once for each file, class or object.
 * For classes a val can be created either as a private member of the class or as a member of the companion object.
 * @param tag string to be used instead of the calculated tag based on the class name or file name.
 */
fun logging(tag: String? = null): KmLog {
    if (tag != null)
        return logFactory.get()?.createKmLog(tag, tag) ?: KmLog(tag)
    val (tagCalculated, className) = KmLogging.createTag("KmLog")
    return logFactory.get()?.createKmLog(tagCalculated, className) ?: KmLog(tagCalculated)
}
