package com.muhammedsafiulazam.mobile.utils

import com.muhammedsafiulazam.mobile.service.ServiceSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
actual object ServiceUtils {
    actual val HTTP_CLIENT: HttpClient = HttpClient(Android) {
        install(JsonFeature) {
            serializer = ServiceSerializer.getSerializer()
        }
    }
}