package com.muhammedsafiulazam.common.utils

import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
@ThreadLocal
object ConstantUtils {
    @SharedImmutable
    const val DEFAULT_LATITUDE: Double = 48.864716
    @SharedImmutable
    const val DEFAULT_LONGITUDE: Double = 2.349014
    @SharedImmutable
    const val DEFAULT_RADIUS: Double = 1000.0
    @SharedImmutable
    const val DEFAULT_INDEX: Int = 0
    @SharedImmutable
    const val DEFAULT_COUNT: Int = 100
}