package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.view.BaseView
import com.muhammedsafiulazam.common.view.IViewManager
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationlist.view.StationListActivity


/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseView() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()

        // Entry activity.
        val viewManager: IViewManager = getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager
        viewManager.loadView(StationListActivity::class.java.canonicalName)

        // Finish.
        finish()
    }
}