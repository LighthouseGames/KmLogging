package org.lighthousegames.logging

import android.os.Build

actual object Platform {
    actual val isIOS: Boolean = false
    actual val isAndroid: Boolean = true
    actual val isJS: Boolean = false
    actual val isJVM: Boolean = false
    actual val name: String = "Android"
    actual val version: Double = Build.VERSION.SDK_INT.toDouble()
    actual val versionName: String = Build.VERSION.RELEASE
    actual val timeNanos: Long
        get() = System.nanoTime()
}
