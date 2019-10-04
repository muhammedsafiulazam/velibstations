package com.muhammedsafiulazam.common.location

import com.muhammedsafiulazam.common.addon.IAddOn
import com.muhammedsafiulazam.common.model.Location

interface ILocationManager : IAddOn {
    fun requestUpdates()
    fun cancelUpdates()
    fun getLocation() : Location?
}