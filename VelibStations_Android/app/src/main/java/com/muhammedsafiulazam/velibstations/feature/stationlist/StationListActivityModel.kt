package com.muhammedsafiulazam.velibstations.feature.stationlist

import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.database.velib.event.VelibDatabaseEventType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.velib.event.VelibServiceEventType
import com.muhammedsafiulazam.common.view.BaseView
import com.muhammedsafiulazam.common.view.BaseViewModel
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType

class StationListActivityModel : BaseViewModel() {
    private var mLocation: Location? = null
    private lateinit var mEventManager: IEventManager
    private lateinit var mServiceManager: IServiceManager
    private lateinit var mDatabaseManager: IDatabaseManager
    private lateinit var mView: BaseView

    override fun onViewLoad() {
        super.onViewLoad()

        // Managers.
        mServiceManager = getAddOn(AddOnType.SERVICE_MANAGER) as IServiceManager
        mDatabaseManager = getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager
        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Activity.
        mView = getView() as BaseView

        // Enable events.
        receiveEvents(true)
    }

    private fun loadDataBusy(busy: Boolean) {
        val event = Event(StationListEventType.LOAD_DATA_BUSY, busy, null)
        mEventManager.send(event)
    }

    private fun loadDataError(error: String?) {
        val event = Event(StationListEventType.LOAD_DATA_ERROR, error, null)
        mEventManager.send(event)
    }

    private fun loadDataResponse(response: Any?) {
        val event = Event(StationListEventType.LOAD_DATA_RESPONSE, response, null)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)

        if (TextUtils.equals(StationListEventType.LOAD_DATA_REQUEST, event.type)) {
            // Show loader.
            loadDataBusy(true)

            mLocation = event.data as Location

            // Call service.
            mServiceManager.getVelibService().getData(mLocation!!.latitude, mLocation!!.longitude)

        } else if (TextUtils.equals(VelibServiceEventType.GET_DATA, event.type)) {
            if (event.error != null) {
                // Show loader.
                loadDataBusy(true)

                // Call database.
                mDatabaseManager.getVelibDatabase().getData(mLocation!!.latitude, mLocation!!.longitude)
            } else {

                // Hide loader.
                loadDataBusy(false)

                // Load data.
                loadDataResponse(event.data)
            }

        } else if (TextUtils.equals(VelibDatabaseEventType.GET_DATA, event.type)) {
            if (event.error != null) {
                // Hide loader.
                loadDataBusy(false)
                loadDataError(mView.getString(R.string.stationlist_error_data))
            } else {
                // Hide loader.
                loadDataBusy(false)

                // Load data.
                loadDataResponse(event.data)
            }
        }
    }

    override fun onViewUnload() {
        receiveEvents(false)
        super.onViewUnload()
    }
}