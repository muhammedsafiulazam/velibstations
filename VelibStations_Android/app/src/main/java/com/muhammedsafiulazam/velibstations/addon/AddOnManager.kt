package com.muhammedsafiulazam.velibstations.addon

import android.content.Context
import android.content.res.Resources
import com.muhammedsafiulazam.velibstations.activity.ActivityManager
import com.muhammedsafiulazam.velibstations.activity.IActivityManager
import com.muhammedsafiulazam.velibstations.event.EventManager
import com.muhammedsafiulazam.velibstations.event.IEventManager

object AddOnManager : AddOn(), IAddOn {

    // Context.
    private var mContext: Context? = null

    // Activity manager.
    private val mActivityManager: IActivityManager by lazy {
        ActivityManager()
    }

    // Event manager.
    private val mEventManager: IEventManager by lazy {
        EventManager()
    }

    /**
     * Initialize with context.
     * @param context context
     */
    fun initialize(context: Context) {
        mContext = context
        onInitialize()
    }

    private fun onInitialize() {
        // Activity manager.
        addAddOn(AddOnType.ACTIVITY_MANAGER, mActivityManager)

        // Event manager.
        addAddOn(AddOnType.EVENT_MANAGER, mEventManager)
    }

    /**
     * Get context.
     * @return context
     */
    fun getContext() : Context {
        return mContext!!
    }

    /**
     * Get resources.
     * @return resources
     */
    fun getResources() : Resources {
        return mContext!!.resources
    }
}