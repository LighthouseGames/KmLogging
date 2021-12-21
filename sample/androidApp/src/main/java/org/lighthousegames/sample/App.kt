package org.lighthousegames.sample

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.*

class App : Application() {
    private val log = logging()

    private val remoteConfig by lazy { Firebase.remoteConfig }

    override fun onCreate() {
        super.onCreate()
        KmLogging.setLoggers(PlatformLogger(VariableLogLevel(LogLevel.Verbose)), CrashlyticsLogger())
        log.info { "onCreate ${Thread.currentThread()}" }

        setupRemoteConfig()
        logOnThread()
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
                    Log.i("App", "log_level: $logLevel ${Thread.currentThread()}")
                    val level = LogLevel.valueOf(logLevel)
                    KmLogging.setLogLevel(level)
                    log.i { "Config params updated: $updated. LogLevel: $level" }
                } else {
                    log.w { "Fetch failed" }
                }
            }
        }
    }

    private fun logOnThread() {
        log.d { "logOnThread ${Thread.currentThread()}" }
        runBlocking {

        }
        GlobalScope.launch(Dispatchers.IO) {
            log.d { "logOnThread coroutine: ${Thread.currentThread()} $this ${this.coroutineContext}" }
        }
        GlobalScope.launch(Dispatchers.Main) {
            log.d { "logOnThread coroutine main ${Thread.currentThread()} $this ${this.coroutineContext}" }
        }
    }
}