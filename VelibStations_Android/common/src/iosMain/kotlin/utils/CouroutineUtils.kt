package com.muhammedsafiulazam.mobile.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
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
actual object CouroutineUtils {

    // Dispatcher.
    @SharedImmutable
    actual val DISPATCHER: CoroutineDispatcher = NsQueueDispatcher(
        dispatch_get_main_queue()
    )

    internal class NsQueueDispatcher(private val dispatchQueue: dispatch_queue_t) : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(dispatchQueue) {
                block.run()
            }
        }
    }
}

