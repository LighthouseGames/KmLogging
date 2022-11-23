package org.lighthousegames.logging

/**
 * Add logs containing how long it took to do each section. The logs will be added at the debug log level so the debug log level must be enabled.
 * Verbose level logging creates a new section which is also totaled by the next debug level logs. If only logging debug level then the verbose
 * level logs are not sent to the logs.
 * Usage:
 * val log = TimingLog("work")
 * ... do work A (taking 5 ms)
 * log.debug { "A" }
 * ... do work B 1 (taking 1 ms)
 * log.verbose { "B 1" }
 * ... do work B 2 (taking 3 ms)
 * log.verbose { "B 2" }
 * log.debug { "B" }
 * log.finish()
 *
 * If the log level is set to debug or higher then this would output:
 * D/Tag: work: begin TimingLog
 * D/Tag: work:     5 ms A
 * D/Tag: work:     4 ms B
 * D/Tag: work: end 9 ms
 * If the log level is set to verbose then this would output:
 * D/Tag: work: begin TimingLog
 * D/Tag: work:     5 ms A
 * V/Tag: work:           1 ms B 1
 * V/Tag: work:           3 ms B 2
 * D/Tag: work:     4 ms B
 * D/Tag: work: end 9 ms
 */
class TimingLog(private val label: String, tag: String? = null) {

    data class Timing(val time: Long, val msg: String, val isVerbose: Boolean)

    private val tagName = tag ?: KmLogging.createTag("TimingLog").first
    private val timing = ArrayList<Timing>()

    init {
        add("", false)
    }

    fun add(msg: String, isVerbose: Boolean) {
        timing.add(Timing(Platform.timeNanos, msg, isVerbose))
    }

    inline fun verbose(msg: () -> Any?) {
        if (KmLogging.isLoggingVerbose) {
            add(msg().toString(), true)
        }
    }

    inline fun debug(msg: () -> Any?) {
        if (KmLogging.isLoggingDebug) {
            add(msg().toString(), false)
        }
    }

    fun finish() {
        if (KmLogging.isLoggingDebug || timing.size > 1) {
            KmLogging.debug(tagName, "$label: begin TimingLog")
            val first = timing[0].time
            var now = first
            var prevDebug = first
            var prev = first
            for (i in 1 until timing.size) {
                val t = timing[i]
                now = t.time
                val msg = t.msg
                val indent = if (t.isVerbose) "         " else "     "
                val timeStr = if (t.isVerbose) msDiff(now, prev) else msDiff(now, prevDebug)
                KmLogging.debug(tagName, "$label:$indent$timeStr $msg")
                prev = now
                if (!t.isVerbose)
                    prevDebug = now
            }
            KmLogging.debug(tagName, "$label: end ${msDiff(now, first)}")
        }
        reset()
    }

    fun reset() {
        timing.clear()
        add("", false)
    }

    private fun msDiff(nano1: Long, nano2: Long): String {
        val diff = (nano1 - nano2) / 1_000_000f
        return "${truncateDecimal(diff, 3)} ms"
    }

    companion object {
        fun truncateDecimal(num: Float, decPlaces: Int): String {
            val str = num.toString()
            val dotPos = str.indexOf('.')
            return if (dotPos >= 0 && dotPos < str.length - decPlaces)
                str.substring(0..(dotPos + decPlaces))
            else
                str
        }
    }
}