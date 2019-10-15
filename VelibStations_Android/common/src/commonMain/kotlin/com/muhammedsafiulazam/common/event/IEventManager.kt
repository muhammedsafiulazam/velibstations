package com.muhammedsafiulazam.common.event

import com.muhammedsafiulazam.common.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IEventManager : IAddOn {
    /**
     * Send com.muhammedsafiulazam.common.event.
     * @param event sent com.muhammedsafiulazam.common.event
     */
    fun send(event: Event)

    /**
     * Subscribe to events.
     * @param callback com.muhammedsafiulazam.common.event callback
     * @param context use context
     * @return subscriber
     */
    fun subscribe(callback: (event: Event) -> Unit) : IEventSubscriber

    /**
     * Unsubscribe from events.
     * @param eventSubscriber subscriber
     */
    fun unsubscribe(eventSubscriber: IEventSubscriber?)
}