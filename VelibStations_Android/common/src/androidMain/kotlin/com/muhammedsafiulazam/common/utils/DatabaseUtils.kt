package com.muhammedsafiulazam.common.utils

import com.muhammedsafiulazam.common.database.velib.VelibDB
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
actual object DatabaseUtils {
    // Schema.
    actual val VELIB_DB_SCHEMA: SqlDriver.Schema = VelibDB.Schema

    // Filename.
    actual val VELIB_DB_FILENAME: String = "velib.db"

    // Driver.
    actual var VELIB_DB_DRIVER: SqlDriver? = null
}