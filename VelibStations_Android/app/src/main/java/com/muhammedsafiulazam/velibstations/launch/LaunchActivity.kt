package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import android.widget.Toast
import com.muhammedsafiulazam.mobile.WeatherApi
import com.muhammedsafiulazam.mobile.getPlatformName
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.util.InternalAPI
import kotlinx.android.synthetic.main.activity_launch.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    @UseExperimental(InternalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        // Test expect / actual.
        lunch_txv_message.text = getPlatformName()

        val weatherApi = WeatherApi(OkHttpEngine(OkHttpConfig()))
        launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) { weatherApi.fetchWeather() }
                Toast.makeText(this@LaunchActivity, result.toString(), Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@LaunchActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
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