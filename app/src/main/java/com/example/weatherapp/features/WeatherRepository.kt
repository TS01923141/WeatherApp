package com.example.weatherapp.features

import com.example.weatherapp.core.exception.Failure
import com.example.weatherapp.core.functional.Either
import com.example.weatherapp.core.platform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface WeatherRepository {
    fun currentWeather(countryName: String, appId: String, units: String): Either<Failure, CurrentWeather>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler, private val service: WeatherService) : WeatherRepository {
        override fun currentWeather(countryName: String, appId: String, units: String): Either<Failure, CurrentWeather> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(service.currentWeather(countryName, appId, units), {it.toCurrentWeather()}, CurrentWeatherEntity.empty)
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T) : Either<Failure, R> {
            return try{
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform(response.body() ?: default))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }
    }
}