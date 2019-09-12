package com.muhammedsafiulazam.common.database.velib

import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.utils.ConstantUtils

interface IVelibDatabase : IAddOn {
    fun getData(latitude: Double = ConstantUtils.DEFAULT_LATITUDE, longitude: Double = ConstantUtils.DEFAULT_LONGITUDE, radius: Double = ConstantUtils.DEFAULT_RADIUS)
    fun addData(dataset: Dataset?)
    fun cleanData()
}