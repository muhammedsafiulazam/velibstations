package com.muhammedsafiulazam.common.addon

import com.muhammedsafiulazam.common.database.DatabaseManager
import com.muhammedsafiulazam.common.event.EventManager
import com.muhammedsafiulazam.common.location.LocationManager
import com.muhammedsafiulazam.common.service.ServiceManager
import com.muhammedsafiulazam.common.view.ViewManager
import com.muhammedsafiulazam.photoalbum.context.ContextManager
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object AddOnManager : IAddOnManager {

    private lateinit var mContext: Any

    override fun initialize(context: Any) {
        mContext = context
        onInitialize()
    }

    @SharedImmutable
    private val mAddOn: IAddOn by lazy {
        val addOn = AddOn()
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

    private fun onInitialize() {
        val contextManager = ContextManager(mContext)
        val viewManager = ViewManager()
        val eventManager = EventManager()
        val serviceManager = ServiceManager()
        val databaseManager = DatabaseManager()
        val locationManager = LocationManager()

        serviceManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        serviceManager.addAddOn(AddOnType.DATABASE_MANAGER, databaseManager)

        databaseManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        databaseManager.addAddOn(AddOnType.CONTEXT_MANAGEER, contextManager)

        locationManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        locationManager.addAddOn(AddOnType.VIEW_MANAGEER, viewManager)

        addAddOn(AddOnType.CONTEXT_MANAGEER, contextManager)
        addAddOn(AddOnType.VIEW_MANAGEER, viewManager)
        addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        addAddOn(AddOnType.SERVICE_MANAGER, serviceManager)
        addAddOn(AddOnType.DATABASE_MANAGER, databaseManager)
        addAddOn(AddOnType.LOCATION_MANAGER, locationManager)
    }
}