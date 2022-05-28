package com.example.ecomkt.app.util

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context) {
    private val mySession: SharedPreferences =
        context.getSharedPreferences("filename", Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = mySession.edit()

    fun saveToken(token: String) {
        editor.putString(Common.USER_TOKEN, token)
        editor.apply()
    }

    fun saveExpiryTime(expiryTime: Int) {
        editor.putInt(Common.KEY_EXPIRY, expiryTime)
        editor.apply()
    }

    fun getExpiryTime() = mySession.getString(Common.KEY_EXPIRY, null)
    fun getUserToken() = mySession.getString(Common.USER_TOKEN, null)
}