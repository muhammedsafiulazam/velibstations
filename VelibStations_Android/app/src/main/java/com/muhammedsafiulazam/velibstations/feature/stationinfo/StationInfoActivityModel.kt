package com.muhammedsafiulazam.velibstations.feature.stationinfo

import android.content.Context
import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivityModel
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType

class StationInfoActivityModel : BaseActivityModel() {

    private lateinit var mRecord: Record
    private lateinit var mContext: Context

    private lateinit var mEventManager: IEventManager
    private lateinit var mEventSubscriber: IEventSubscriber

    override fun onCreateActivity() {
        super.onCreateActivity()

        mContext = getActivity()!!
        mRecord = getActivity()?.getData() as Record

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
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
        mEventSubscriber = mEventManager.subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })
    }

    private fun unsubscribeFromEvents() {
        mEventManager.unsubscribe(mEventSubscriber)
    }

    private fun loadDataBusy(busy: Boolean) {
        val event = Event(StationInfoEventType.LOAD_DATA_BUSY, busy, null)
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        eventManager?.send(event)
    }

    private fun loadDataError(error: String?) {
        val event = Event(StationInfoEventType.LOAD_DATA_ERROR, error, null)
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        eventManager?.send(event)
    }

    private fun loadDataResponse(response: Any?) {
        val event = Event(StationInfoEventType.LOAD_DATA_RESPONSE, response, null)
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        eventManager?.send(event)
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(StationInfoEventType.LOAD_DATA_REQUEST, event.type)) {
            // Show loader.
            loadDataBusy(true)
            onCreatePropertyList(mRecord)
        }
    }

    fun onCreatePropertyList(record: Record) {
        try {

            val fields = record.fields!!
            val propertyList: MutableList<Property> = mutableListOf()

            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_name),
                    fields.name!!
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_code),
                    fields.code!!
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_state),
                    fields.state!!
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_duedate),
                    fields.dueDate!!
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_latitude),
                    fields.geolocation!!.get(0).toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_longitude),
                    fields.geolocation!!.get(1).toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_kioskstate),
                    fields.kioskState!!
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_creditcard),
                    fields.creditCard!!
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_overflowactivation),
                    fields.overflowActivation!!
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_maxbikeoverflow),
                    fields.maxBikeOverflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbedock),
                    fields.nbEDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbfreeedock),
                    fields.nbFreeEDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbdock),
                    fields.nbDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbfreedock),
                    fields.nbFreeDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbebike),
                    fields.nbEBike!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbbike),
                    fields.nbBike!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbbikeoverflow),
                    fields.nbBikeOverflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_nbebikeoverflow),
                    fields.nbEBikeOverflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_overflow),
                    fields.overflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mContext.getString(R.string.stationinfo_property_densitylevel),
                    fields.densityLevel!!.toString()
                )
            )

            // Hide loader.
            loadDataBusy(false)

            loadDataResponse(propertyList)

        } catch (e: Exception) {

            // Hide loader.
            loadDataBusy(false)

            loadDataError(getActivity()?.getString(R.string.stationinfo_error_data))
        }
    }

    override fun onDestroyActivity() {
        unsubscribeFromEvents()
        super.onDestroyActivity()
    }
}