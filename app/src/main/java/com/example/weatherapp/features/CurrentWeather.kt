package com.example.weatherapp.features

import com.example.weatherapp.core.extension.empty
import com.example.weatherapp.features.child.*

data class CurrentWeather(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int?,
    val wind: Wind?,
    val clouds: Clouds?,
    val dt: Long?,
    val sys: Sys?,
    val timezone: Int?,
    val id: Int?,
    val name: String?,
    val cod: Int?){

    companion object {
        val empty = CurrentWeather(
            Coord(0.0, 0.0),
            arrayListOf(),
            String.empty(),
            Main(0f, 0f, 0f, 0f, 0, 0),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}