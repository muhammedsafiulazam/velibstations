package com.muhammedsafiulazam.common.utils

import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
expect object DatabaseUtils {
    // Schema.
    val VELIB_SCHEMA: SqlDriver.Schema

    // Filename.
    val VELIB_FILENAME: String

    // Driver.
    var VELIB_DRIVER : SqlDriver?
}