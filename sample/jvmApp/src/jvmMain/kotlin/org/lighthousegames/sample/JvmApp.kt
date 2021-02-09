package org.lighthousegames.sample

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.core.util.StatusPrinter
import org.lighthousegames.logging.Platform
import org.lighthousegames.logging.logging
import org.slf4j.LoggerFactory

class TestClass {
    private val log = logging()

    init {
        log.verbose { "TestClass ctor" }
    }

    fun foo() {
        log.i { "foo()" }
        TestClass2().foo2()
        log.i { "foo() done" }
    }
}

class TestClass2 {
    fun foo2() {
        log.d { "foo2()" }
        log.verbose { "this will not output because logback does not have this class configured at the TRACE level" }
    }

    companion object {
        val log = logging()
    }
}

class JvmApp {
    companion object {
        val log = logging()

        @JvmStatic
        fun main(args: Array<String>) {
            // print logback's internal status
            StatusPrinter.print(LoggerFactory.getILoggerFactory() as LoggerContext)
            log.i { "main()" }
            val test = TestClass()
            test.foo()
            Platform.setRelease(true)
            // following should not generate logging
            test.foo()
        }
    }
}
