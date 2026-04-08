package com.example.myapplication

class WeatherRepository(
    private val api: WeatherApi
) {

    suspend fun getWeather(city: String): Result<String> {
        return api.getWeather(city)
    }
}