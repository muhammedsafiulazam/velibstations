package com.muhammedsafiulazam.common.addon

import com.muhammedsafiulazam.common.database.DatabaseManager
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.event.EventManager
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.ServiceManager
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object AddOnManager : AddOn(), IAddOn {

    // Service manager.
    @SharedImmutable
    private val mServiceManager: IServiceManager by lazy {
        ServiceManager()
    }

    // Event manager.
    @SharedImmutable
    private val mEventManager: IEventManager by lazy {
        EventManager()
    }

    // Database manager.
    @SharedImmutable
    private val mDatabaseManager: IDatabaseManager by lazy {
        DatabaseManager()
    }

    fun initialize() {
        onInitialize()
    }

    private fun onInitialize() {
        // Service manager.
        addAddOn(AddOnType.SERVICE_MANAGER, mServiceManager)

        // Event manager.
        addAddOn(AddOnType.EVENT_MANAGER, mEventManager)

        // Database manager.
        addAddOn(AddOnType.DATABASE_MANAGER, mDatabaseManager)

        // Now assign individually.

        // Service manager.
        mServiceManager.addAddOn(AddOnType.EVENT_MANAGER, mEventManager)
        mServiceManager.addAddOn(AddOnType.DATABASE_MANAGER, mDatabaseManager)

        // Database manager.
        mDatabaseManager.addAddOn(AddOnType.EVENT_MANAGER, mEventManager)
    }
}