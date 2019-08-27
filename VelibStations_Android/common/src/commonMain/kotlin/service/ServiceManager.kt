package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Data
import com.muhammedsafiulazam.mobile.service.model.Error
import com.muhammedsafiulazam.mobile.service.model.Weather

class ServiceManager {

    protected val mServiceClient: ServiceClient by lazy {
        val serviceClient = ServiceClient()
        serviceClient.setBaseURL("https://samples.openweathermap.org")
        serviceClient
    }

    protected val URL_WEATHER: String = "/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22"

    fun getWeather(callback: (response: Weather?, error: Error?) -> Unit) : Unit {
        mServiceClient.call(URL_WEATHER, callback = callback)
    }
}