package org.lighthousegames.logging

expect object Platform {
    val isDebug: Boolean
    val isIOS: Boolean
    val isAndroid: Boolean
    val isJS: Boolean
    val name: String
    val version: Double
    val versionName: String
}