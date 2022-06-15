package com.fakhrulasa.realmdb.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatsFavEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var image_id: String,

    @ColumnInfo(name = "url")
    var url: String,
)