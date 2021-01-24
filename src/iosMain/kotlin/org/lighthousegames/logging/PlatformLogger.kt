package org.lighthousegames.logging

import kotlinx.cinterop.ptr
import platform.Foundation.NSThread
import platform.darwin.*

actual class PlatformLogger actual constructor(logLevel: LogLevelController) : Logger,
    LogLevelController by logLevel {

    actual override fun verbose(tag: String?, msg: String) {
        _os_log_internal(
            __dso_handle.ptr,
            OS_LOG_DEFAULT,
            OS_LOG_TYPE_DEBUG,
            message("V", tag, msg)
        )
    }

    actual override fun debug(tag: String?, msg: String) {
        _os_log_internal(__dso_handle.ptr, OS_LOG_DEFAULT, OS_LOG_TYPE_INFO, message("D", tag, msg))
    }

    actual override fun info(tag: String?, msg: String) {
        _os_log_internal(
            __dso_handle.ptr,
            OS_LOG_DEFAULT,
            OS_LOG_TYPE_DEFAULT,
            message("I", tag, msg)
        )
    }

    actual override fun warn(tag: String?, msg: String, t: Throwable?) {
        _os_log_internal(
            __dso_handle.ptr,
            OS_LOG_DEFAULT,
            OS_LOG_TYPE_ERROR,
            message("W", tag, msg, t)
        )
    }

    actual override fun error(tag: String?, msg: String, t: Throwable?) {
        _os_log_internal(
            __dso_handle.ptr,
            OS_LOG_DEFAULT,
            OS_LOG_TYPE_FAULT,
            message("E", tag, msg, t)
        )
    }

    private fun message(level: String, tag: String?, msg: String, t: Throwable? = null): String {
        val str = if (tag == null) "$level: $msg" else "$level/$tag: $msg"
        return if (t == null) str else "$str $t"
    }

    actual fun createTag(): String {
        val stack = NSThread.callStackSymbols
        var clsName = ""
        stack.forEachIndexed { index, t ->
            val stackEntry = t.toString()
            val tag = getClassName(stackEntry)
//            println("tag: $tag stack: $stackEntry")

            if (stackEntry.contains("KmLog") && stack.size > index) {
                val nextEntry = stack[index + 1].toString()
                if (!nextEntry.contains("KmLog"))
                    clsName = getClassName(nextEntry)
            }
        }
        return clsName
    }

    private fun getClassName(stackEntry: String): String {
        var tag = ""
        if (stackEntry.contains(".Companion")) {
            tag = stackEntry.substringBefore(".Companion")
        } else if (stackEntry.contains("#<init>()")) {
            tag = stackEntry.substringBefore("#<init>()")
        }
        return tag.substring(tag.lastIndexOf(".") + 1)
    }
}