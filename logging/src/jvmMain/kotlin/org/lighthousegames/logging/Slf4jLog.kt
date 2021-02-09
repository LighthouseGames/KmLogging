package org.lighthousegames.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Slf4jLog(tag: String, className: String) : KmLog(tag) {

    private val logger: Logger = LoggerFactory.getLogger(className)

    override fun verbose(tag: String, msg: String) {
        super.verbose(tag, msg)
        logger.trace(msg)
    }

    override fun debug(tag: String, msg: String) {
        super.debug(tag, msg)
        logger.debug(msg)
    }

    override fun info(tag: String, msg: String) {
        super.info(tag, msg)
        logger.info(msg)
    }

    override fun warn(tag: String, msg: String, t: Throwable?) {
        super.warn(tag, msg, t)
        if (t != null)
            logger.warn(msg, t)
        else
            logger.warn(msg)
    }

    override fun error(tag: String, msg: String, t: Throwable?) {
        super.error(tag, msg, t)
        if (t != null)
            logger.error(msg, t)
        else
            logger.error(msg)
    }
}