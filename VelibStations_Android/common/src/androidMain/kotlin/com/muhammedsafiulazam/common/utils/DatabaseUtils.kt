package com.muhammedsafiulazam.common.utils

import com.muhammedsafiulazam.common.database.velib.VelibDatabase
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
actual object DatabaseUtils {
    // Schema.
    actual val VELIB_SCHEMA: SqlDriver.Schema = VelibDatabase.Schema

    // Filename.
    actual val VELIB_FILENAME: String = "database_velib.db"

    // Driver.
    actual var VELIB_DRIVER: SqlDriver? = null
}