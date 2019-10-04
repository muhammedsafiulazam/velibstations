package com.muhammedsafiulazam.common.location

import com.muhammedsafiulazam.common.model.Location

expect class LocationManager() : ILocationManager {
    override fun requestUpdates()
    override fun cancelUpdates()
    override fun getLocation() : Location?
}