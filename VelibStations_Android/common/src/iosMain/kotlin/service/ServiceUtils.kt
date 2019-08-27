package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Coordinate
import com.muhammedsafiulazam.mobile.service.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlinx.serialization.json.Json
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext

actual object ServiceUtils {
    actual fun getHttpClient() : HttpClient {
        return HttpClient(Ios) {
            install(JsonFeature) {
                serializer = ServiceSerializer.getSerializer()
            }
        }
    }
    actual fun getCoroutineDispatcher() : CoroutineDispatcher {
        return coroutineDispatcher
    }

    // Courtine dispatcher.
    internal val coroutineDispatcher: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_main_queue())
    internal class NsQueueDispatcher(private val dispatchQueue: dispatch_queue_t) : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(dispatchQueue) {
                block.run()
            }
        }
    }
}