package com.muhammedsafiulazam.common.utils

import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
expect object DatabaseUtils {
    // Velib database driver.
    val VELIB_DB_DRIVER : SqlDriver
}