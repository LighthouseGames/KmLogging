package org.lighthousegames.logging

expect object Platform {
    fun isDebug(): Boolean
    val isIOS: Boolean
    val isAndroid: Boolean
    val isJS: Boolean
    val isJVM: Boolean
    val name: String
    val version: Double
    val versionName: String
    val timeNanos: Long
}