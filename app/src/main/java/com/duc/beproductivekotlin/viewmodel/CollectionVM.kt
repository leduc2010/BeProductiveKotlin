package com.duc.beproductivekotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import com.duc.beproductivekotlin.CommonUtils
import com.duc.beproductivekotlin.view.fragment.OnFocusFragment.Companion.POKEMON_COLLECTION

class CollectionVM : BaseViewModel() {
    val pokemonList = MutableLiveData<List<String>>()
    val unlockedList = MutableLiveData<List<Boolean>>()

    init {
        val savedString = CommonUtils.instance?.getPref(POKEMON_COLLECTION) ?: ""
        val list = if (savedString.isEmpty()) {
            List(12) { "" }
        } else {
            val savedList = savedString.split(",").map { it.trim() }
            savedList + List(12 - savedList.size) { "" }

        }
        pokemonList.value = list

        val unlockStatus = list.map { it.isNotEmpty() }
        unlockedList.value = unlockStatus
    }
}
