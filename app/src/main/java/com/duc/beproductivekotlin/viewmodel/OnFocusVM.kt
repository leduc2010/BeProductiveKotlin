package com.duc.beproductivekotlin.viewmodel

import androidx.lifecycle.MutableLiveData

class OnFocusVM : BaseViewModel() {
    var pokemon: String? = ""

    private var currentIndex = 0
    val currentTheme: MutableLiveData<String?> = MutableLiveData<String?>()
    private var themeFiles: Array<String>? = null

    fun setThemeFiles(themeFiles: Array<String>?) {
        this.themeFiles = themeFiles
        if (themeFiles != null && themeFiles.isNotEmpty()) {
            currentTheme.value = themeFiles[0]
        }
    }

    fun nextTheme() {
        if (themeFiles == null || themeFiles!!.isEmpty()) return
        currentIndex = (currentIndex + 1) % themeFiles!!.size
        currentTheme.value = themeFiles!![currentIndex]
    }

    fun previousTheme() {
        if (themeFiles == null || themeFiles!!.isEmpty()) return
        currentIndex = (currentIndex - 1 + themeFiles!!.size) % themeFiles!!.size
        currentTheme.value = themeFiles!![currentIndex]
    }
}
