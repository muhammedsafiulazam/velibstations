package com.muhammedsafiulazam.mobile.service

import com.muhammedsafiulazam.mobile.service.velib.IVelibService

interface IServiceManager {
    fun getVelibService() : IVelibService
}