package com.muhammedsafiulazam.common.view

import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber

open class BaseModel : AddOn(), IBaseModel {

    private var mEventSubscriber: IEventSubscriber? = null

    override fun onLoad() {
        // Essential addons for activity model.
        addAddOn(AddOnType.SERVICE_MANAGER, AddOnManager.getAddOn(AddOnType.SERVICE_MANAGER)!!)
        addAddOn(AddOnType.EVENT_MANAGER, AddOnManager.getAddOn(AddOnType.EVENT_MANAGER)!!)
        addAddOn(AddOnType.DATABASE_MANAGER, AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER)!!)
    }

    override fun onUnload() {
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
}