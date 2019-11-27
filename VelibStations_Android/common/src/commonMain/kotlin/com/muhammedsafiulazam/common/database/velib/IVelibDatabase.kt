package com.muhammedsafiulazam.common.database.velib

import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.contants.Constants
import com.muhammedsafiulazam.common.service.velib.model.Dataset

interface IVelibDatabase : IAddOn {
    fun getData(latitude: Double = Constants.DEFAULT_LATITUDE, longitude: Double = Constants.DEFAULT_LONGITUDE, radius: Double = Constants.DEFAULT_RADIUS)
    fun saveData(dataset: Dataset?)
    fun cleanData()
}