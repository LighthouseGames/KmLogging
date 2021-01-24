package org.lighthousegames.logging

class PrintLogger(logLevel: LogLevelController) : Logger, LogLevelController by logLevel {

    override fun verbose(tag: String?, msg: String) {
        println("${tag ?: ""} $msg")
    }

    override fun debug(tag: String?, msg: String) {
        println("${tag ?: ""} $msg")
    }

    override fun info(tag: String?, msg: String) {
        println("${tag ?: ""} $msg")
    }

    override fun warn(tag: String?, msg: String, t: Throwable?) {
        println("${tag ?: ""} $msg $t")
    }

    override fun error(tag: String?, msg: String, t: Throwable?) {
        println("${tag ?: ""} $msg $t")
    }
}