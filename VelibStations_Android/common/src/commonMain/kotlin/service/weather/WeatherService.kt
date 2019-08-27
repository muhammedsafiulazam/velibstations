package service.weather

import com.muhammedsafiulazam.mobile.event.Event
import com.muhammedsafiulazam.mobile.knowledge.Knowledge
import com.muhammedsafiulazam.mobile.service.ServiceClient
import com.muhammedsafiulazam.mobile.service.model.Error
import com.muhammedsafiulazam.mobile.service.model.Weather
import service.weather.event.WeatherEventType

class WeatherService(val serviceClient: ServiceClient) : IWeatherService {
    private val URL_WEATHER: String = "/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22"

    override fun getWeather() {
        serviceClient.call(URL_WEATHER, callback = { response: Weather?, error: Error? ->

            val event = Event(WeatherEventType.GET_WEATHER, response, error)
            Knowledge.getEventManager().send(event)
        })
    }
}