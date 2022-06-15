package com.fakhrulasa.realmdb.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CatItemDto(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
)