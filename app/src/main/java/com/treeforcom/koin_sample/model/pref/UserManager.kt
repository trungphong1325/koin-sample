package com.treeforcom.koin_sample.model.pref

import android.content.Context.MODE_PRIVATE
import android.content.Context
import com.treeforcom.koin_sample.utils.Constant.KEY_TOKEN

object UserManager {
    private const val prefName = "userManager"
    fun saveToken(context: Context, token: String) {
        val editor = context.getSharedPreferences(prefName, MODE_PRIVATE).edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences(prefName, MODE_PRIVATE).getString(KEY_TOKEN, "")
    }
}