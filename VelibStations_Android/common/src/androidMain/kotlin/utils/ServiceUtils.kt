package com.muhammedsafiulazam.mobile.utils

import com.muhammedsafiulazam.mobile.service.ServiceSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature

actual object ServiceUtils {
    actual val HTTP_CLIENT: HttpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = ServiceSerializer.getSerializer()
        }
    }
}