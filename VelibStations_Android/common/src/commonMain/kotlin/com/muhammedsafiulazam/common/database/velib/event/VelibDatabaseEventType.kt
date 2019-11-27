package com.muhammedsafiulazam.common.database.velib.event

object VelibDatabaseEventType {
    // Tag.
    private const val TAG = "VELIB_DATABASE_EVENT_TYPE_"

    // Types
    const val GET_DATA: String = TAG + "GET_DATA"
    const val SAVE_DATA: String = TAG + "SAVE_DATA"
    const val CLEAN_DATA: String = TAG + "CLEAN_DATA"
}