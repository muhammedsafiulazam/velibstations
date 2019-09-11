package com.muhammedsafiulazam.common.event

import kotlinx.coroutines.channels.ReceiveChannel

class EventSubscriber {
    private var receiveChannel: ReceiveChannel<Event>

    constructor(receiveChannel: ReceiveChannel<Event>) {
        this.receiveChannel = receiveChannel
    }

    /**
     * Get event receive channel.
     * @return event receive channel
     */
    fun getReceiveChannel() : ReceiveChannel<Event> {
        return receiveChannel
    }
}