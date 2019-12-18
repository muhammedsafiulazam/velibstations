package com.muhammedsafiulazam.velibstations.feature.stationinfo.viewmodel

import android.content.Context
import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.model.Error
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.view.BaseViewModel
import com.muhammedsafiulazam.common.view.IViewManager
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType
import com.muhammedsafiulazam.velibstations.feature.stationinfo.model.Property
import com.muhammedsafiulazam.velibstations.feature.stationinfo.model.StationInfoModel
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType

class StationInfoActivityModel : BaseViewModel() {
    private var mRecord: Record? = null
    private lateinit var mEventManager: IEventManager

    override fun onLoad() {
        super.onLoad()

        // Set model.
        setModel(StationInfoModel::class.java.canonicalName)

        // Addons.
        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Enable events.
        receiveEvents(true)
    }

    /**
     * Request to load data.
     */
    private fun requestLoadData(record: Record?) {
        val event = Event(StationInfoEventType.MODEL_REQUEST_LOAD_DATA, record, null)
        mEventManager.send(event)
    }

    /**
     * Response with data.
     */
    private fun responseLoadData(response: Any?, error: Error?) {
        val event = Event(StationInfoEventType.VIEWMODEL_RESPONSE_LOAD_DATA, response, error)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)
        if (TextUtils.equals(StationInfoEventType.VIEWMODEL_REQUEST_LOAD_DATA, event.type)) {
            mRecord = event.data as Record?
            requestLoadData(mRecord)
        } else if (TextUtils.equals(StationInfoEventType.MODEL_RESPONSE_LOAD_DATA, event.type)) {
            responseLoadData(event.data, event.error)
        }
    }

    override fun onUnload() {
        receiveEvents(false)
        super.onUnload()
    }
}