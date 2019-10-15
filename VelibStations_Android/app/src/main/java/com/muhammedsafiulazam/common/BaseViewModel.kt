package com.muhammedsafiulazam.common

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.view.IBaseView
import com.muhammedsafiulazam.common.view.IBaseViewModel

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseViewModel : ViewModel(), IBaseViewModel {

    private var mView: IBaseView? = null
    private var mEventSubscriber: IEventSubscriber? = null

    override fun getView() : IBaseView? {
        return mView
    }

    override fun setView(view: IBaseView?) {
        mView = view
    }

    override fun onViewLoad() {
        // Essential addons for activity model.
        addAddOn(AddOnType.SERVICE_MANAGER, AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
        addAddOn(AddOnType.DATABASE_MANAGER, AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER)!!)
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
        clearAddOns()
        receiveEvents(false)
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