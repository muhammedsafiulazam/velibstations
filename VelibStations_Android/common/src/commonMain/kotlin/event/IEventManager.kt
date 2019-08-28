package com.muhammedsafiulazam.mobile.event

import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IEventManager {
    /**
     * Send event.
     * @param event sent event
     * @param context use context
     */
    fun send(event: Event)

    /**
     * Subscribe to receiving mChannel.
     * @return receiving mChannel
     */
    fun subscribe() : ReceiveChannel<Event>

    /**
     * Subscribe to receiving mChannel.
     * @param callback event callback
     * @param context use context
     * @return receiving mChannel
     */
    fun subscribe(callback: (event: Event) -> Unit) : ReceiveChannel<Event>

    /**
     * Unsubscribe from receiving mChannel.
     * @param receiveChannel receiving mChannel
     */
    fun unsubscribe(receiveChannel: ReceiveChannel<Event>?)
}