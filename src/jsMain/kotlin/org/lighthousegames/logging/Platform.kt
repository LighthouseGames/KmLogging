package org.lighthousegames.logging

actual object Platform {
    actual val isDebug: Boolean = true
    actual val isIOS: Boolean = false
    actual val isAndroid: Boolean = false
    actual val isJS: Boolean = true
    actual val name: String = "JavaScript"
    actual val version: Double = 1.0        //TODO
    actual val versionName: String = "1.0"  //TODO
}
