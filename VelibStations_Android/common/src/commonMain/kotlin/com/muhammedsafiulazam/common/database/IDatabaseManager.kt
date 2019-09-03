package com.muhammedsafiulazam.common.database

import com.muhammedsafiulazam.common.database.velib.IVelibDatabase

interface IDatabaseManager {
    fun getVelibDatabase() : IVelibDatabase
}