package com.muhammedsafiulazam.velibstations.activity

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.muhammedsafiulazam.velibstations.addon.AddOnManager
import com.muhammedsafiulazam.velibstations.addon.AddOnType
import com.muhammedsafiulazam.velibstations.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseActivity : AppCompatActivity(), IAddOn {
    companion object {
        const val KEY_DATA: String = "KEY_DATA"
    }

    private var mActivityModel: BaseActivityModel? = null
    private val mAddOns: MutableMap<String, IAddOn> = mutableMapOf()

    private var isViewReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Essential addons for activity.
        addAddOn(AddOnType.ACTIVITY_MANAGER, AddOnManager.getAddOn(AddOnType.ACTIVITY_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)

        isViewReady = false
    }

    override fun onStart() {
        super.onStart()

        if (!isViewReady) {
            isViewReady = true
            mActivityModel?.onCreateActivity()
        }

        mActivityModel?.onStartActivity()
        onActivateActivity()
    }

    override fun onResume() {
        super.onResume()
        mActivityModel?.onResumeActivity()
        onActivateActivity()
    }

    override fun onPause() {
        onDeactivateActivity()
        mActivityModel?.onPauseActivity()
        super.onPause()
    }

    override fun onStop() {
        onDeactivateActivity()
        mActivityModel?.onStopActivity()
        super.onStop()
    }

    fun getData() : Parcelable? {
        return intent?.getParcelableExtra(KEY_DATA)
    }

    fun getActivityModel() : BaseActivityModel? {
        return mActivityModel
    }

    fun setActivityModel(activityModel: Class<out BaseActivityModel>) {
        mActivityModel = ViewModelProviders.of(this).get(activityModel)
        mActivityModel?.setActivity(this)
    }

    private fun onActivateActivity() {
        val activityManager: IActivityManager? = getAddOn(AddOnType.ACTIVITY_MANAGER) as IActivityManager?
        activityManager?.onStartActivity(this)
    }

    private fun onDeactivateActivity() {
        val activityManager: IActivityManager? = getAddOn(AddOnType.ACTIVITY_MANAGER) as IActivityManager?
        activityManager?.onStopActivity(this)
    }

    override fun onDestroy() {
        clearAddOns()
        mActivityModel?.onDestroyActivity()
        super.onDestroy()
    }

    // Addons related methods.

    /**
     * Get addon.
     * @param type type of addon
     * @return addon for type
     */
    override fun getAddOn(type: String) : IAddOn? {
        return mAddOns.get(type)
    }

    /**
     * Get addons.
     * @return map of addons
     */
    override fun getAddOns() : Map<String, IAddOn> {
        return mAddOns
    }

    /**
     * Add addon.
     * @param type type of addon
     * @param addOn addon to be added
     */
    override fun addAddOn(type: String, addOn: IAddOn) {
        mAddOns.put(type, addOn)
    }

    /**
     * Add addons.
     * @param addons map of addons
     */
    override fun addAddOns(addons: Map<String, IAddOn>) {
        mAddOns.putAll(addons)
    }

    /**
     * Remove addon.
     * @param type type of addon
     */
    override fun removeAddOn(type: String) {
        mAddOns.remove(type)
    }

    /**
     * Remove addons.
     * @param types types of addons
     */
    override fun removeAddOns(types: List<String>) {
        types.forEach { key ->
            mAddOns.remove(key)
        }
    }

    /**
     * Clear addons.
     */
    override fun clearAddOns() {
        // Clear.
        mAddOns.clear()
    }
}