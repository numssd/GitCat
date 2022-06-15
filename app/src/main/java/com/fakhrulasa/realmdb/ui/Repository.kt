package com.fakhrulasa.realmdb.ui

import com.fakhrulasa.realmdb.ui.models.CatItem

interface Repository {
    suspend fun getFavCats(): List<CatItem>?
    suspend fun saveFavCats(cat: CatItem)
    suspend fun getNewImage(): CatItem?
}