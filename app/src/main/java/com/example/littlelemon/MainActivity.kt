package com.example.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {

    val androidViewModel by viewModels<AndroidViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                        LittleLemonTheme {
                            val menuItemsEntities by androidViewModel.menuItemsList.observeAsState(
                                    emptyList()
                            )

                            Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                            ) {

                                val navController = rememberNavController()

                                NavigationComposable(navContoller = navController, menuItemsEntities, androidViewModel )
                            }
                        }
                    }


        }
    }
