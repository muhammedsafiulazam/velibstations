package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Error
import com.muhammedsafiulazam.mobile.utils.CouroutineUtils
import com.muhammedsafiulazam.mobile.utils.ServiceUtils
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ServiceClient {
    var mBaseURL: String? = null
    val mClient: HttpClient by lazy {
        ServiceUtils.CLIENT
    }

    fun setBaseURL(url: String) {
        mBaseURL = url
    }

    inline fun <reified T> call(url: String, crossinline callback: (response: T?, error: Error?) -> Unit) : Unit {
        GlobalScope.launch(CouroutineUtils.DISPATCHER) {
            try {
                val response = mClient.get<T>("$mBaseURL$url")
                callback(response, null)
            } catch (e: Exception) {
                val error = Error(null, e.message)
                callback(null, error)
            }
        }
    }
}