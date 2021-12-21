package org.lighthousegames.logging

open class KmLog(tag: String) {
    val tagName = tag

    constructor() : this(KmLogging.createTag("KmLog").first)

    inline fun verbose(msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose)
            verboseApi(tagName, msg().toString())
    }

    inline fun verbose(tag: String, msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose)
            verboseApi(tag, msg().toString())
    }

    inline fun v(tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose)
            verboseApi(tag ?: tagName, msg().toString())
    }

    inline fun debug(msg: () -> Any?) {
        if (KmLogging.isLoggingDebug)
            debugApi(tagName, msg().toString())
    }

    inline fun debug(tag: String, msg: () -> Any?) {
        if (KmLogging.isLoggingDebug)
            debugApi(tag, msg().toString())
    }

    inline fun d(tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingDebug)
            debugApi(tag ?: tagName, msg().toString())
    }

    inline fun info(msg: () -> Any?) {
        if (KmLogging.isLoggingInfo)
            infoApi(tagName, msg().toString())
    }

    inline fun info(tag: String, msg: () -> Any?) {
        if (KmLogging.isLoggingInfo)
            infoApi(tag, msg().toString())
    }

    inline fun i(tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingInfo)
            infoApi(tag ?: tagName, msg().toString())
    }

    inline fun warn(msg: () -> Any?) {
        if (KmLogging.isLoggingWarning)
            warnApi(tagName, msg().toString(), null)
    }

    inline fun warn(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingWarning)
            warnApi(tag ?: tagName, msg().toString(), err)
    }

    inline fun w(err: Throwable? = null, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingWarning)
            warnApi(tag ?: tagName, msg().toString(), err)
    }

    inline fun error(msg: () -> Any?) {
        if (KmLogging.isLoggingError)
            errorApi(tagName, msg().toString(), null)
    }

    inline fun error(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingError)
            errorApi(tag ?: tagName, msg().toString(), err)
    }

    inline fun e(err: Throwable? = null, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingError)
            errorApi(tag ?: tagName, msg().toString(), err)
    }

    @PublishedApi
    internal fun verboseApi(tag: String, msg: String) = verbose(tag, msg)

    @PublishedApi
    internal fun debugApi(tag: String, msg: String) = debug(tag, msg)

    @PublishedApi
    internal fun infoApi(tag: String, msg: String) = info(tag, msg)

    @PublishedApi
    internal fun warnApi(tag: String, msg: String, t: Throwable?) = warn(tag, msg, t)

    @PublishedApi
    internal fun errorApi(tag: String, msg: String, t: Throwable?) = error(tag, msg, t)

    protected open fun verbose(tag: String, msg: String) {
        KmLogging.verbose(tag, msg)
    }

    protected open fun debug(tag: String, msg: String) {
        KmLogging.debug(tag, msg)
    }

    protected open fun info(tag: String, msg: String) {
        KmLogging.info(tag, msg)
    }

    protected open fun warn(tag: String, msg: String, t: Throwable? = null) {
        KmLogging.warn(tag, msg, t)
    }

    protected open fun error(tag: String, msg: String, t: Throwable? = null) {
        KmLogging.error(tag, msg, t)
    }
}

fun logging(tag: String? = null): KmLog {
    val (tagCalculated, className) = KmLogging.createTag("KmLog")
    val t = tag ?: tagCalculated
    return logFactory.get()?.createKmLog(t, className) ?: KmLog(t)
}


