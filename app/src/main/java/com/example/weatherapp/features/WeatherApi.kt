package com.example.weatherapp.features

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherApi {
    companion object{
        private const val WEATHER = "weather"
    }
    @GET(WEATHER) fun currentWeather(
        @Query("q") countryName: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): Call<CurrentWeatherEntity>
}