package com.muhammedsafiulazam.mobile.utils

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher

expect object ServiceUtils {
    val CLIENT: HttpClient
}