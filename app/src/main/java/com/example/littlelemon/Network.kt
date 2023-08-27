package com.example.littlelemon

import androidx.room.Entity
import io.ktor.http.ContentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MenuNetworkData(
        @SerialName("menu") var menu: List<MenuItemNetwork>
)


@Serializable
data class MenuItemNetwork(
        @SerialName("id") val id: Int,
        @SerialName("title") val title: String,
        @SerialName("description") val description: String,
        @SerialName("price") val price: String,
        @SerialName("image") val image: String,
        @SerialName("category") val category: String
)