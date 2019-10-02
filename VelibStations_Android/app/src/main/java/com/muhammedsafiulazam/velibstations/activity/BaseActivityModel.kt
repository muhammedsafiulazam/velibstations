package com.muhammedsafiulazam.velibstations.activity

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.addon.IAddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseActivityModel : ViewModel(), IAddOn {

    private var mActivity: BaseActivity? = null

    fun getActivity() : BaseActivity? {
        return mActivity
    }

    fun setActivity(activity: BaseActivity?) {
        mActivity = activity
    }

    open fun onCreateActivity() {
        // Essential addons for activity model.
        addAddOn(AddOnType.SERVICE_MANAGER, AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
    }

    open fun onStartActivity() {
    }

    open fun onResumeActivity() {

    }

    open fun onPauseActivity() {

    }

    open fun onStopActivity() {
    }

    open fun onDestroyActivity() {
        clearAddOns()
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