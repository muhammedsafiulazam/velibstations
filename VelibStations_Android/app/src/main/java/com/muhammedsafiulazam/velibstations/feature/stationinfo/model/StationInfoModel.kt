package com.muhammedsafiulazam.velibstations.feature.stationinfo.model

import android.content.Context
import android.text.TextUtils
import com.muhammedsafiulazam.common.addon.AddOnManager
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
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.view.BaseModel
import com.muhammedsafiulazam.common.view.IViewManager
import com.muhammedsafiulazam.photoalbum.context.IContextManager
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType

class StationInfoModel : BaseModel() {

    private lateinit var mEventManager: IEventManager
    private var mRecord: Record? = null

    override fun onLoad() {
        super.onLoad()

        // Addons
        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        // Enable events.
        receiveEvents(true)
    }

    private fun responseLoadData(response: Any?, error: Error?) {
        val event = Event(StationInfoEventType.MODEL_RESPONSE_LOAD_DATA, response, error)
        mEventManager.send(event)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)
        if (TextUtils.equals(StationInfoEventType.MODEL_REQUEST_LOAD_DATA, event.type)) {
            mRecord = event.data as Record?
            onCreatePropertyList(mRecord)
        }
    }

    fun onCreatePropertyList(record: Record?) {
        try {
            val fields = record?.fields!!
            val propertyList: MutableList<Property> = mutableListOf()

            val contextManager: IContextManager? = AddOnManager.getAddOn(AddOnType.CONTEXT_MANAGEER) as IContextManager?
            val context: Context? = contextManager?.getContext() as Context?

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

            responseLoadData(propertyList, null)

        } catch (e: Exception) {
            responseLoadData(null, Error(null, e.message))
        }
    }

    override fun onUnload() {
        // Disable events.
        receiveEvents(false)
        super.onUnload()
    }
}