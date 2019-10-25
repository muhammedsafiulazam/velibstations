package com.muhammedsafiulazam.common.utils

import kotlinx.coroutines.*
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
@ThreadLocal
actual object CoroutineUtils {

    // Dispatcher.
    @SharedImmutable
    actual var DISPATCHER_MAIN: CoroutineDispatcher = NsQueueDispatcher(
        dispatch_get_main_queue()
    )

    @SharedImmutable
    actual val DISPATCHER_IO: CoroutineDispatcher = NsQueueDispatcher(
        // TODO - Use IO Queue
        dispatch_get_main_queue()
    )

    internal class NsQueueDispatcher(private val dispatchQueue: dispatch_queue_t) : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(dispatchQueue) {
                block.run()
            }
        }
    }

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
        CoroutineUtils.DISPATCHER_MAIN = NsQueueDispatcher(
            dispatch_get_main_queue()
        )
    }

    actual fun unitTests() {
        CoroutineUtils.DISPATCHER_MAIN = CoroutineUtils.DISPATCHER_IO
    }
}

