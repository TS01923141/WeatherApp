package com.example.weatherapp.features

import com.example.weatherapp.core.exception.Failure
import com.example.weatherapp.core.interactor.UseCase
import javax.inject.Inject

class GetCurrentWeather
@Inject constructor(private val weatherRepository: WeatherRepository): UseCase<CurrentWeather, GetCurrentWeather.Params>() {

    override suspend fun run(params: Params) = weatherRepository.currentWeather(
        params.countryName,
        params.appId,
        params.units
    )

    data class Params(
        val countryName: String,
        val appId: String,
        val units: String)
}