package com.muhammedsafiulazam.common.database

import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.database.velib.IVelibDatabase

interface IDatabaseManager : IAddOn {
    fun getVelibDatabase() : IVelibDatabase
}