package com.muhammedsafiulazam.mobile.knowledge

import com.muhammedsafiulazam.mobile.event.EventManager
import com.muhammedsafiulazam.mobile.event.IEventManager
import com.muhammedsafiulazam.mobile.service.ServiceManager
import service.IServiceManager
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Knowledge {

    @SharedImmutable
    private val mEventManager: IEventManager by lazy {
        EventManager()
    }

    @SharedImmutable
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