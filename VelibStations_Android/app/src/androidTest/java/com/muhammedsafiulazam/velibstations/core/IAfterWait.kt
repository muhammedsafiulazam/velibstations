package com.muhammedsafiulazam.velibstations.core

import com.muhammedsafiulazam.velibstations.event.Event

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

interface IAfterWait {
    fun afterWait(events: List<Event>)
}