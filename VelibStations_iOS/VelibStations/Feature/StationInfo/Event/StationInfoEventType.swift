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
    static let UPDATE_LOADER: String = TAG + "UPDATE_LOADER"
    static let UPDATE_MESSAGE: String = TAG + "UPDATE_MESSAGE"
    
    static let REQUEST_LOAD_DATA: String = TAG + "REQUEST_LOAD_DATA"
    static let RESPONSE_LOAD_DATA: String = TAG + "RESPONSE_LOAD_DATA"
}
