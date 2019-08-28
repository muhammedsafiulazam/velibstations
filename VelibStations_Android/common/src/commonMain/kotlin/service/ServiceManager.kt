package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Error
import com.muhammedsafiulazam.mobile.service.model.Weather
import com.muhammedsafiulazam.mobile.utils.ServiceUtils
import io.ktor.client.HttpClient
import service.IServiceManager
import service.weather.IWeatherService
import service.weather.WeatherService

class ServiceManager : IServiceManager {

    private val mHttpClient: HttpClient = ServiceUtils.HTTP_CLIENT
    private val mBaseURL: String = "https://samples.openweathermap.org"

    private val mServiceClient: ServiceClient by lazy {
        val serviceClient = ServiceClient(mHttpClient, mBaseURL)
        serviceClient
    }

    private val mWeatherService: IWeatherService by lazy {
        WeatherService(mServiceClient)
    }

    override fun getWeatherService(): IWeatherService {
        return mWeatherService
    }

    private val URL_WEATHER: String = "/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22"

    fun getWeather(callback: (response: Weather?, error: Error?) -> Unit) : Unit {
        mServiceClient.call(URL_WEATHER, callback = callback)
    }
}