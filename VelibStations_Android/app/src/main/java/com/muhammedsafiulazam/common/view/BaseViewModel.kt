package com.muhammedsafiulazam.common.view

import androidx.lifecycle.ViewModel
import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

open class BaseViewModel : ViewModel(), IBaseViewModel {
    private var mModel: IBaseModel? = null
    private var mEventSubscriber: IEventSubscriber? = null


    override fun getModel(): IBaseModel? {
        return mModel
    }

    override fun setModel(model: String) {
        mModel = Class.forName(model).newInstance() as IBaseModel?
        mModel?.onLoad()
    }

    override fun onLoad() {
        // Essential addons.
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
    }

    override fun onUnload() {
        // Unload model.
        mModel?.onUnload()

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