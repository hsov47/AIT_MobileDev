package hu.ait.weather.network

import hu.ait.weather.data.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/weather?q=Budapest,hu&units=metric&appid=b823207fb4975004fbe7a7684d835e4a

//Host: https://api.openweathermap.org
//Path: data/2.5
//Query params: weather?q=Budapest,hu&units=metric&appid=b823207fb4975004fbe7a7684d835e4a

interface WeatherAPI {

    @GET("/data/2.5")
    suspend fun getWeather(@Query("appid") accessKey: String): WeatherResult
}
Pf6z3wnuhvtM