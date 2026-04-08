package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel by viewModels<WeatherViewModel>()

            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = WeatherState())

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
                                    value = state.city,
                                    onValueChange = { text ->
                                        viewModel.onCityChange(text)
                                    },
                                    placeholder = {
                                        Text("Поиск города")
                                    }
                                )

                                IconButton(onClick = { viewModel.search() }) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = "Поиск"
                                    )
                                }
                            }

                        }

                        item {
                            Text(state.weather)
                        }
                    }
                }
            }
        }
    }
}