package com.muhammedsafiulazam.mobile.service

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

actual object ServiceUtils {
    actual fun getHttpClient() : HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = ServiceSerializer.getSerializer()
            }
        }
    }

    actual fun getCoroutineDispatcher() : CoroutineDispatcher {
        return Dispatchers.Default
    }
}