package com.muhammedsafiulazam.velibstations.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.velibstations.addon.AddOnTypeNative
import com.muhammedsafiulazam.velibstations.location.LocationManager

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseActivity : AppCompatActivity(), IAddOn {
    companion object {
        const val KEY_DATA_ID: String = "KEY_DATA_ID"
    }

    private var mData: Any? = null
    private var mActivityModel: BaseActivityModel? = null

    private var isViewReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Essential addons for activity.
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
        addAddOn(AddOnTypeNative.ACTIVITY_MANAGER, AddOnManager.getAddOn(AddOnTypeNative.ACTIVITY_MANAGER)!!)
        addAddOn(AddOnType.LOCATION_MANAGER, AddOnManager.getAddOn(AddOnType.LOCATION_MANAGER)!!)

        // Load data.
        val dataID: String? = intent.getStringExtra(KEY_DATA_ID)
        if (dataID != null) {
            val activityManager: ActivityManager? = getAddOn(AddOnTypeNative.ACTIVITY_MANAGER) as ActivityManager?
            mData = activityManager?.retrieveData(dataID)
        }

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

    fun getData() : Any? {
        return mData
    }

    fun getActivityModel() : BaseActivityModel? {
        return mActivityModel
    }

    fun setActivityModel(activityModel: Class<out BaseActivityModel>) {
        mActivityModel = ViewModelProviders.of(this).get(activityModel)
        mActivityModel?.setActivity(this)
    }

    private fun onActivateActivity() {
        val activityManager: IActivityManager? = getAddOn(AddOnTypeNative.ACTIVITY_MANAGER) as IActivityManager?
        activityManager?.onStartActivity(this)
    }

    private fun onDeactivateActivity() {
        val activityManager: IActivityManager? = getAddOn(AddOnTypeNative.ACTIVITY_MANAGER) as IActivityManager?
        activityManager?.onStopActivity(this)
    }

    override fun onDestroy() {
        clearAddOns()
        mActivityModel?.onDestroyActivity()
        super.onDestroy()
    }

    // Permission related methods.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val locationManager: LocationManager? = getAddOn(AddOnType.LOCATION_MANAGER) as LocationManager?
        locationManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Addons related methods.
    private val mAddOn: IAddOn by lazy {
        AddOn()
    }
    
    override fun getAddOn(type: String): IAddOn? {
        return mAddOn.getAddOn(type)
    }

    override fun getAddOns(): Map<String, IAddOn> {
        return mAddOn.getAddOns()
    }

    override fun addAddOn(type: String, addOn: IAddOn) {
        mAddOn.addAddOn(type, addOn)
    }

    override fun addAddOns(addons: Map<String, IAddOn>) {
        mAddOn.addAddOns(addons)
    }

    override fun removeAddOn(type: String) {
        mAddOn.removeAddOn(type)
    }

    override fun removeAddOns(types: List<String>) {
        mAddOn.removeAddOns(types)
    }

    override fun clearAddOns() {
        mAddOn.clearAddOns()
    }
}