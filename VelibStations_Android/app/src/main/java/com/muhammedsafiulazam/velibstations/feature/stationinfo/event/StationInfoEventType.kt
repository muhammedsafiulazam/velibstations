package com.muhammedsafiulazam.velibstations.feature.stationinfo.event

object StationInfoEventType {
    // Tag.
    private const val TAG = "STATION_INFO_EVENT_TYPE_"

    const val LOAD_DATA_REQUEST: String = TAG + "LOAD_DATA_REQUEST"
    const val LOAD_DATA_BUSY: String = TAG + "LOAD_DATA_BUSY"
    const val LOAD_DATA_ERROR: String = TAG + "LOAD_DATA_ERROR"
    const val LOAD_DATA_RESPONSE: String = TAG + "LOAD_DATA_RESPONSE"
}