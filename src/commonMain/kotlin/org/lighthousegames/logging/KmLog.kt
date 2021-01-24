package org.lighthousegames.logging

class KmLog(tag: String? = null) {
    val tagName = tag ?: KmLogging.createTag()

    inline fun verbose(msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose())
            KmLogging.verbose(tagName, msg().toString())
    }

    inline fun verbose(tag: String, msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose())
            KmLogging.verbose(tag, msg().toString())
    }

    inline fun v(tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose())
            KmLogging.verbose(tag ?: tagName, msg().toString())
    }

    inline fun debug(msg: () -> Any?) {
        if (KmLogging.isLoggingDebug())
            KmLogging.debug(tagName, msg().toString())
    }

    inline fun debug(tag: String, msg: () -> Any?) {
        if (KmLogging.isLoggingDebug())
            KmLogging.debug(tag, msg().toString())
    }

    inline fun d(tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingDebug())
            KmLogging.debug(tag ?: tagName, msg().toString())
    }

    inline fun info(msg: () -> Any?) {
        if (KmLogging.isLoggingInfo())
            KmLogging.info(tagName, msg().toString())
    }

    inline fun info(tag: String, msg: () -> Any?) {
        if (KmLogging.isLoggingInfo())
            KmLogging.info(tag, msg().toString())
    }

    inline fun i(tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingInfo())
            KmLogging.info(tag ?: tagName, msg().toString())
    }

    inline fun warn(msg: () -> Any?) {
        if (KmLogging.isLoggingWarning())
            KmLogging.warn(tagName, msg().toString())
    }

    inline fun warn(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingWarning())
            KmLogging.warn(tag ?: tagName, msg().toString(), err)
    }

    inline fun w(err: Throwable? = null, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingWarning())
            KmLogging.warn(tag ?: tagName, msg().toString(), err)
    }

    inline fun error(msg: () -> Any?) {
        if (KmLogging.isLoggingError())
            KmLogging.error(tagName, msg().toString())
    }

    inline fun error(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingError())
            KmLogging.error(tag ?: tagName, msg().toString(), err)
    }

    inline fun e(err: Throwable? = null, tag: String? = null, msg: () -> Any?) {
        if (KmLogging.isLoggingError())
            KmLogging.error(tag ?: tagName, msg().toString(), err)
    }
}

fun logging(tag: String? = null): KmLog {
    return KmLog(tag)
}


