package com.muhammedsafiulazam.velibstations.core

import event.Event

/**
 * Created by Muhammed Safiul Azam on 29/07/2019.
 */

interface IAfterWait {
    fun afterWait(events: List<Event>)
}