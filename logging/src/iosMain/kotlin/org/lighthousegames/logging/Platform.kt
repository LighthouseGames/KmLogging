package org.lighthousegames.logging

import platform.UIKit.UIDevice
import kotlin.native.Platform.isDebugBinary

actual object Platform {
    actual val isDebug: Boolean = isDebugBinary
    actual val isIOS: Boolean = true
    actual val isAndroid: Boolean = false
    actual val isJS: Boolean = false
    actual val name: String = UIDevice.currentDevice.systemName
    actual val version: Double
    actual val versionName: String
    init {
        val ver = UIDevice.currentDevice.systemVersion
        versionName = ver
        version = try {
            ver.toDouble()
        } catch (e:Exception) {
            try {
                ver.substringBeforeLast('.').toDouble()
            } catch (e: Exception) {
                ver.substringBefore('.').toDouble()
            }
        }
    }
}
