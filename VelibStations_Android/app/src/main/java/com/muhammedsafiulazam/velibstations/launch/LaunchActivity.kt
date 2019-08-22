package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import com.muhammedsafiulazam.mobile.getPlatformName
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_launch.*

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        // Test expect / actual.
        lunch_txv_message.text = getPlatformName()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}