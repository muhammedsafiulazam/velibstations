package com.muhammedsafiulazam.common.addon

import com.muhammedsafiulazam.common.database.DatabaseManager
import com.muhammedsafiulazam.common.event.EventManager
import com.muhammedsafiulazam.common.location.LocationManager
import com.muhammedsafiulazam.common.service.ServiceManager
import com.muhammedsafiulazam.common.view.ViewManager
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object AddOnManager : IAddOnManager {

    @SharedImmutable
    private val mAddOn: IAddOn by lazy {
        val addOn = AddOn()
        addAddOns(addOn)
        addOn
    }

    override fun getAddOn(type: String): IAddOn? {
        return mAddOn.getAddOn(type)
    }

    override fun getAddOns(): Map<String, IAddOn> {
        return mAddOn.getAddOns()
    }

    override fun addAddOn(type: String, addOn: IAddOn) {
        mAddOn.addAddOn(type, addOn)
    }

    override fun addAddOns(addons: Map<String, IAddOn>) {
        mAddOn.addAddOns(addons)
    }

    override fun removeAddOn(type: String) {
        mAddOn.removeAddOn(type)
    }

    override fun removeAddOns(types: List<String>) {
        mAddOn.removeAddOns(types)
    }

    override fun clearAddOns() {
        mAddOn.clearAddOns()
    }

    private fun addAddOns(addOn: IAddOn) {
        var viewManager = ViewManager()
        val eventManager = EventManager()
        val serviceManager = ServiceManager()
        val databaseManager = DatabaseManager()
        val locationManager = LocationManager()

        serviceManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        serviceManager.addAddOn(AddOnType.DATABASE_MANAGER, databaseManager)
        databaseManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        locationManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)

        addOn.addAddOn(AddOnType.VIEW_MANAGEER, viewManager)
        addOn.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        addOn.addAddOn(AddOnType.SERVICE_MANAGER, serviceManager)
        addOn.addAddOn(AddOnType.DATABASE_MANAGER, databaseManager)
        addOn.addAddOn(AddOnType.LOCATION_MANAGER, locationManager)
    }
}