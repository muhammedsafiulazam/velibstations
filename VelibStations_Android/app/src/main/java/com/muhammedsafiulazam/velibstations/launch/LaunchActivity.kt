package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import com.muhammedsafiulazam.mobile.service.ServiceManager
import com.muhammedsafiulazam.mobile.service.model.Error
import com.muhammedsafiulazam.mobile.service.model.Weather
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseActivity() {
    private val mServiceManager: ServiceManager by lazy {
        ServiceManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        mServiceManager.getWeather(callback = { response: Weather?, error: Error? ->

            var message: String

            if (error != null) {
                message = error.message.toString()
            } else {
                message = response.toString()
            }

            println(message)
        })
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}