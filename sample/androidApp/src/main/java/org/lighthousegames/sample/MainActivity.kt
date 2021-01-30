package org.lighthousegames.sample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.lighthousegames.logging.logging
import org.lighthousegames.sample.shared.Greeting

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private val log = logging()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.i { "onCreate" }
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        findViewById<Button>(R.id.log_debug).setOnClickListener {
            log.debug { "debug level msg" }
        }

        findViewById<Button>(R.id.log_info).setOnClickListener {
            log.info { "info level msg" }
        }

        findViewById<Button>(R.id.crash).setOnClickListener {
            try {
                throw RuntimeException("Non Fatal")
            } catch (e: Exception) {
                log.error(e) { "exception handled" }
            }
            throw RuntimeException("Crash here")
        }
    }
}
