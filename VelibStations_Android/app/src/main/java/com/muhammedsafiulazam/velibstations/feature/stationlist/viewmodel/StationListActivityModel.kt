package com.muhammedsafiulazam.velibstations.feature.stationlist.viewmodel

import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.view.BaseViewModel
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType

class StationListActivityModel : BaseViewModel() {
    private var mLocation: Location? = null
    private lateinit var mEventManager: IEventManager
    private lateinit var mServiceManager: IServiceManager
    private lateinit var mDatabaseManager: IDatabaseManager

    override fun onLoad() {
        super.onLoad()
        // Managers.
        mServiceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        mDatabaseManager = getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager
        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Enable events.
        receiveEvents(true)
    }

    private fun updateLoader(show: Boolean) {
        val event = Event(StationListEventType.UPDATE_LOADER, show, null)
        mEventManager.send(event)
    }

    private fun updateMessage(message: Any?) {
        val event = Event(StationListEventType.UPDATE_MESSAGE, message, null)
        mEventManager.send(event)
    }

    private fun responseLoadData(response: Any?) {
        val event = Event(StationListEventType.RESPONSE_LOAD_DATA, response, null)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)

        if (TextUtils.equals(StationListEventType.REQUEST_LOAD_DATA, event.type)) {
            // Show loader.
            updateLoader(true)

            mLocation = event.data as Location

            // Call service.
            mServiceManager.getVelibService().getData(mLocation!!.latitude, mLocation!!.longitude)

        } else if (TextUtils.equals(VelibServiceEventType.GET_DATA, event.type)) {
            if (event.error != null) {
                // Show loader.
                updateLoader(true)

                // Call database.
                mDatabaseManager.getVelibDatabase().getData(mLocation!!.latitude, mLocation!!.longitude)
            } else {

                // Hide loader.
                updateLoader(false)

                // Load data.
                responseLoadData(event.data)
            }

        } else if (TextUtils.equals(VelibDatabaseEventType.GET_DATA, event.type)) {
            if (event.error != null) {
                // Hide loader.
                updateLoader(false)
                updateMessage(R.string.stationlist_error_data)
            } else {
                // Hide loader.
                updateLoader(false)

                // Load data.
                responseLoadData(event.data)

                // Save data.
                mDatabaseManager.getVelibDatabase().saveData(event.data as Dataset?)
            }
        }
    }

    override fun onUnload() {
        receiveEvents(false)
        super.onUnload()
    }
}