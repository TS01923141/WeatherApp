package com.example.weatherapp.features

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAIPEI = "taipei"
private const val APP_ID = "0899273c10ddbaae0059f020ae69e421"
private const val UNITS = "metric"

@HiltViewModel
class WeatherViewModel
@Inject constructor(private val getCurrentWeather: GetCurrentWeather) : BaseViewModel() {

    private val _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather = _currentWeather

    fun loadCurrentWeather() {
        val params = GetCurrentWeather.Params(TAIPEI, APP_ID, UNITS)
        getCurrentWeather(params, viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleCurrentWeather
            )
        }
    }

    private fun handleCurrentWeather(currentWeather: CurrentWeather) {
        _currentWeather.value = currentWeather
    }
}