package com.fakhrulasa.realmdb.ui

import android.app.Application
import androidx.room.Room
import com.facebook.drawee.backends.pipeline.Fresco
import com.fakhrulasa.realmdb.data.ComposeRepository
import com.fakhrulasa.realmdb.data.network.NetworkRepository
import com.fakhrulasa.realmdb.data.room.CatsFavDatabase
import com.fakhrulasa.realmdb.data.room.RoomRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class App : Application() {

    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        val db = Room.databaseBuilder(
            applicationContext,
            CatsFavDatabase::class.java, "cats_database"
        ).build()
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val roomRepository = RoomRepository(db)
        val networkRepository = NetworkRepository(client)
        repository = ComposeRepository(roomRepository, networkRepository)
    }
}