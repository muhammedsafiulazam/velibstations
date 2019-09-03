package com.muhammedsafiulazam.common.service

import com.muhammedsafiulazam.common.service.velib.IVelibService

interface IServiceManager {
    fun getVelibService() : IVelibService
}