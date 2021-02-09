package org.lighthousegames.sample.shared

import org.lighthousegames.logging.Platform
import org.lighthousegames.logging.TimingLog
import org.lighthousegames.logging.logging

class Greeting {
    fun greeting(): String {
        val tlog = TimingLog("Test Timing")
        log.i { "greeting()" }
        val greet = "Hello, ${Platform.name} ${Platform.versionName} (${Platform.version})"
        tlog.debug { "greeting" }
        var s = "0"
        for (i in 1..1_000_000) {
            s = (s.toInt() + 1).toString()
            if (i == 700_000)
                tlog.verbose { "i = $i" }
            else if (i == 500_000)
                tlog.verbose { "i = $i" }
            else if (i == 200_000)
                tlog.verbose { "i = $i" }
        }
        tlog.verbose { "i = $s" }
        tlog.debug { "i" }
        tlog.finish()
        return greet
    }

    companion object {
        val log = logging()
    }
}
