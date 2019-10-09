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
    
    // Types.
    static let LOAD_DATA_REQUEST: String = TAG + "LOAD_DATA_REQUEST"
    static let LOAD_DATA_BUSY: String = TAG + "LOAD_DATA_BUSY"
    static let LOAD_DATA_ERROR: String = TAG + "LOAD_DATA_ERROR"
    static let LOAD_DATA_RESPONSE: String = TAG + "LOAD_DATA_RESPONSE"
}
