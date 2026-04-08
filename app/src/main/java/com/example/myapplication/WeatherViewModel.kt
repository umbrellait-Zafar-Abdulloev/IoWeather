package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeatherState(
    val city: String = "",
    val weather: String = "Введите название города и нажмите поиск чтобы получить погоду",
    val loading: Boolean = false
)

class WeatherViewModel : ViewModel() {

    private val api = WeatherApi()
    private val repository = WeatherRepository(api = api)

    private val mutableState = MutableStateFlow(WeatherState())

    val state: Flow<WeatherState> = mutableState

    fun search() {
        viewModelScope.launch {
            val city = mutableState.value.city
            when (val weatherResult = repository.getWeather(city)) {
                is Result.Data<String> -> mutableState.update { current ->
                    current.copy(
                        weather = "Погода: ${weatherResult.data}"
                    )
                }

                is Result.Error<*> -> {
                    mutableState.update { current ->
                        val error =
                            weatherResult.throwable.message + " ${weatherResult.throwable.javaClass.simpleName}"
                        current.copy(
                            weather = "Ошибка: $error"
                        )
                    }
                }
            }
        }
    }

    fun onCityChange(text: String) {
        mutableState.update {
            it.copy(city = text)
        }
    }
}