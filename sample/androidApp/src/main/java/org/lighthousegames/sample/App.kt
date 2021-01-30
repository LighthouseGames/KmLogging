package org.lighthousegames.sample

import android.app.Application
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lighthousegames.logging.*

class App : Application() {
    private val log = logging()

    private val remoteConfig by lazy { Firebase.remoteConfig }

    override fun onCreate() {
        super.onCreate()
        KmLogging.setLogLevel(LogLevel.Verbose)
        KmLogging.setLoggers(PlatformLogger(DynamicLogLevel), CrashlyticsLogger())
        log.info { "onCreate" }

        setupRemoteConfig()
    }

    private fun setupRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 60 else 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        GlobalScope.launch(Dispatchers.IO) {
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    val logLevel = remoteConfig["log_level"].asString()
                    Log.i("App", "log_level: $logLevel")
                    val level = LogLevel.valueOf(logLevel)
                    KmLogging.setLogLevel(level)
                    log.i { "Config params updated: $updated. LogLevel: $level" }
                } else {
                    log.w { "Fetch failed" }
                }
            }
        }
    }
}