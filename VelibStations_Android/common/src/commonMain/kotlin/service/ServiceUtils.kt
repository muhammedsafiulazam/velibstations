package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Coordinate
import com.muhammedsafiulazam.mobile.service.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json

expect object ServiceUtils {
    fun getHttpClient() : HttpClient
    fun getCoroutineDispatcher(): CoroutineDispatcher
}