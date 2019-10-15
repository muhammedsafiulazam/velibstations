package com.muhammedsafiulazam.velibstations

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.muhammedsafiulazam.common.BaseView
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

        // Load view mechanism.
        val viewManager: IViewManager = AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager
        viewManager.loadViewMechanism { view: String?, info: Any?, data: Any? ->
            val activity: Activity = viewManager.getCurrentView()!! as Activity
            val intent = Intent(activity, Class.forName(view))
            if (data != null) {
                val identifier = viewManager.push(data)
                intent.putExtra(BaseView.KEY_DATA_IDENTIFIER, identifier)
            }
            activity?.startActivity(intent)
        }
    }

    override fun onTerminate() {
        AddOnManager.clearAddOns()
        super.onTerminate()
    }
}