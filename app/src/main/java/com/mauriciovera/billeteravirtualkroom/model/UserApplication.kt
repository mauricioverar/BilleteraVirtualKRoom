package com.mauriciovera.billeteravirtualkroom.model

import android.app.Application

class UserApplication: Application() {
    companion object {
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}