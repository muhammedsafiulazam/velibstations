package com.muhammedsafiulazam.mobile.utils

import com.muhammedsafiulazam.mobile.service.ServiceSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
actual object ServiceUtils {
    @SharedImmutable
    actual val HTTP_CLIENT: HttpClient = HttpClient(Ios) {
        install(JsonFeature) {
            serializer = ServiceSerializer.getSerializer()
        }
    }
}