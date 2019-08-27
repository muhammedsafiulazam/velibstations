package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.muhammedsafiulazam.mobile.event.Event
import com.muhammedsafiulazam.mobile.knowledge.Knowledge
import com.muhammedsafiulazam.velibstations.R
import kotlinx.coroutines.channels.ReceiveChannel
import service.weather.event.WeatherEventType


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : AppCompatActivity() {
    private var mReceiveChannel: ReceiveChannel<Event>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        mReceiveChannel = Knowledge.getEventManager().subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })

        Knowledge.getServiceManager().getWeatherService().getWeather()
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(WeatherEventType.GET_WEATHER, event.type)) {
            var message: String

            if (event.error != null) {
                message = event.error.toString()
            } else {
                message = event.data.toString()
            }

            println(message)
        }
    }
}