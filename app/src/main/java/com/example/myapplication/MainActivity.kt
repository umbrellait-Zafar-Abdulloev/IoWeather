package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.append
import io.ktor.http.parameters
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val coroutineScope = rememberCoroutineScope()
            var query by remember {
                mutableStateOf("")
            }

            var weather by remember {
                mutableStateOf("")
            }
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        item {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    value = query,
                                    onValueChange = {
                                        query = it
                                    },
                                    placeholder = {
                                        Text("Поиск города")
                                    }
                                )

                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            try {
                                                val response: WeatherResponse =
                                                    client.get("https://api.openweathermap.org/data/2.5/weather") {
                                                        parameter("q", query)
                                                        parameter("units", "metric")
                                                        parameter("appid", API_KEY)
                                                        parameter("lang", "ru")
                                                    }.body()
                                                weather = response.main.temp.toString()
                                            } catch (e: Exception) {
                                                val error =
                                                    "ERROR: ${e.message}, ${e::class.simpleName}"
                                                weather = "Не удалось загрузить погоду \n $error"
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "Поиск"
                                    )
                                }
                            }

                        }

                        item {
                            Text("Погода: $weather")
                        }
                    }
                }
            }
        }
    }
}