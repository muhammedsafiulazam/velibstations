package com.muhammedsafiulazam.mobile.knowledge

import com.muhammedsafiulazam.mobile.event.EventManager
import com.muhammedsafiulazam.mobile.event.IEventManager
import com.muhammedsafiulazam.mobile.service.ServiceManager
import service.IServiceManager

object Knowledge {
    private val mEventManager: IEventManager by lazy {
        EventManager()
    }

    private val mServiceManager: IServiceManager by lazy {
        ServiceManager()
    }

    fun getEventManager() : IEventManager {
        return mEventManager
    }

    fun getServiceManager() : IServiceManager {
        return mServiceManager
    }
}