package org.lighthousegames.sample.shared

import org.lighthousegames.logging.Platform

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform.name} ${Platform.versionName} (${Platform.version})"
    }
}
