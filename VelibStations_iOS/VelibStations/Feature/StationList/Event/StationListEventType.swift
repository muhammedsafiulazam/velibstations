//
//  StationListEventType.swift
//  VelibStations
//
//  Created by Safi on 07/10/2019.
//

import Foundation

class StationListEventType {
    // Tag.
    private static let TAG: String = "STATION_LIST_EVENT_TYPE_"
    
    // Types.
    static let UPDATE_LOADER: String = TAG + "UPDATE_LOADER"
    static let UPDATE_MESSAGE: String = TAG + "UPDATE_MESSAGE"
    
    static let REQUEST_LOAD_DATA: String = TAG + "REQUEST_LOAD_DATA"
    static let RESPONSE_LOAD_DATA: String = TAG + "RESPONSE_LOAD_DATA"
}
