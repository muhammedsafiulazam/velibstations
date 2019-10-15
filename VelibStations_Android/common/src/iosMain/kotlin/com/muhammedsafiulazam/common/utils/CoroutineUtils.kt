package com.muhammedsafiulazam.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
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
    actual val DISPATCHER_MAIN: CoroutineDispatcher = NsQueueDispatcher(
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
}

