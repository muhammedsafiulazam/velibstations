package com.muhammedsafiulazam.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
actual object CoroutineUtils {
    // Dispatcher.
    actual val DISPATCHER_MAIN: CoroutineDispatcher = Dispatchers.Main
    actual val DISPATCHER_IO: CoroutineDispatcher = Dispatchers.IO

    actual fun execute(dispatcher: CoroutineDispatcher, block: () -> Unit) {
        GlobalScope.launch(dispatcher) {
            block()
        }
    }
}

