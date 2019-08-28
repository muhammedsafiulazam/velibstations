package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.model.Coordinate
import com.muhammedsafiulazam.mobile.service.model.Weather
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ServiceSerializer {
    fun getSerializer() : KotlinxSerializer {
        return KotlinxSerializer(Json.nonstrict).apply {
            setMapper(Weather::class, Weather.serializer())
            setMapper(Coordinate::class, Coordinate.serializer())
        }
    }
}