package com.muhammedsafiulazam.common.database

import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.database.velib.IVelibDatabase
import com.muhammedsafiulazam.common.database.velib.VelibDB
import com.muhammedsafiulazam.common.database.velib.VelibDatabase
import com.muhammedsafiulazam.common.utils.DatabaseUtils

class DatabaseManager : AddOn(), IDatabaseManager {

    private val mVelibDB: VelibDB by lazy {
        VelibDB(DatabaseUtils.VELIB_DB_DRIVER!!)
    }

    private val mVelibDatabase: IVelibDatabase by lazy {
        val velibDatabase = VelibDatabase(mVelibDB)
        velibDatabase.addAddOns(getAddOns())
        velibDatabase
    }

    override fun getVelibDatabase(): IVelibDatabase {
        return mVelibDatabase
    }
}