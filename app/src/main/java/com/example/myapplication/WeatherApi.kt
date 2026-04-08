package com.example.myapplication

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

// DataSource
class WeatherApi {

    suspend fun getWeather(city: String): Result<String> {
       return try {
            val response: WeatherResponse =
                client.get("https://api.openweathermap.org/data/2.5/weather") {
                    parameter("q", city)
                    parameter("units", "metric")
                    parameter("appid", API_KEY)
                    parameter("lang", "ru")
                }.body()
            val temp = response.main.temp.toString()
            Result.Data(temp)
        } catch (e: Exception) {
            Result.Error<String>(e)
        }
    }
}