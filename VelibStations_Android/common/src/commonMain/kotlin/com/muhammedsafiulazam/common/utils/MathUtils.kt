package com.muhammedsafiulazam.common.utils

import kotlin.math.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object MathUtils {
    fun calculateDistance(fromLat: Double, fromLng: Double, toLat: Double, toLng: Double): Double {
        val theta = fromLng - toLng
        var dist = sin(getDegreeToRadian(fromLat)) * sin(getDegreeToRadian(toLat)) + (cos(getDegreeToRadian(fromLat))
                * cos(getDegreeToRadian(toLat))
                * cos(getDegreeToRadian(theta)))
        dist = acos(dist)
        dist = getRadianToDegree(dist)
        dist = dist * 60.0 * 1.1515
        return dist * 1000
    }

    fun getDegreeToRadian(deg: Double): Double {
        return deg * PI / 180.0
    }

    fun getRadianToDegree(rad: Double): Double {
        return rad * 180.0 / PI
    }
}