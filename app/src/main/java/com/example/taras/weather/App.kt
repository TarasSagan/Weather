package com.example.taras.weather

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import android.support.multidex.MultiDex



class App: MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}