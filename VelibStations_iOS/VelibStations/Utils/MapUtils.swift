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
        applyMapStyle(map: map)
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
        applyMapStyle(map: map)
    }
    
    private static func applyMapStyle(map: GMSMapView) {
        do {
            // Set the map style by passing the URL of the local file.
            if let styleURL = Bundle.main.url(forResource: "google_map_style", withExtension: "json") {
                map.mapStyle = try GMSMapStyle(contentsOfFileURL: styleURL)
            } else {
                NSLog("Unable to find style json.")
            }
        } catch {
            NSLog("\(error)")
        }
    }
    
    static func zoomOnLocation(map: GMSMapView, location: Location, zoom: Float = ZOOM_DEFAULT) {
        let cameraPosition = GMSCameraPosition.camera(withLatitude: location.latitude, longitude: location.longitude, zoom: zoom)
        map.animate(to: cameraPosition)
    }
}


