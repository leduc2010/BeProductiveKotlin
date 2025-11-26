package com.duc.beproductivekotlin.viewmodel

import com.duc.beproductivekotlin.App
import com.duc.beproductivekotlin.db.entities.Explore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ExploreViewModel : BaseViewModel() {
    var listPopular: List<Explore> = listOf()
    var listNewArticle: List<Explore> = listOf()

    init {
        runBlocking {
            listPopular = withContext(Dispatchers.IO) {
                App.instance.db.getExploreDao().getAllExploresByType("Popular")
            }
            listNewArticle = withContext(Dispatchers.IO) {
                App.instance.db.getExploreDao().getAllExploresByType("New articles")
            }
        }
    }}
