package com.fakhrulasa.realmdb.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class FavCatItemDto(
    @SerialName("image_id")
    val image_id: String,
)