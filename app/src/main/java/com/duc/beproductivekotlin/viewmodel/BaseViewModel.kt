package com.duc.beproductivekotlin.viewmodel

import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {
    companion object {
        private val TAG: String = BaseViewModel::class.java.getName()
    }
}