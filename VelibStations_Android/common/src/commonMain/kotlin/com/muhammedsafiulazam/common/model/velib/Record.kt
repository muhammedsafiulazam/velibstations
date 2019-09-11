package com.muhammedsafiulazam.common.service.velib.model

import com.muhammedsafiulazam.common.utils.MathUtils
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Record(@SerialName("recordid")
                   val id: String?,

                  @SerialName("record_timestamp")
                   val timestamp: String?,

                  @SerialName("fields")
                   val fields: Fields?) {

    fun isWithinGeofence(latitude: Double, longitude: Double, radius: Double) : Boolean {
        val distance = MathUtils.calculateDistance(latitude, longitude,
            fields?.geolocation?.get(0)!!, fields?.geolocation?.get(1)!!)

        if (distance <= radius) {
            return true
        }

        return false
    }
}