package org.lighthousegames.logging

/**
 * Enum representing a log level where that level and all succeeding less granular levels are enabled.
 * Verbose is the lowest level means all levels are enabled and Error means only Error
 */
enum class LogLevel(val level: Int) {
    Verbose(ERROR_MASK or WARN_MASK or INFO_MASK or DEBUG_MASK or VERBOSE_MASK),
    Debug(ERROR_MASK or WARN_MASK or INFO_MASK or DEBUG_MASK),
    Info(ERROR_MASK or WARN_MASK or INFO_MASK),
    Warn(ERROR_MASK or WARN_MASK),
    Error(ERROR_MASK),
    Off(0)
}

const val ERROR_MASK = 0x1
const val WARN_MASK = 0x2
const val INFO_MASK = 0x4
const val DEBUG_MASK = 0x8
const val VERBOSE_MASK = 0x10