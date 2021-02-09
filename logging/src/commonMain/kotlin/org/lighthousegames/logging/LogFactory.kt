package org.lighthousegames.logging

interface LogFactory {
    fun createKmLog(tag: String, className: String): KmLog
}