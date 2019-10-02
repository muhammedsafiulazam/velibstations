//
//  ILocationManager.swift
//  VelibStations
//
//  Created by Safi on 01/10/2019.
//

import Foundation
import CommonKit
import CoreLocation

protocol ILocationManager : IAddOn {
    func requestUpdates()
    func cancelUpdates()
    func getLocation() -> CLLocation?
}
