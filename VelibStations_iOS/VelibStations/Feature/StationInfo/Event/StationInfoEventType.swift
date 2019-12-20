//
//  StationInfoEventType.swift
//  VelibStations
//
//  Created by Safi on 09/10/2019.
//

import Foundation

class StationInfoEventType {
    // Tag.
    private static let TAG: String = "STATION_INFO_EVENT_TYPE_"
    
    // Event types.
    static let VIEWMODEL_REQUEST_LOAD_DATA: String = TAG + "VIEWMODEL_REQUEST_LOAD_DATA"
    static let VIEWMODEL_RESPONSE_LOAD_DATA: String = TAG + "VIEWMODEL_RESPONSE_LOAD_DATA"
    
    static let MODEL_REQUEST_LOAD_DATA: String = TAG + "MODEL_REQUEST_LOAD_DATA"
    static let MODEL_RESPONSE_LOAD_DATA: String = TAG + "MODEL_RESPONSE_LOAD_DATA"
}
