package com.muhammedsafiulazam.common.location

import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.model.Location

actual class LocationManager : AddOn(), ILocationManager {

    actual override fun requestUpdates() {
        error("TODO")
    }
    actual override fun cancelUpdates() {
        error("TODO")
    }
    actual override fun getLocation() : Location? {
        error("TODO")
        return null
    }
}