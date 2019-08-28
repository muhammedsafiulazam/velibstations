package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Error
import com.muhammedsafiulazam.mobile.utils.CouroutineUtils
import com.muhammedsafiulazam.mobile.utils.ServiceUtils
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ServiceClient(val httpClient: HttpClient, val baseURL: String) {
    inline fun <reified T> call(url: String, crossinline callback: (response: T?, error: Error?) -> Unit) : Unit {
        GlobalScope.launch(CouroutineUtils.DISPATCHER) {
            try {
                val response = httpClient.get<T>("$baseURL$url")
                callback(response, null)
            } catch (e: Exception) {
                val error = Error(null, e.message)
                callback(null, error)
            }
        }
    }
}