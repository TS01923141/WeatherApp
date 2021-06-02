package com.example.weatherapp.features.child

/*
        {
            "id": 800,
            "main": "Clear",
            "description": "clear sky",
            "icon": "01d"
        }
 */
data class Weather (
    val id: Int,
    val main : String,
    val description: String,
    val icon : String
)