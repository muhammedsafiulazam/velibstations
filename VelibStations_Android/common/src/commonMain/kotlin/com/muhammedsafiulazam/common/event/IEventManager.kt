package com.muhammedsafiulazam.common.event

import com.muhammedsafiulazam.common.addon.IAddOn
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IEventManager : IAddOn {
    /**
     * Send com.muhammedsafiulazam.common.event.
     * @param event sent com.muhammedsafiulazam.common.event
     * @param context use context
     */
    fun send(event: Event)

    /**
     * Subscribe to receiving mChannel.
     * @param callback com.muhammedsafiulazam.common.event callback
     * @param context use context
     * @return receiving mChannel
     */
    fun subscribe(callback: (event: Event) -> Unit) : IEventSubscriber

    /**
     * Unsubscribe from receiving mChannel.
     * @param subscriber event subscriber
     */
    fun unsubscribe(subscriber: IEventSubscriber?)
}