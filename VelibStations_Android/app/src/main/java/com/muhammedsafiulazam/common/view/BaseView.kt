package com.muhammedsafiulazam.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.location.LocationManager
import kotlin.reflect.KClass

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseView : AppCompatActivity(), IBaseView {
    companion object {
        const val KEY_DATA_IDENTIFIER: String = "KEY_DATA_IDENTIFIER"
    }

    private var mData: Any? = null
    private var mViewModel: IBaseViewModel? = null
    private var mEventSubscriber: IEventSubscriber? = null

    private var isViewReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isViewReady = false
        onViewLoad()
    }

    override fun getData() : Any? {
        return mData
    }

    override fun getViewModel() : IBaseViewModel? {
        return mViewModel
    }

    override fun setViewModel(viewModel: String) {
        mViewModel = ViewModelProviders.of(this).get(Class.forName(viewModel) as Class<ViewModel>) as? IBaseViewModel
        mViewModel?.setView(this)
    }

    override fun onViewLoad() {
        // Essential addons for view.
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
        addAddOn(AddOnType.VIEW_MANAGEER, AddOnManager.getAddOn(AddOnType.VIEW_MANAGEER)!!)
        addAddOn(AddOnType.LOCATION_MANAGER, AddOnManager.getAddOn(AddOnType.LOCATION_MANAGER)!!)

        // Load data.
        val dataIdentifier: String? = intent.getStringExtra(KEY_DATA_IDENTIFIER)
        if (dataIdentifier != null) {
            val viewManager: IViewManager? = getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager?
            mData = viewManager?.pop(dataIdentifier)
        }
    }

    override fun onViewStart() {

    }

    override fun onViewResume() {

    }

    override fun onViewPause() {

    }

    override fun onViewStop() {

    }

    override fun onViewUnload() {

    }

    override fun onStart() {
        super.onStart()
        if (!isViewReady) {
            isViewReady = true
            mViewModel?.onViewLoad()
        }
        onViewStart()
        mViewModel?.onViewStart()
        onChangeView()
    }

    override fun onResume() {
        super.onResume()
        onViewResume()
        mViewModel?.onViewResume()
        onChangeView()
    }

    override fun onPause() {
        super.onPause()
        onViewPause()
        mViewModel?.onViewPause()
        super.onPause()
    }

    override fun onStop() {
        onViewStop()
        mViewModel?.onViewStop()
        super.onStop()
    }

    fun receiveEvents(receive: Boolean) {
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?

        if (receive) {
            mEventSubscriber = eventManager?.subscribe(callback = { event: Event ->
                onReceiveEvents(event)
            })
        } else {
            if (mEventSubscriber != null) {
                eventManager?.unsubscribe(mEventSubscriber)
            }
            mEventSubscriber = null
        }
    }

    open fun onReceiveEvents(event: Event) {
    }

    override fun onDestroy() {
        onViewUnload()
        mViewModel?.onViewUnload()

        clearAddOns()
        receiveEvents(false)
        super.onDestroy()
    }

    private fun onChangeView() {
        val viewManager: IViewManager? = getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager?
        viewManager?.onChangeView(this)
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