package com.muhammedsafiulazam.mobile.utils

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import sun.rmi.server.Dispatcher

actual object CouroutineUtils {
    actual val DISPATCHER: CoroutineDispatcher = Dispatchers.Default
}

