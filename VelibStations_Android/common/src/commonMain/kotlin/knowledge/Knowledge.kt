package com.muhammedsafiulazam.common.knowledge

import com.muhammedsafiulazam.common.event.EventManager
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.ServiceManager
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