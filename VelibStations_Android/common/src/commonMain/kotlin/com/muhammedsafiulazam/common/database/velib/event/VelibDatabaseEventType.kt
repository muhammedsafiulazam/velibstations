package com.muhammedsafiulazam.common.database.velib.event

object VelibDatabaseEventType {
    // Tag.
    private const val TAG = "VELIB_DATABASE_EVENT_TYPE_"

    // Types
    const val GET_DATA: String = TAG + "GET_DATA"
    const val ADD_DATA: String = TAG + "ADD_DATA"
    const val CLEAN_DATA: String = TAG + "CLEAN_DATA"
}