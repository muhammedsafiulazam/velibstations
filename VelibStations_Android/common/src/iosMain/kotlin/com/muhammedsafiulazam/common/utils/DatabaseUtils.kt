package com.muhammedsafiulazam.common.utils


import com.muhammedsafiulazam.common.database.velib.VelibDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
@ThreadLocal
actual object DatabaseUtils {
    // Schema.
    @SharedImmutable
    actual val VELIB_SCHEMA: SqlDriver.Schema = VelibDatabase.Schema

    // Filename.
    @SharedImmutable
    actual val VELIB_FILENAME: String = "database_velib.db"

    // Driver.
    @SharedImmutable
    actual var VELIB_DRIVER: SqlDriver? = NativeSqliteDriver(VELIB_SCHEMA, VELIB_FILENAME)
}