package com.example.mindorkscoroutine.utils

import android.content.SharedPreferences

inline fun SharedPreferences.edit(
    commit: Boolean = false,
    action: SharedPreferences.Editor.() -> Unit

) {
    val editor = edit()
    action(editor)
    if (commit) editor.commit() else editor.apply()
}


private const val KEY_TOKEN = "token"

class PreferencesManager(private val preferences: SharedPreferences) {

    fun saveToken(token: String) {
        var dummy = 3
        preferences.edit {
            putString(KEY_TOKEN, token)
        }
    }
}