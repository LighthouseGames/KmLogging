package org.lighthousegames.logging

import android.util.Log

actual open class PlatformLogger actual constructor(actual val logLevel: LogLevelController) : Logger, TagProvider, LogLevelController by logLevel {

    actual override fun verbose(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    actual override fun debug(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    actual override fun info(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    actual override fun warn(tag: String, msg: String, t: Throwable?) {
        Log.w(tag, msg, t)
    }

    actual override fun error(tag: String, msg: String, t: Throwable?) {
        Log.e(tag, msg, t)
    }

    actual override fun createTag(fromClass: String?): Pair<String, String> {
//        println("createTag($fromClass)")
        var clsName = "NA"
        val stack = Thread.currentThread().stackTrace.map { it.className }
        stack.forEachIndexed { index, stackEntry ->
//            println("stack $stackEntry")
            if (stackEntry.endsWith("KmLogKt") && stack.size > index) {
                clsName = stack[index + 1]
            }
            if (fromClass != null && stackEntry.endsWith(fromClass) && stack.size > index) {
                clsName = stack[index + 1]
            }
        }
//        println("cls: $clsName")
        return Pair(getTag(clsName), clsName)
    }

    private fun getTag(className: String): String {
        var pos = className.lastIndexOf('.')
        pos = if (pos < 0) 0 else pos + 1
        return className.substring(pos)
    }
}