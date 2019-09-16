package com.muhammedsafiulazam.velibstations.feature.stationinfo

import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.velibstations.activity.BaseActivityModel

class StationInfoActivityModel : BaseActivityModel() {

    private var mEventManager: IEventManager? = null
    private var mEventSubscriber: IEventSubscriber? = null

    override fun onCreateActivity() {
        super.onCreateActivity()

        // Do things.

        mEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        subscribeToEvents()
    }

    override fun onStartActivity() {
        super.onStartActivity()
        subscribeToEvents()
    }

    override fun onStopActivity() {
        unsubscribeFromEvents()
        super.onStopActivity()
    }

    private fun subscribeToEvents() {
        mEventSubscriber = mEventManager?.subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })
    }

    private fun unsubscribeFromEvents() {
        mEventManager?.unsubscribe(mEventSubscriber)
    }

    fun onReceiveEvents(event: Event) {
    }

    override fun onDestroyActivity() {
        unsubscribeFromEvents()
        super.onDestroyActivity()
    }
}