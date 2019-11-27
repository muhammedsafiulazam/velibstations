package com.muhammedsafiulazam.common.utils


import com.muhammedsafiulazam.common.database.velib.VelibDB
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.ThreadLocal

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
@ThreadLocal
actual object DatabaseUtils {
    // Velib database driver.
    @SharedImmutable
    actual val VELIB_DB_DRIVER: SqlDriver = NativeSqliteDriver(VelibDB.Schema, "Velib.db")
}