package com.example.weatherapp.features

import retrofit2.Retrofit
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherService
@Inject constructor(retrofit: Retrofit) : WeatherApi {
    private val weatherApi by lazy { retrofit.create(WeatherApi::class.java) }

    override fun currentWeather(
        @Query("q") countryName: String,
        @Query("appid") appId: String,
        @Query("units") units: String) = weatherApi.currentWeather(countryName, appId, units)
}