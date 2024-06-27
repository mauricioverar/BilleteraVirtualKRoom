package com.mauriciovera.billeteravirtualkroom.model

import android.content.Context
import android.util.Log

class Prefs(private val context: Context) {

    val SHARED_NAME = "Mydtb"
    val SHARED_TOKEN = "token"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)
    fun saveToken(token: String) {
        Log.d("result token en prefs", token)
        storage.edit().putString(SHARED_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return storage.getString(SHARED_TOKEN, "")!!
    }

    fun wipe() {
        storage.edit().clear().apply()
    }

}