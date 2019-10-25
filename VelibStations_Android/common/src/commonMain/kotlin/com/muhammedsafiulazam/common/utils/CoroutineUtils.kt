package com.muhammedsafiulazam.common.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
expect object CoroutineUtils {
    // Dispatcher.
    var DISPATCHER_MAIN: CoroutineDispatcher
    val DISPATCHER_IO: CoroutineDispatcher

    fun execute(dispatcher: CoroutineDispatcher, block: () -> Unit)

    fun runBlocking(block: suspend () -> Unit)
    suspend fun sleep(milliseconds: Long)

    fun uiTests()
    fun unitTests()
}

