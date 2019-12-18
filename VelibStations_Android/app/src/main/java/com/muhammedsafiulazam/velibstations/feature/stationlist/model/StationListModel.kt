package com.muhammedsafiulazam.velibstations.feature.stationlist.model

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
import com.muhammedsafiulazam.common.view.BaseModel
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType

class StationListModel : BaseModel() {

    private lateinit var mEventManager: IEventManager
    private lateinit var mServiceManager: IServiceManager
    private lateinit var mDatabaseManager: IDatabaseManager

    private var mLocation: Location? = null

    override fun onLoad() {
        super.onLoad()

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mServiceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        mDatabaseManager = getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager

        // Enable events.
        receiveEvents(true)
    }

    private fun responseLoadData(response: Any?, error: Error?) {
        val event = Event(StationListEventType.MODEL_RESPONSE_LOAD_DATA, response, error)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)
        if (TextUtils.equals(StationListEventType.MODEL_REQUEST_LOAD_DATA, event.type)) {
            mLocation = event.data as Location
            // Call service.
            mServiceManager.getVelibService().getData(mLocation!!.latitude, mLocation!!.longitude)

        } else if (TextUtils.equals(VelibServiceEventType.GET_DATA, event.type)) {
            if (event.error != null) {
                // Call db.
                mDatabaseManager.getVelibDatabase().getData(mLocation!!.latitude, mLocation!!.longitude)
            } else {
                // Response.
                responseLoadData(event.data, event.error)

                // Save in db.
                mDatabaseManager.getVelibDatabase().saveData(event.data as Dataset?)
            }

        } else if (TextUtils.equals(VelibDatabaseEventType.GET_DATA, event.type)) {
            // Response.
            responseLoadData(event.data, event.error)
        }
    }

    override fun onUnload() {
        // Disable events.
        receiveEvents(false)
        super.onUnload()
    }
}