package com.muhammedsafiulazam.velibstations.feature.stationlist.event

object StationListEventType {
    // Tag.
    private const val TAG = "STATION_LIST_EVENT_TYPE_"

    const val LOAD_DATA_REQUEST: String = TAG + "LOAD_DATA_REQUEST"
    const val LOAD_DATA_BUSY: String = TAG + "LOAD_DATA_BUSY"
    const val LOAD_DATA_ERROR: String = TAG + "LOAD_DATA_ERROR"
    const val LOAD_DATA_RESPONSE: String = TAG + "LOAD_DATA_RESPONSE"
}