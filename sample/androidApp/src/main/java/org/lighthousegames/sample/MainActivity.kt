package org.lighthousegames.sample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.lighthousegames.logging.TimingLog
import org.lighthousegames.logging.logging
import org.lighthousegames.sample.shared.Greeting

class MainActivity : AppCompatActivity() {

    private val log = logging()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tlog = TimingLog("onCreate")
        setContentView(R.layout.activity_main)
        tlog.debug { "setContentView" }

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = Greeting().greeting()
        tlog.debug { "greet" }

        findViewById<Button>(R.id.log_debug).setOnClickListener {
            log.debug { "debug level msg" }
        }

        findViewById<Button>(R.id.log_info).setOnClickListener {
            log.info { "info level msg" }
        }

        findViewById<Button>(R.id.log_warn).setOnClickListener {
            try {
                throw RuntimeException("Non Fatal")
            } catch (e: Exception) {
                log.warn(e) { "exception handled" }
            }
        }

        findViewById<Button>(R.id.log_error).setOnClickListener {
            try {
                throw RuntimeException("Non Fatal")
            } catch (e: Exception) {
                log.error(e) { "exception handled" }
            }
        }

        findViewById<Button>(R.id.crash).setOnClickListener {
            throw RuntimeException("Crash here")
        }

        findViewById<Button>(R.id.crash).setOnClickListener {
            try {
                throw RuntimeException("Non Fatal")
            } catch (e: Exception) {
                log.error(e) { "exception handled" }
            }
            throw RuntimeException("Crash here")
        }
        tlog.debug { "findViewById" }
        tlog.finish()
    }
}
