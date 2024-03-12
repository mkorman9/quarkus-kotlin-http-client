package com.github.mkorman9

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(value = [])
class WeatherResource(
    private val weatherService: WeatherService
) {
    @GET
    fun getWeather(): Weather {
        return weatherService.getWeather(52.52, 13.419998)
    }
}
