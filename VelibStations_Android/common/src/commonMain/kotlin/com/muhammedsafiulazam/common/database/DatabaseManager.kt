package com.muhammedsafiulazam.common.database

import com.muhammedsafiulazam.common.database.velib.VelibDatabase
import com.muhammedsafiulazam.common.utils.DatabaseUtils

class DatabaseManager : IDatabaseManager {
    private val mVelibDatabase: VelibDatabase by lazy {
        VelibDatabase(DatabaseUtils.VELIB_DRIVER!!)
    }

    override fun getVelibDatabase(): VelibDatabase {
        return mVelibDatabase
    }
}