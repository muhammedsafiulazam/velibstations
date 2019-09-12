package com.muhammedsafiulazam.common.service

import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.service.velib.IVelibService

interface IServiceManager : IAddOn {
    fun getVelibService() : IVelibService
}