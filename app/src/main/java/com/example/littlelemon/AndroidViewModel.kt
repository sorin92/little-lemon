package com.example.littlelemon

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.littlelemon.ui.theme.green
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.concurrent.Flow

class AndroidViewModel(application : Application) : AndroidViewModel(application) {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType.Application.Json)
        }
    }

    var menuItemsEntities = mutableListOf<MenuItemEntity>()
    private val db = Room.databaseBuilder(application,AppDatabase::class.java, "app-database").build()

    var menuItemsList = db.menuItemDao().getMenuItems()
    var category by mutableStateOf("")
    var startersAlpha by mutableStateOf(0.17f)
    var mainsAlpha by mutableStateOf(0.17f)
    var dessertsAlpha by mutableStateOf(0.17f)
    var drinksAlpha by mutableStateOf(0.17f)

    fun resetAlphaValues(){
        startersAlpha = 0.17f
        mainsAlpha = 0.17f
        dessertsAlpha = 0.17f
        drinksAlpha = 0.17f
    }




    init{
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                val responseBody = response.bodyAsText()
                val menuNetworkData : MenuNetworkData = Json.decodeFromString(responseBody)
                val menuList = menuNetworkData.menu
                menuItemsEntities= menuList.map { menuItem ->
                    MenuItemEntity(
                            id = menuItem.id,
                            title = menuItem.title,
                            description = menuItem.description,
                            price = menuItem.price,
                            image = menuItem.image,
                            category = menuItem.category
                    )
                }.toMutableList()
                println(menuItemsEntities)

                viewModelScope.launch(Dispatchers.IO) {
                    db.menuItemDao().insertMenuItems(menuItemsEntities)
                }




            }catch (e: Exception) {
               println(e)
            }
        }
    }


}