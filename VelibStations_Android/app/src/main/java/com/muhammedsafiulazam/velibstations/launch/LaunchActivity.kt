package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import com.muhammedsafiulazam.velibstations.activity.IActivityManager
import com.muhammedsafiulazam.velibstations.addon.AddOnTypeNative
import com.muhammedsafiulazam.velibstations.feature.stationinfo.StationInfoActivity
import com.muhammedsafiulazam.velibstations.feature.stationlist.StationListActivity


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()

        // Entry activity.
        val activityManager: IActivityManager = getAddOn(AddOnTypeNative.ACTIVITY_MANAGER) as IActivityManager
        activityManager.loadActivity(StationListActivity::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}