package org.lighthousegames.logging

import android.util.Log

//private val baseLoggingClassName = object {}::class.java.enclosingClass?.name ?: "PlatformLogger"

actual class PlatformLogger actual constructor(logLevel: LogLevelController) : Logger,
    LogLevelController by logLevel {

    actual override fun verbose(tag: String?, msg: String) {
        Log.v(tag ?: "", msg)
    }

    actual override fun debug(tag: String?, msg: String) {
        Log.d(tag ?: "", msg)
    }

    actual override fun info(tag: String?, msg: String) {
        Log.i(tag ?: "", msg)
    }

    actual override fun warn(tag: String?, msg: String, t: Throwable?) {
        Log.w(tag ?: "", msg, t)
    }

    actual override fun error(tag: String?, msg: String, t: Throwable?) {
        Log.e(tag ?: "", msg, t)
    }

    actual fun createTag(): String {
        var clsName = ""
        val stack = Thread.currentThread().stackTrace.map { it.className }
        stack.forEachIndexed { index, stackEntry ->
//                println("stack $stackEntry")
            if (stackEntry.endsWith("KmLog") && stack.size > index) {
                clsName = getClassName(stack[index + 1])
            }
            if (stackEntry.endsWith("KmLogKt") && stack.size > index) {
                clsName = getClassName(stack[index + 1])
            }
        }
        return clsName
    }

    private fun getClassName(stackEntry: String): String {
        var pos = stackEntry.lastIndexOf('.')
        pos = if (pos < 0) 0 else pos + 1
        return stackEntry.substring(pos)
    }
}