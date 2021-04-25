package com.android.pxapp.util

import android.app.Application
import android.content.SharedPreferences
import com.android.pxapp.util.Prefs.defaultPreference


val prefs: SharedPreferences by lazy {
    App.prefs!!
}

class App: Application()
{
    companion object {
        var prefs: SharedPreferences? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = defaultPreference(applicationContext)

    }
}