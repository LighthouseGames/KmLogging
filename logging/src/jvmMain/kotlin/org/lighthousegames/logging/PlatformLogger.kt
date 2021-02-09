package org.lighthousegames.logging

actual open class PlatformLogger actual constructor(actual val logLevel: LogLevelController) : Logger, TagProvider, LogLevelController by logLevel {

    init {
        if (KmLogging.logFactory != null)
            println("WARNING: existing logFactory is being overwritten by JVM PlatformLogger. Was ${KmLogging.logFactory}")
        KmLogging.logFactory = Slf4jLogFactory()
    }

    // Note: these methods don't do anything because the logging output happens from Slf4Log class

    actual override fun verbose(tag: String, msg: String) {
    }

    actual override fun debug(tag: String, msg: String) {
    }

    actual override fun info(tag: String, msg: String) {
    }

    actual override fun warn(tag: String, msg: String, t: Throwable?) {
    }

    actual override fun error(tag: String, msg: String, t: Throwable?) {
    }

    actual override fun createTag(fromClass: String?): Pair<String, String> {
        val clsName = findClassName(fromClass)
        return Pair(getTag(clsName), clsName)
    }

    companion object {
        fun findClassName(fromClass: String?): String {
            var clsName = ""
            val stack = Thread.currentThread().stackTrace.map { it.className }
            stack.forEachIndexed { index, stackEntry ->
//                println("stack $stackEntry")
                if (stackEntry.endsWith("KmLogKt") && stack.size > index) {
                    clsName = stack[index + 1]
                }
                if (fromClass != null && stackEntry.endsWith(fromClass) && stack.size > index) {
                    clsName = stack[index + 1]
                }
            }
            return clsName
        }

        private fun getTag(fullClassName: String): String {
            var pos = fullClassName.lastIndexOf('.')
            pos = if (pos < 0) 0 else pos + 1
            return fullClassName.substring(pos)
        }
    }
}