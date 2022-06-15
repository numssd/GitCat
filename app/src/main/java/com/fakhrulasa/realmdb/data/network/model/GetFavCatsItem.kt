package com.fakhrulasa.realmdb.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetFavCatsItem(
    @SerialName("image")
    val image: Image
)

@Serializable
class Image(
    @SerialName("url")
    val url: String,

    @SerialName("id")
    val id: String

)