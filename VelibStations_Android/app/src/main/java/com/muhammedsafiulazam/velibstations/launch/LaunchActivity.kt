package com.muhammedsafiulazam.velibstations.launch

import android.os.Bundle
import com.muhammedsafiulazam.mobile.getPlatformName
import com.muhammedsafiulazam.mobile.model.Person
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import com.muhammedsafiulazam.velibstations.activity.IActivityManager
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

        // Test common object.
        val me: Person = Person("Safiul", Person.Gender.Male)
        me.info()
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