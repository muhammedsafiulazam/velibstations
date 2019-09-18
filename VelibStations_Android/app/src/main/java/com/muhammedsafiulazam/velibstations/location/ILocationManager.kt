package com.muhammedsafiulazam.velibstations.location

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.muhammedsafiulazam.common.addon.IAddOn

interface ILocationManager : IAddOn {
    fun requestUpdates()
    fun cancelUpdates()
    fun getLocation() : LatLng?
}