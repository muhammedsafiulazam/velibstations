package com.muhammedsafiulazam.common.service.velib

import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.contants.Constants

interface IVelibService : IAddOn {
    fun getData(latitude: Double = Constants.DEFAULT_LATITUDE, longitude: Double = Constants.DEFAULT_LONGITUDE, radius: Double = Constants.DEFAULT_RADIUS, index: Int = Constants.DEFAULT_INDEX, count: Int = Constants.DEFAULT_COUNT)
}