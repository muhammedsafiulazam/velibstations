package com.muhammedsafiulazam.common.database

import com.muhammedsafiulazam.common.database.velib.VelibDatabase

interface IDatabaseManager {
    fun getVelibDatabase() : VelibDatabase
}