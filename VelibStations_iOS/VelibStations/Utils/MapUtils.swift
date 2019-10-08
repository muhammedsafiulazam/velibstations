//
//  MapUtils.swift
//  VelibStations
//
//  Created by Safi on 07/10/2019.
//

import Foundation
import GoogleMaps
import CommonKit

class MapUtils {
    
    private static let ZOOM_MIN: Float = 12.0
    private static let ZOOM_MAX: Float = 18.0
    private static let ZOOM_DEFAULT: Float = 16.0

    static func initializeDynamicMap(map: GMSMapView) {
        map.isMyLocationEnabled = true
        map.mapType = GMSMapViewType.normal
        map.settings.zoomGestures = true
        map.settings.scrollGestures = true
        map.settings.rotateGestures = false
        map.settings.myLocationButton = true
        map.settings.compassButton = false
        map.setMinZoom(ZOOM_MIN, maxZoom: ZOOM_MAX)
    }
    
    static func initializeStaticMap(map: GMSMapView) {
        map.isMyLocationEnabled = true
        map.mapType = GMSMapViewType.normal
        map.settings.zoomGestures = false
        map.settings.scrollGestures = false
        map.settings.rotateGestures = false
        map.settings.myLocationButton = false
        map.settings.compassButton = false
        map.setMinZoom(ZOOM_MIN, maxZoom: ZOOM_MAX)
    }
    
    static func zoomOnLocation(map: GMSMapView, location: Location, zoom: Float = ZOOM_DEFAULT) {
        let cameraPosition = GMSCameraPosition.camera(withLatitude: location.latitude, longitude: location.longitude, zoom: zoom)
        map.animate(to: cameraPosition)
    }
}


