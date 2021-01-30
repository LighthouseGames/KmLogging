package org.lighthousegames.logging

expect class PlatformLogger(logLevel: LogLevelController) : Logger {
    override fun verbose(tag: String, msg: String)
    override fun debug(tag: String, msg: String)
    override fun info(tag: String, msg: String)
    override fun warn(tag: String, msg: String, t: Throwable?)
    override fun error(tag: String, msg: String, t: Throwable?)
    fun createTag(): String
    val logLevel: LogLevelController
}