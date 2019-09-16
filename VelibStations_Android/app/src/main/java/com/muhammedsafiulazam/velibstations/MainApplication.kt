package com.muhammedsafiulazam.velibstations

import android.app.Application
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.utils.DatabaseUtils
import com.muhammedsafiulazam.velibstations.activity.ActivityManager
import com.muhammedsafiulazam.velibstations.addon.AddOnTypeNative
import com.squareup.sqldelight.android.AndroidSqliteDriver

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Database driver.
        DatabaseUtils.VELIB_DB_DRIVER = AndroidSqliteDriver(DatabaseUtils.VELIB_DB_SCHEMA, this, DatabaseUtils.VELIB_DB_FILENAME)

        // Add activity manager.
        AddOnManager.addAddOn(AddOnTypeNative.ACTIVITY_MANAGER, ActivityManager())
    }

    override fun onTerminate() {
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}