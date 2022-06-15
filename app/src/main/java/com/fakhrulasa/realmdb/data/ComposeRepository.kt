package com.fakhrulasa.realmdb.data

import com.fakhrulasa.realmdb.ui.Repository
import com.fakhrulasa.realmdb.data.network.NetworkRepository
import com.fakhrulasa.realmdb.data.room.RoomRepository
import com.fakhrulasa.realmdb.ui.models.CatItem

class ComposeRepository(
    private val roomRepository: RoomRepository,
    private val networkRepository: NetworkRepository
) : Repository {
    override suspend fun getFavCats(): List<CatItem>? {
        val cats = roomRepository.getFavCats()
        return if (cats == null) {
            val networkCats = networkRepository.getFavCats()
            networkCats
        } else {
            cats
        }
    }

    override suspend fun saveFavCats(cat: CatItem) {
        networkRepository.saveFavCats(cat)
        roomRepository.saveFavCats(cat)
    }

    override suspend fun getNewImage(): CatItem? {
        return networkRepository.getNewImage()
    }
}