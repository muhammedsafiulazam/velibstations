package com.muhammedsafiulazam.common.utils

actual object DateTimeUtils {
    actual fun currentTimeMilliseconds() : Long {
        return System.currentTimeMillis()
    }
}