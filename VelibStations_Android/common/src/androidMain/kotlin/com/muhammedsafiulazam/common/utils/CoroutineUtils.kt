package com.muhammedsafiulazam.common.utils

import kotlinx.coroutines.*

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
actual object CoroutineUtils {
    // Dispatcher.
    actual var DISPATCHER_MAIN: CoroutineDispatcher = Dispatchers.Main
    actual val DISPATCHER_IO: CoroutineDispatcher = Dispatchers.IO

    actual fun execute(dispatcher: CoroutineDispatcher, block: () -> Unit) {
        GlobalScope.launch(dispatcher) {
            block()
        }
    }

    actual fun runBlocking(block: suspend () -> Unit) = kotlinx.coroutines.runBlocking {
        block()
    }

    actual suspend fun sleep(milliseconds: Long) {
        delay(milliseconds)
    }

    actual fun uiTests() {
        CoroutineUtils.DISPATCHER_MAIN = Dispatchers.Main
    }

    actual fun unitTests() {
        CoroutineUtils.DISPATCHER_MAIN = CoroutineUtils.DISPATCHER_IO
    }
}

