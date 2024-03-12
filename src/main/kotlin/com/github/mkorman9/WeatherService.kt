package com.github.mkorman9

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.logging.Logger
import java.time.LocalDateTime

@ApplicationScoped
class WeatherService(
    private val log: Logger,
    @RestClient private val openMeteoClient: OpenMeteoClient
) {
    fun getWeather(latitude: Double, longitude: Double): Weather {
        val response = try {
            openMeteoClient.getForecast(latitude, longitude)
        } catch (e: Exception) {
            log.error("Failed to retrieve weather from OpenMeteo API", e)
            throw OpenMeteoApiException()
        }

        return Weather(
            location = Weather.WeatherLocation(
                latitude = response.latitude,
                longitude = response.longitude,
                elevation = response.elevation
            ),
            time = Weather.WeatherTime(
                time = response.currentWeather.time,
                isDay = response.currentWeather.isDay > 0
            ),
            temperature = response.currentWeather.temperature,
            wind = Weather.WeatherWind(
                windSpeed = response.currentWeather.windSpeed,
                windDirection = response.currentWeather.windSpeed
            ),
            wmoCode = response.currentWeather.weatherCode
        )
    }
}

data class Weather(
    val location: WeatherLocation,
    val time: WeatherTime,
    val temperature: Double,
    val wind: WeatherWind,
    val wmoCode: Int
) {
    data class WeatherLocation(
        val latitude: Double,
        val longitude: Double,
        val elevation: Double
    )

    data class WeatherWind(
        val windSpeed: Double,
        val windDirection: Double
    )

    data class WeatherTime(
        val time: LocalDateTime,
        val isDay: Boolean
    )
}

class OpenMeteoApiException : RuntimeException()
