package com.muhammedsafiulazam.test

import com.muhammedsafiulazam.common.event.Event

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

interface IAfterDelay {
    fun afterWait(events: List<Event>)
}