package com.example.weatherapp.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.core.exception.Failure
import com.example.weatherapp.core.extension.failure
import com.example.weatherapp.core.extension.observe
import com.example.weatherapp.databinding.ActivityCurrentWeatherBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding : ActivityCurrentWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentWeatherBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_current_weather)
        setContentView(binding.root)

        with(viewModel){
            observe(currentWeather, ::renderCurrentWeather)
            failure(failure, ::handleFailure)
        }

        viewModel.loadCurrentWeather()
    }

    private fun renderCurrentWeather(currentWeather: CurrentWeather?){
        currentWeather?.let {
            val celsiusSymbol = getString(R.string.symbol_celsius)
            val temp = it.main.temp.toString() + celsiusSymbol
            val realmFeel = it.main.feels_like.toString() + celsiusSymbol
            val tempRange = it.main.temp_min.toString() + celsiusSymbol + "/" + it.main.temp_max.toString() + celsiusSymbol
            binding.textViewCurrentWeatherTemp.text = temp
            binding.textViewCurrentWeatherRealFeel.text = realmFeel
            binding.textViewCurrentWeatherTempRange.text = tempRange
            binding.textViewCurrentWeatherWeather.text = it.weather.first().description
            val iconUrl = "https://openweathermap.org/img/w/" + it.weather.first().icon + ".png"
            Glide.with(this)
                .load(iconUrl)
                .into(binding.imageViewCurrentWeatherIcon)
        }
    }

    private fun handleFailure(failure: Failure?){
        when (failure) {
            is Failure.NetworkConnection -> {
                notify(R.string.failure_network_connection)
            }
            is Failure.ServerError -> {
                notify(R.string.failure_server_error)
            }
            is WeatherFailure.NonExistentCurrentWeather -> {
                notify(R.string.failure_current_weather_non_existent)
            }
            else -> {
                notify(R.string.failure_server_error)
            }
        }
    }

    private fun notify(@StringRes message: Int) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
}