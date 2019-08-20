package com.muhammedsafiulazam.velibstations.event

import com.muhammedsafiulazam.velibstations.addon.IAddOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IEventManager : IAddOn {
    /**
     * Send event.
     * @param event sent event
     * @param context use context
     */
    fun send(event: Event, context: CoroutineContext = Dispatchers.Main)

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
    fun subscribe(callback: (event: Event) -> Unit, context: CoroutineContext = Dispatchers.Main) : ReceiveChannel<Event>

    /**
     * Unsubscribe from receiving mChannel.
     * @param receiveChannel receiving mChannel
     */
    fun unsubscribe(receiveChannel: ReceiveChannel<Event>?)
}