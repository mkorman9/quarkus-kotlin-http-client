package com.github.mkorman9

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.rest.client.reactive.ClientQueryParam
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.reactive.RestQuery
import java.time.LocalDateTime

@RegisterRestClient(baseUri = "https://api.open-meteo.com/v1")
interface OpenMeteoClient {
    @GET
    @Path("/forecast")
    @ClientQueryParam(name = "current_weather", value = ["true"])
    fun getForecast(@RestQuery latitude: Double, @RestQuery longitude: Double): OpenMeteoForecastResponse
}

data class OpenMeteoForecastResponse(
    val latitude: Double,
    val longitude: Double,
    val elevation: Double,
    @field:JsonProperty("current_weather") val currentWeather: CurrentWeather
) {
    data class CurrentWeather(
        val temperature: Double,
        @field:JsonProperty("windspeed") val windSpeed: Double,
        @field:JsonProperty("winddirection") val windDirection: Double,
        @field:JsonProperty("weathercode") val weatherCode: Int,
        @field:JsonProperty("is_day") val isDay: Int,
        val time: LocalDateTime
    )
}
