package com.muhammedsafiulazam.mobile.utils

import io.ktor.client.HttpClient
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

expect object ServiceUtils {
    val HTTP_CLIENT: HttpClient
}