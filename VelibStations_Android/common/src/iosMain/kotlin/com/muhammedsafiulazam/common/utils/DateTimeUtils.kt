package com.muhammedsafiulazam.common.utils

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual object DateTimeUtils {
    actual fun currentTimeMilliseconds() : Long {
        return (NSDate().timeIntervalSince1970() as Long) * 1000
    }
}