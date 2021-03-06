package com.muhammedsafiulazam.velibstations.feature.stationlist.viewmodel

import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.model.Error
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.view.BaseViewModel
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType
import com.muhammedsafiulazam.velibstations.feature.stationlist.model.StationListModel

class StationListActivityModel : BaseViewModel() {
    private var mLocation: Location? = null
    private lateinit var mEventManager: IEventManager

    override fun onLoad() {
        super.onLoad()

        // Set model.
        setModel(StationListModel::class.java.canonicalName)

        // Addons.
        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Enable events.
        receiveEvents(true)
    }

    /**
     * Request to load data.
     */
    private fun requestLoadData(location: Location?) {
        val event = Event(StationListEventType.MODEL_REQUEST_LOAD_DATA, location, null)
        mEventManager.send(event)
    }

    /**
     * Response with loaded data.
     */
    private fun responseLoadData(response: Any?, error: Error?) {
        val event = Event(StationListEventType.VIEWMODEL_RESPONSE_LOAD_DATA, response, error)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)
        if (TextUtils.equals(StationListEventType.VIEWMODEL_REQUEST_LOAD_DATA, event.type)) {
            mLocation = event.data as Location
            requestLoadData(mLocation)

        } else if (TextUtils.equals(StationListEventType.MODEL_RESPONSE_LOAD_DATA, event.type)) {
            responseLoadData(event.data, event.error)
        }
    }

    override fun onUnload() {
        // Disable events.
        receiveEvents(false)
        super.onUnload()
    }
}