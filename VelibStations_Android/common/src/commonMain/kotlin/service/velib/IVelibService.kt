package com.muhammedsafiulazam.mobile.service.velib

interface IVelibService {
    fun getData(latitude: Double = 48.864716, longitude: Double = 2.349014, radius: Double = 1000.0, index: Int = 0, count: Int = 10)
}