package com.muhammedsafiulazam.velibstations.activity

import android.os.Parcelable
import com.muhammedsafiulazam.velibstations.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

interface IActivityManager : IAddOn {
    /**
     * Get current activity.
     * @return current activity
     */
    fun getCurrentActivity() : BaseActivity?

    /**
     * Track starting activity.
     * @param baseActivity activity
     */
    fun onStartActivity(baseActivity: BaseActivity)

    /**
     * Track stopping activity.
     * @param baseActivity activity
     */
    fun onStopActivity(baseActivity: BaseActivity)

    /**
     * Load activity.
     * @param activity activity class
     */
    fun loadActivity(activity: Class<out BaseActivity>)

    /**
     * Load activity with data.
     * @param activity activity class
     * @param data data
     */
    fun loadActivity(activity: Class<out BaseActivity>, data: Parcelable?)
}