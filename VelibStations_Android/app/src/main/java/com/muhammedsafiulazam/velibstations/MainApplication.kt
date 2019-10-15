package com.muhammedsafiulazam.velibstations

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.muhammedsafiulazam.common.view.BaseView
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.utils.DatabaseUtils
import com.muhammedsafiulazam.common.view.IViewManager
import com.squareup.sqldelight.android.AndroidSqliteDriver

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Database driver.
        DatabaseUtils.VELIB_DB_DRIVER = AndroidSqliteDriver(DatabaseUtils.VELIB_DB_SCHEMA, this, DatabaseUtils.VELIB_DB_FILENAME)
    }

    override fun onTerminate() {
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}