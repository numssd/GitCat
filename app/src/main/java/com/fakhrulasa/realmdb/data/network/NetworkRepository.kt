package com.fakhrulasa.realmdb.data.network

import com.fakhrulasa.realmdb.ui.Repository
import com.fakhrulasa.realmdb.data.network.api.CatAPI.API
import com.fakhrulasa.realmdb.data.network.api.CatAPI.API_FAVOURITE
import com.fakhrulasa.realmdb.data.network.api.CatAPI.API_KEY
import com.fakhrulasa.realmdb.data.network.model.CatItemDto
import com.fakhrulasa.realmdb.data.network.model.FavCatItemDto
import com.fakhrulasa.realmdb.data.network.model.GetFavCatsItem
import com.fakhrulasa.realmdb.ui.models.CatItem
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.parsing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class NetworkRepository(
    private val client: HttpClient,

    ) : Repository {
    override suspend fun getNewImage(): CatItem? {
        return safeNetworkCall {
            val response: HttpResponse = client.get(API)
            val cat = response.body<List<CatItemDto>>().first()
            CatItem(cat.id, cat.url)
        }
    }

    override suspend fun getFavCats(): List<CatItem>? {
        return safeNetworkCall {

            val response: HttpResponse = client.get(API_FAVOURITE) {
                headers {
                    append("x-api-key", API_KEY)
                }
            }
            val cats = response.body<List<GetFavCatsItem>>()
            val catItemList = ArrayList<CatItem>()
            for (i in cats.indices) {
                val imageId = cats[i].image.id
                val url = cats[i].image.url
                catItemList.add(CatItem(imageId, url))
            }
            catItemList
        }
    }

    override suspend fun saveFavCats(cat: CatItem) {
        safeNetworkCall {
            client.post(API_FAVOURITE) {
                headers {
                    append("x-api-key", API_KEY)
                    append("Content-Type", "application/json")
                }
                setBody(FavCatItemDto(cat.imageId))
            }
        }
    }

    private suspend fun <R> safeNetworkCall(action: suspend () -> R): R? {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext action()

            } catch (e: IOException) {
                return@withContext null

            } catch (e: ParseException) {
                return@withContext null
            } catch (e: IllegalArgumentException) {
                return@withContext null
            }
        }
    }
}