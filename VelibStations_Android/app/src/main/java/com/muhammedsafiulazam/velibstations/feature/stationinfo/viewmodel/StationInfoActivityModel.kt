package com.muhammedsafiulazam.velibstations.feature.stationinfo.viewmodel

import android.content.Context
import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.view.BaseViewModel
import com.muhammedsafiulazam.common.view.IViewManager
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType
import com.muhammedsafiulazam.velibstations.feature.stationinfo.model.Property

class StationInfoActivityModel : BaseViewModel() {
    private lateinit var mRecord: Record
    private lateinit var mEventManager: IEventManager

    override fun onViewLoad() {
        super.onViewLoad()

        // Data.
        mRecord = getView()?.getData() as Record

        // Managers.
        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Enable events.
        receiveEvents(true)
    }

    private fun updateLoader(busy: Boolean) {
        val event = Event(StationInfoEventType.UPDATE_LOADER, busy, null)
        mEventManager.send(event)
    }

    private fun updateMessage(message: Any?) {
        val event = Event(StationInfoEventType.UPDATE_MESSAGE, message, null)
        mEventManager.send(event)
    }

    private fun responseLoadData(response: Any?) {
        val event = Event(StationInfoEventType.RESPONSE_LOAD_DATA, response, null)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)

        if (TextUtils.equals(StationInfoEventType.REQUEST_LOAD_DATA, event.type)) {
            // Show loader.
            updateLoader(true)
            onCreatePropertyList(mRecord)
        }
    }

    fun onCreatePropertyList(record: Record) {
        try {

            val fields = record.fields!!
            val propertyList: MutableList<Property> = mutableListOf()

            val viewManager: IViewManager? = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager?
            val context: Context? = (viewManager?.getCurrentView()) as Context?

            if (context != null) {

                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_name),
                        fields.name!!
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_code),
                        fields.code!!
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_state),
                        fields.state!!
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_duedate),
                        fields.dueDate!!
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_latitude),
                        fields.geolocation!!.get(0).toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_longitude),
                        fields.geolocation!!.get(1).toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_kioskstate),
                        fields.kioskState!!
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_creditcard),
                        fields.creditCard!!
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_overflowactivation),
                        fields.overflowActivation!!
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_maxbikeoverflow),
                        fields.maxBikeOverflow!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbedock),
                        fields.nbEDock!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbfreeedock),
                        fields.nbFreeEDock!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbdock),
                        fields.nbDock!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbfreedock),
                        fields.nbFreeDock!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbebike),
                        fields.nbEBike!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbbike),
                        fields.nbBike!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbbikeoverflow),
                        fields.nbBikeOverflow!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_nbebikeoverflow),
                        fields.nbEBikeOverflow!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_overflow),
                        fields.overflow!!.toString()
                    )
                )
                propertyList.add(
                    Property(
                        context.getString(R.string.stationinfo_property_densitylevel),
                        fields.densityLevel!!.toString()
                    )
                )
            }

            // Hide loader.
            updateLoader(false)

            responseLoadData(propertyList)

        } catch (e: Exception) {

            // Hide loader.
            updateLoader(false)

            updateMessage(R.string.stationinfo_error_data)
        }
    }

    override fun onViewUnload() {
        receiveEvents(false)
        super.onViewUnload()
    }
}