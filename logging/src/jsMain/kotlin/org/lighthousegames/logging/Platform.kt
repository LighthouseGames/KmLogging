package org.lighthousegames.logging

import kotlin.js.Date

actual object Platform {
    private var isRelease = false

    actual fun isDebug(): Boolean = !isRelease
    actual val isIOS: Boolean = false
    actual val isAndroid: Boolean = false
    actual val isJS: Boolean = true
    actual val isJVM: Boolean = false
    actual val name: String = "JavaScript"
    actual val version: Double = 1.0        //TODO
    actual val versionName: String = "1.0"  //TODO
    actual val timeNanos: Long
        get() = Date().getMilliseconds() * 1_000_000L

    fun setRelease(isRelease: Boolean) {
        this.isRelease = isRelease
        // PlatformLogger is using isDebug to determine if it is logging so need to recalculate flags
        KmLogging.setupLoggingFlags()
    }
}
