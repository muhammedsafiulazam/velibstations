package com.muhammedsafiulazam.velibstations.feature.stationlist

import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.common.utils.ConstantUtils
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivityModel
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType

class StationListActivityModel : BaseActivityModel() {

    private var mEventManager: IEventManager? = null
    private var mServiceManager: IServiceManager? = null
    private var mDatabaseManager: IDatabaseManager? = null
    private var mEventSubscriber: IEventSubscriber? = null
    private var mLocation: List<Double>? = null

    override fun onCreateActivity() {
        super.onCreateActivity()

        mServiceManager = AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager?
        mDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager?
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

    private fun loadDataBusy(busy: Boolean) {
        val event = Event(StationListEventType.LOAD_DATA_BUSY, busy, null)
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        eventManager?.send(event)
    }

    private fun loadDataError(error: String?) {
        val event = Event(StationListEventType.LOAD_DATA_ERROR, error, null)
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        eventManager?.send(event)
    }

    private fun loadDataResponse(response: Any?) {
        val event = Event(StationListEventType.LOAD_DATA_RESPONSE, response, null)
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        eventManager?.send(event)
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(StationListEventType.LOAD_DATA_REQUEST, event.type)) {

            // Show loader.
            loadDataBusy(true)

            mLocation = event.data as List<Double>
            mServiceManager?.getVelibService()?.getData(mLocation?.get(0)!!, mLocation?.get(1)!!)

        } else if (TextUtils.equals(VelibServiceEventType.GET_DATA, event.type)) {
            if (event.error != null) {

                // Show loader.
                loadDataBusy(true)

                mDatabaseManager?.getVelibDatabase()?.getData(mLocation?.get(0)!!, mLocation?.get(1)!!)
            } else {

                // Hide loader.
                loadDataBusy(false)

                loadDataResponse(event.data)
            }

        } else if (TextUtils.equals(VelibDatabaseEventType.GET_DATA, event.type)) {
            if (event.error != null) {
                // Hide loader.
                loadDataBusy(false)

                loadDataError(getActivity()?.getString(R.string.stationlist_error_data))
            } else {
                // Hide loader.
                loadDataBusy(false)

                loadDataResponse(event.data)
            }
        }
    }

    override fun onDestroyActivity() {
        unsubscribeFromEvents()
        super.onDestroyActivity()
    }
}