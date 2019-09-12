package com.muhammedsafiulazam.common.addon

import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

@ThreadLocal
object AddOnType {
    @SharedImmutable
    const val EVENT_MANAGER: String = "EVENT_MANAGER"
    @SharedImmutable
    const val SERVICE_MANAGER: String = "SERVICE_MANAGER"
    @SharedImmutable
    const val DATABASE_MANAGER: String = "DATABASE_MANAGER"
}