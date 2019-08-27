package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Data
import com.muhammedsafiulazam.mobile.service.model.Error
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ServiceClient {
    var mBaseURL: String? = null
    val mHttpClient: HttpClient by lazy {
        ServiceUtils.getHttpClient()
    }

    fun setBaseURL(url: String) {
        mBaseURL = url
    }

    inline fun <reified T> call(url: String, crossinline callback: (response: T?, error: Error?) -> Unit) : Unit {
        GlobalScope.launch(ServiceUtils.getCoroutineDispatcher()) {
            try {
                val response = mHttpClient.get<T>("$mBaseURL$url")
                callback(response, null)
            } catch (e: Exception) {
                val error = Error(null, e.message)
                callback(null, error)
            }
        }
    }
}