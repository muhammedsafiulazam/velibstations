package com.muhammedsafiulazam.velibstations

import android.app.Application
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.utils.DatabaseUtils
import com.muhammedsafiulazam.velibstations.activity.ActivityManager
import com.muhammedsafiulazam.velibstations.addon.AddOnTypeNative
import com.muhammedsafiulazam.velibstations.location.LocationManager
import com.squareup.sqldelight.android.AndroidSqliteDriver

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Database driver.
        DatabaseUtils.VELIB_DB_DRIVER = AndroidSqliteDriver(DatabaseUtils.VELIB_DB_SCHEMA, this, DatabaseUtils.VELIB_DB_FILENAME)

        // Addons.
        val activityManager = ActivityManager()
        val locationManager = LocationManager()
        val eventManager: IEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        locationManager.addAddOn(AddOnType.EVENT_MANAGER, eventManager)
        locationManager.addAddOn(AddOnTypeNative.ACTIVITY_MANAGER, activityManager)
        AddOnManager.addAddOn(AddOnTypeNative.ACTIVITY_MANAGER, activityManager)
        AddOnManager.addAddOn(AddOnTypeNative.LOCATION_MANAGER, locationManager)
    }

    override fun onTerminate() {
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}