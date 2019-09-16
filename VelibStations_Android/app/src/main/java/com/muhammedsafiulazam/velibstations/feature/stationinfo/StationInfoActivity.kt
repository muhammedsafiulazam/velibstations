package com.muhammedsafiulazam.velibstations.feature.stationinfo

import android.os.Bundle
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import com.muhammedsafiulazam.velibstations.feature.stationlist.StationListActivityModel

class StationInfoActivity : BaseActivity() {

    private var mEventManager: IEventManager? = null
    private var mEventSubscriber: IEventSubscriber? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stationinfo)
        setActivityModel(StationListActivityModel::class.java)

        // Do things.

        mEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        subscribeToEvents()
    }

    override fun onStart() {
        super.onStart()
        subscribeToEvents()
    }

    override fun onStop() {
        unsubscribeFromEvents()
        super.onStop()
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

    override fun onDestroy() {
        unsubscribeFromEvents()
        super.onDestroy()
    }
}