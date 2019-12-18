package com.muhammedsafiulazam.velibstations.feature.stationlist.event

object StationListEventType {
    // Tag.
    private const val TAG = "STATION_LIST_EVENT_TYPE_"

    // Event types.
    const val VIEWMODEL_REQUEST_LOAD_DATA: String = TAG + "VIEWMODEL_REQUEST_LOAD_DATA"
    const val VIEWMODEL_RESPONSE_LOAD_DATA: String = TAG + "VIEWMODEL_RESPONSE_LOAD_DATA"

    const val MODEL_REQUEST_LOAD_DATA: String = TAG + "MODEL_REQUEST_LOAD_DATA"
    const val MODEL_RESPONSE_LOAD_DATA: String = TAG + "MODEL_RESPONSE_LOAD_DATA"
}