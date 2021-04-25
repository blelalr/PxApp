package com.android.pxapp.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object Prefs {
    private const val DELETE_MESSAGE_SET = "DELETE_MESSAGE_SET"

    fun defaultPreference(context: Context): SharedPreferences = context.getSharedPreferences("App", Context.MODE_PRIVATE)


    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    inline fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
        val key = pair.first
        val value = pair.second
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Set<*> -> putStringSet(key, HashSet<String>())
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }


    var SharedPreferences.deleteMessageSet : HashSet<String>
        get() = getStringSet(DELETE_MESSAGE_SET, HashSet()) as HashSet<String>
        set(value) {
            editMe {
                Log.d("esther", "SET : $value")
                it.remove(DELETE_MESSAGE_SET)
                it.clear()
                it.putStringSet(DELETE_MESSAGE_SET, value).commit()
            }
        }


}

