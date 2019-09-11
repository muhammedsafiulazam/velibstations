package com.muhammedsafiulazam.common.event

import com.muhammedsafiulazam.common.utils.CouroutineUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
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
        GlobalScope.launch(CouroutineUtils.DISPATCHER) {
            mChannel.send(event)
        }
    }

    /**
     * Subscribe to receiving mChannel.
     * @param callback com.muhammedsafiulazam.common.event callback
     * @param context use context
     * @return receiving mChannel
     */
    override fun subscribe(callback: (event: Event) -> Unit) : IEventSubscriber {
        val channel = newChannel<Event>()
        GlobalScope.launch(CouroutineUtils.DISPATCHER) {
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
    override fun unsubscribe(subscriber: IEventSubscriber?) {
        (subscriber as? EventSubscriber)?.getReceiveChannel()?.cancel()
    }

    /**
     * Open receive channel.
     * @return receive channel
     */
    private inline fun <reified T> newChannel(): ReceiveChannel<T> {
        return mChannel.openSubscription().filter { it is T }.map { it as T }
    }
}