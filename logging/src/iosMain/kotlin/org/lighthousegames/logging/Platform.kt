package org.lighthousegames.logging

import platform.CoreFoundation.*
import platform.UIKit.UIDevice


actual object Platform {
    actual val isIOS: Boolean = true
    actual val isAndroid: Boolean = false
    actual val isJS: Boolean = false
    actual val isJVM: Boolean = false
    actual val name: String = UIDevice.currentDevice.systemName
    actual val version: Double
    actual val versionName: String
    actual val timeNanos: Long
        get() = (CFAbsoluteTimeGetCurrent() * 1_000_000_000).toLong()

    init {
        val ver = UIDevice.currentDevice.systemVersion
        versionName = ver
        version = try {
            ver.toDouble()
        } catch (e: Exception) {
            try {
                ver.substringBeforeLast('.').toDouble()
            } catch (e: Exception) {
                ver.substringBefore('.').toDouble()
            }
        }
    }
}
