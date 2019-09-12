package com.muhammedsafiulazam.common.service.velib

import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.utils.ConstantUtils

interface IVelibService : IAddOn {
    fun getData(latitude: Double = ConstantUtils.DEFAULT_LATITUDE, longitude: Double = ConstantUtils.DEFAULT_LONGITUDE, radius: Double = ConstantUtils.DEFAULT_RADIUS, index: Int = ConstantUtils.DEFAULT_INDEX, count: Int = ConstantUtils.DEFAULT_COUNT)
}