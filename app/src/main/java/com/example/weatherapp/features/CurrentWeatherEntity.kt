package com.example.weatherapp.features

import com.example.weatherapp.core.extension.empty
import com.example.weatherapp.features.child.*

/*
    "coord": {
        "lon": 121.5319,
        "lat": 25.0478
    },
    "weather": [
        {
            "id": 803,
            "main": "Clouds",
            "description": "broken clouds",
            "icon": "04d"
        }
    ],
    "base": "stations",
    "main": {
        "temp": 29.48,
        "feels_like": 36.48,
        "temp_min": 28.64,
        "temp_max": 32.99,
        "pressure": 1007,
        "humidity": 83
    },
    "visibility": 10000,
    "wind": {
        "speed": 2.68,
        "deg": 110,
        "gust": 6.26
    },
    "clouds": {
        "all": 75
    },
    "dt": 1622620819,
    "sys": {
        "type": 2,
        "id": 266033,
        "country": "TW",
        "sunrise": 1622581443,
        "sunset": 1622630397
    },
    "timezone": 28800,
    "id": 1668341,
    "name": "Taipei",
    "cod": 200
 */

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