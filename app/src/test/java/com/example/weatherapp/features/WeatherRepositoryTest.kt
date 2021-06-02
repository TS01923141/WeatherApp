package com.example.weatherapp.features

import com.example.weatherapp.UnitTest
import com.example.weatherapp.core.exception.Failure
import com.example.weatherapp.core.extension.empty
import com.example.weatherapp.core.functional.Either
import com.example.weatherapp.core.platform.NetworkHandler
import com.example.weatherapp.features.child.Coord
import com.example.weatherapp.features.child.Main
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase
import org.amshove.kluent.called
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class WeatherRepositoryTest : UnitTest() {

    private lateinit var networkRepository: WeatherRepository.Network

    @MockK private lateinit var networkHandler: NetworkHandler
    @MockK private lateinit var service: WeatherService
    @MockK private lateinit var currentWeatherCall : Call<CurrentWeatherEntity>
    @MockK private lateinit var currentWeatherResponse: Response<CurrentWeatherEntity>

    @Before fun setUp() {
        networkRepository = WeatherRepository.Network(networkHandler, service)
    }

    @Test fun `should return empty list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { currentWeatherResponse.body() } returns null
        every { currentWeatherResponse.isSuccessful }  returns true
        every { currentWeatherCall.execute() } returns currentWeatherResponse
        every { service.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric") } returns currentWeatherCall

        val currentWeather = networkRepository.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")
        currentWeather shouldEqual Either.Right(CurrentWeather.empty)
        verify(exactly = 1) { service.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")}
    }

    @Test fun `should get weather from service`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { currentWeatherResponse.body() } returns
                CurrentWeatherEntity(
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
        every { currentWeatherResponse.isSuccessful }  returns true
        every { currentWeatherCall.execute() } returns currentWeatherResponse
        every { service.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric") } returns currentWeatherCall

        val currentWeather = networkRepository.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")
        currentWeather shouldEqual Either.Right(CurrentWeather(
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
        ))
        verify(exactly = 1) { service.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")}
    }

    @Test fun `current weather service should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val currentWeather = networkRepository.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")

        currentWeather shouldBeInstanceOf Either::class.java
        currentWeather.isLeft shouldEqual true
        currentWeather.fold({failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verify { service wasNot Called }
    }

    @Test fun `current weather service should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { currentWeatherResponse.body() } returns null
        every { currentWeatherResponse.isSuccessful } returns false
        every { currentWeatherCall.execute() } returns currentWeatherResponse
        every { service.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric") } returns currentWeatherCall

        val currentWeather = networkRepository.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")

        currentWeather shouldBeInstanceOf Either::class.java
        currentWeather.isLeft shouldEqual true
        currentWeather.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test fun `current weather request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { currentWeatherCall.execute() } returns currentWeatherResponse
        every { service.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric") } returns currentWeatherCall

        val currentWeather = networkRepository.currentWeather("taipei", "0899273c10ddbaae0059f020ae69e421", "metric")

        currentWeather shouldBeInstanceOf Either::class.java
        currentWeather.isLeft shouldEqual true
        currentWeather.fold({failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }
}