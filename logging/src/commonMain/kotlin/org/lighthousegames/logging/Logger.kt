package org.lighthousegames.logging

interface Logger : LogLevelController {
    fun verbose(tag: String?, msg: String)
    fun debug(tag: String?, msg: String)
    fun info(tag: String?, msg: String)
    fun warn(tag: String?, msg: String, t: Throwable? = null)
    fun error(tag: String?, msg: String, t: Throwable? = null)
}