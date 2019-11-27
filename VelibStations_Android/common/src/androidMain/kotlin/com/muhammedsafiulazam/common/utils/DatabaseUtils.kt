package com.muhammedsafiulazam.common.utils

import android.content.Context
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.velib.VelibDB
import com.muhammedsafiulazam.photoalbum.context.IContextManager
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
actual object DatabaseUtils {
    // Velib database driver.
    actual val VELIB_DB_DRIVER: SqlDriver by lazy {
        val contextManager: IContextManager? = AddOnManager.getAddOn(AddOnType.CONTEXT_MANAGEER) as IContextManager?
        val context: Context? = contextManager?.getContext() as Context?
        AndroidSqliteDriver(VelibDB.Schema, context!!, "Photo.db")
    }
}