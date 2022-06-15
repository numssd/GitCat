package com.fakhrulasa.realmdb.data.room

import com.fakhrulasa.realmdb.ui.Repository
import com.fakhrulasa.realmdb.data.room.model.CatsFavEntity
import com.fakhrulasa.realmdb.ui.models.CatItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.sql.SQLException
import java.util.ArrayList

class RoomRepository(
    private val db: CatsFavDatabase,

    ) : Repository {
    override suspend fun getNewImage(): CatItem? {
        return null
    }

    override suspend fun saveFavCats(cat: CatItem) {
        safeRoomCall {
            val catsFavEntity = CatsFavEntity(cat.imageId, cat.url)
            db.catsFavDao().insertFavCat(catsFavEntity)
        }
    }

    override suspend fun getFavCats(): List<CatItem>? {
        return safeRoomCall {
            val cats = db.catsFavDao().getAllCats()
            val catItemList = ArrayList<CatItem>()
            for (i in cats.indices) {
                val imageId = cats[i].image_id
                val url = cats[i].url
                catItemList.add(CatItem(imageId, url))
            }
            catItemList
        }
    }

    private suspend fun <R> safeRoomCall(action: suspend () -> R): R? {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext action()

            } catch (e: SQLException) {
                return@withContext null
            } catch (e: IOException) {
                return@withContext null
            }
        }
    }
}
