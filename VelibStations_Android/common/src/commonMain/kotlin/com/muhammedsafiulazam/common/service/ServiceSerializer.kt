package com.muhammedsafiulazam.common.service

import com.muhammedsafiulazam.common.service.velib.model.*
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ServiceSerializer {
    fun getSerializer() : KotlinxSerializer {
        return KotlinxSerializer(Json.nonstrict).apply {
            setMapper(Dataset::class, Dataset.serializer())
            setMapper(Parameters::class, Parameters.serializer())
            setMapper(Record::class, Record.serializer())
            setMapper(Fields::class, Fields.serializer())
        }
    }
}