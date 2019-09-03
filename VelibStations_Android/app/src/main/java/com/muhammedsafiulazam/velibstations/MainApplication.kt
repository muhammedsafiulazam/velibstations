package com.muhammedsafiulazam.velibstations

import android.app.Application
import com.muhammedsafiulazam.common.utils.DatabaseUtils
import com.squareup.sqldelight.android.AndroidSqliteDriver

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Database driver.
        DatabaseUtils.VELIB_DRIVER = AndroidSqliteDriver(DatabaseUtils.VELIB_SCHEMA, this, DatabaseUtils.VELIB_FILENAME)
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}