package com.muhammedsafiulazam.velibstations.feature.stationinfo

import android.text.TextUtils
import com.muhammedsafiulazam.common.view.BaseView
import com.muhammedsafiulazam.common.view.BaseViewModel
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType

class StationInfoActivityModel : BaseViewModel() {
    private lateinit var mRecord: Record
    private lateinit var mEventManager: IEventManager
    private lateinit var mEventSubscriber: IEventSubscriber
    private lateinit var mView: BaseView

    override fun onViewLoad() {
        super.onViewLoad()

        // Data.
        mRecord = getView()?.getData() as Record

        // Managers.
        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Activity.
        mView = getView() as BaseView

        // Enable events.
        receiveEvents(true)
    }

    private fun loadDataBusy(busy: Boolean) {
        val event = Event(StationInfoEventType.LOAD_DATA_BUSY, busy, null)
        mEventManager.send(event)
    }

    private fun loadDataError(error: String?) {
        val event = Event(StationInfoEventType.LOAD_DATA_ERROR, error, null)
        mEventManager.send(event)
    }

    private fun loadDataResponse(response: Any?) {
        val event = Event(StationInfoEventType.LOAD_DATA_RESPONSE, response, null)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)

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
                    mView.getString(R.string.stationinfo_property_name),
                    fields.name!!
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_code),
                    fields.code!!
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_state),
                    fields.state!!
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_duedate),
                    fields.dueDate!!
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_latitude),
                    fields.geolocation!!.get(0).toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_longitude),
                    fields.geolocation!!.get(1).toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_kioskstate),
                    fields.kioskState!!
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_creditcard),
                    fields.creditCard!!
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_overflowactivation),
                    fields.overflowActivation!!
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_maxbikeoverflow),
                    fields.maxBikeOverflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbedock),
                    fields.nbEDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbfreeedock),
                    fields.nbFreeEDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbdock),
                    fields.nbDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbfreedock),
                    fields.nbFreeDock!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbebike),
                    fields.nbEBike!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbbike),
                    fields.nbBike!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbbikeoverflow),
                    fields.nbBikeOverflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_nbebikeoverflow),
                    fields.nbEBikeOverflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_overflow),
                    fields.overflow!!.toString()
                )
            )
            propertyList.add(
                Property(
                    mView.getString(R.string.stationinfo_property_densitylevel),
                    fields.densityLevel!!.toString()
                )
            )

            // Hide loader.
            loadDataBusy(false)

            loadDataResponse(propertyList)

        } catch (e: Exception) {

            // Hide loader.
            loadDataBusy(false)

            loadDataError(mView?.getString(R.string.stationinfo_error_data))
        }
    }

    override fun onViewUnload() {
        receiveEvents(false)
        super.onViewUnload()
    }
}