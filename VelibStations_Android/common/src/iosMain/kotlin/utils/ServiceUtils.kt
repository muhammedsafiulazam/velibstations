package com.muhammedsafiulazam.common.utils

import com.muhammedsafiulazam.common.service.ServiceSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
@ThreadLocal
actual object ServiceUtils {

    // Http client.
    @SharedImmutable
    actual val HTTP_CLIENT: HttpClient = HttpClient(Ios) {
        install(JsonFeature) {
            serializer = ServiceSerializer.getSerializer()
        }
    }
}