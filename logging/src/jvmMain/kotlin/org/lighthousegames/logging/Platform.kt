package org.lighthousegames.logging

actual object Platform {
    actual val isIOS: Boolean = false
    actual val isAndroid: Boolean = false
    actual val isJS: Boolean = false
    actual val isJVM: Boolean = true
    actual val name: String = "JVM"
    actual val version: Double = 1.0        //TODO
    actual val versionName: String = "1.0"  //TODO
    actual val timeNanos: Long
        get() = System.nanoTime()
}
