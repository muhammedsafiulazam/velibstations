package com.muhammedsafiulazam.common.event

import com.muhammedsafiulazam.common.utils.CouroutineUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class EventManager : IEventManager {
    private val mChannel = ConflatedBroadcastChannel<Any>(Channel.CONFLATED)

    /**
     * Send com.muhammedsafiulazam.common.event.
     * @param event sent com.muhammedsafiulazam.common.event
     * @param context use context
     */
    override fun send(event: Event) {
        CoroutineScope(CouroutineUtils.DISPATCHER).launch {
            mChannel.send(event)
        }
    }

    private inline fun <reified T> newChannel(): ReceiveChannel<T> {
        return mChannel.openSubscription().filter { it is T }.map { it as T }
    }

    /**
     * Subscribe to receiving mChannel.
     * @return receiving mChannel
     */
    override fun subscribe() : EventSubscriber {
        val channel = newChannel<Event>()
        return EventSubscriber(channel)
    }

    /**
     * Subscribe to receiving mChannel.
     * @param callback com.muhammedsafiulazam.common.event callback
     * @param context use context
     * @return receiving mChannel
     */
    override fun subscribe(callback: (event: Event) -> Unit) : EventSubscriber {
        val channel = newChannel<Event>()
        CoroutineScope(CouroutineUtils.DISPATCHER).launch {
            for(event in channel) {
                callback(event)
            }
        }
        return EventSubscriber(channel)
    }

    /**
     * Unsubscribe from receiving mChannel.
     * @param receiveChannel receiving mChannel
     */
    override fun unsubscribe(subscriber: EventSubscriber?) {
        subscriber?.getReceiveChannel()?.cancel()
    }
}