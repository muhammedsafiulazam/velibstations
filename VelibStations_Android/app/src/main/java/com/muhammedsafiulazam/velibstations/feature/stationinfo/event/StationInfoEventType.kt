package com.muhammedsafiulazam.velibstations.feature.stationinfo.event

object StationInfoEventType {
    // Tag.
    private const val TAG = "STATION_INFO_EVENT_TYPE_"

    // Types.
    const val UPDATE_LOADER: String = TAG + "UPDATE_LOADER"
    const val UPDATE_MESSAGE: String = TAG + "UPDATE_MESSAGE"

    const val REQUEST_LOAD_DATA: String = TAG + "REQUEST_LOAD_DATA"
    const val RESPONSE_LOAD_DATA: String = TAG + "RESPONSE_LOAD_DATA"
}