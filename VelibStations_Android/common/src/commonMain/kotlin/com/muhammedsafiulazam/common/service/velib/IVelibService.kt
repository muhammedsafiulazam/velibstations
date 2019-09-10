package com.muhammedsafiulazam.common.service.velib

import com.muhammedsafiulazam.common.utils.ConstantUtils

interface IVelibService {
    fun getData(latitude: Double = ConstantUtils.DEFAULT_LATITUDE, longitude: Double = ConstantUtils.DEFAULT_LONGITUDE, radius: Double = ConstantUtils.DEFAULT_RADIUS, index: Int = 0, count: Int = 10)
}