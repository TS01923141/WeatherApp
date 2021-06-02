package com.example.weatherapp.features

import com.example.weatherapp.core.extension.empty
import com.example.weatherapp.features.child.*

data class CurrentWeatherEntity(
    private val coord: Coord,
    private val weather: List<Weather>,
    private val base: String,
    private val main: Main,
    private val visibility: Int?,
    private val wind: Wind?,
    private val clouds: Clouds?,
    private val dt: Long?,
    private val sys: Sys?,
    private val timezone: Int?,
    private val id: Int?,
    private val name: String?,
    private val cod: Int?) {

    companion object {
        val empty = CurrentWeatherEntity(
            Coord(0.0, 0.0),
            arrayListOf(),
            String.empty(),
            Main(0f,0f,0f,0f,0,0),
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

    fun toCurrentWeather() = CurrentWeather(
        coord,
        weather,
        base,
        main,
        visibility,
        wind,
        clouds,
        dt,
        sys,
        timezone,
        id,
        name,
        cod
    )
}