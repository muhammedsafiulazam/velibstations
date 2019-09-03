package com.muhammedsafiulazam.common.knowledge

import com.muhammedsafiulazam.common.database.DatabaseManager
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.event.EventManager
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.ServiceManager
import com.muhammedsafiulazam.common.utils.DatabaseUtils
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

    @SharedImmutable
    private val mDatabaseManager: IDatabaseManager by lazy {
        DatabaseManager()
    }

    fun getEventManager() : IEventManager {
        return mEventManager
    }

    fun getServiceManager() : IServiceManager {
        return mServiceManager
    }

    fun getDatabaseManager() : IDatabaseManager {
        return mDatabaseManager
    }
}