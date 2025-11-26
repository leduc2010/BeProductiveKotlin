package com.duc.beproductivekotlin

import android.content.Context
import androidx.core.content.edit

class CommonUtils private constructor() {
    fun savePref(key: String?, value: String?) {
        val pref = App.instance.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        pref.edit { putString(key, value) }
    }

    fun getPref(key: String?): String? {
        val pref = App.instance.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        return pref.getString(key, "")
    }

    fun cleanPref(key: String?) {
        val pref = App.instance.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        pref.edit { remove(key) }
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"
        var instance: CommonUtils? = null
            get() {
                if (field == null) {
                    field = CommonUtils()
                }
                return field
            }
            private set
    }
}
