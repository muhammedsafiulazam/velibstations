package com.muhammedsafiulazam.common.database.velib

import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.utils.ConstantUtils

interface IVelibDatabase {
    fun getRecords(latitude: Double = ConstantUtils.DEFAULT_LATITUDE, longitude: Double = ConstantUtils.DEFAULT_LONGITUDE, radius: Double = ConstantUtils.DEFAULT_RADIUS)
    fun addRecords(records: ArrayList<Record>?)
    fun cleanRecords()
}