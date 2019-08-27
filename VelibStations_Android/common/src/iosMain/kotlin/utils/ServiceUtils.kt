package com.muhammedsafiulazam.mobile.utils

import com.muhammedsafiulazam.mobile.service.ServiceSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature

actual object ServiceUtils {
    actual val CLIENT: HttpClient = HttpClient(Ios) {
        install(JsonFeature) {
            serializer = ServiceSerializer.getSerializer()
        }
    }
}