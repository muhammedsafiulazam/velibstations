package com.muhammedsafiulazam.velibstations.activity

import android.content.Intent
import com.muhammedsafiulazam.common.addon.AddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ActivityManager : AddOn(), IActivityManager {
    private var mCurrentActivity: BaseActivity? = null
    private var mData: MutableMap<String, Any> = mutableMapOf()

    /**
     * Get current activity.
     * @return current activity
     */
    override fun getCurrentActivity() : BaseActivity? {
        return mCurrentActivity
    }

    /**
     * Track starting activity.
     * @param baseActivity activity
     */
    override fun onStartActivity(baseActivity: BaseActivity) {
        this.mCurrentActivity = baseActivity
    }

    /**
     * Track stopping activity.
     * @param baseActivity activity
     */
    override fun onStopActivity(baseActivity: BaseActivity) {
        if (baseActivity == mCurrentActivity) {
            mCurrentActivity = null
        }
    }

    /**
     * Load activity.
     * @param activity activity class
     */
    override fun loadActivity(activity: Class<out BaseActivity>) {
        loadActivity(activity, null)
    }

    /**
     * Load activity with data.
     * @param activity activity class
     * @param data data
     */
    override fun loadActivity(activity: Class<out BaseActivity>, data: Any?) {
        if (mCurrentActivity != null) {
            val intent = Intent(mCurrentActivity, activity)

            if (data != null) {
                val dataID = generateID()
                intent.putExtra(BaseActivity.KEY_DATA_ID, dataID)
                mData.put(dataID, data)
            }

            mCurrentActivity?.startActivity(intent)
        }
    }

    /**
     * Generate data id.
     * @return data id
     */
    private fun generateID() : String {
        return "" + System.currentTimeMillis()
    }

    /**
     * Retrieve data.
     * @return data
     */
    fun retrieveData(id: String) : Any? {
        val data: Any? = mData.get(id)
        mData.remove(id)
        return data
    }
}