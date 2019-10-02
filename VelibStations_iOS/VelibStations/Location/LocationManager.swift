//
//  LocationManager.swift
//  VelibStations
//
//  Created by Safi on 01/10/2019.
//

import Foundation
import CoreLocation
import CommonKit

class LocationManager : AddOn, ILocationManager, CLLocationManagerDelegate {
    private var mLocation: CLLocation? = nil
    private var mLocationManger: CLLocationManager? = nil
    
    override init() {
        super.init()
    
        mLocationManger = CLLocationManager()
        mLocationManger?.delegate = self
        mLocationManger?.distanceFilter = kCLDistanceFilterNone
        mLocationManger?.desiredAccuracy = kCLLocationAccuracyBest
    }
    
    func requestUpdates() {
        cancelUpdates()
        
        let isPermission = (CLLocationManager.authorizationStatus() == CLAuthorizationStatus.authorizedWhenInUse) || (CLLocationManager.authorizationStatus() == CLAuthorizationStatus.authorizedAlways)
        
        if (isPermission) {
            changeLocationUpdates(enable: isPermission)
            let eventManager: IEventManager? = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
            let event = Event(type: LocationEventType.REQUEST_UPDATES, data: nil, error: nil)
            eventManager?.send(event: event)
        } else {
            mLocationManger?.requestWhenInUseAuthorization()
        }
    }
    
    func cancelUpdates() {
        changeLocationUpdates(enable: false)
    }
    
    func getLocation() -> CLLocation? {
        return mLocation
    }
    
    func changeLocationUpdates(enable: Bool) {
        if (enable) {
            mLocationManger?.startUpdatingLocation()
        } else {
            mLocationManger?.stopUpdatingLocation()
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        let isPermission = (CLLocationManager.authorizationStatus() == CLAuthorizationStatus.authorizedWhenInUse) || (CLLocationManager.authorizationStatus() == CLAuthorizationStatus.authorizedAlways)
        changeLocationUpdates(enable: isPermission)
        let eventManager: IEventManager? = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        var error: Error? = nil
        if (!isPermission) {
            error = Error(code: "", message: "")
        }
        let event = Event(type: LocationEventType.REQUEST_UPDATES, data: nil, error: error)
        eventManager?.send(event: event)
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if let location = locations.first {
            if (mLocation == nil || mLocation?.coordinate.latitude != location.coordinate.latitude || mLocation?.coordinate.longitude != location.coordinate.longitude) {
                mLocation = CLLocation(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
                let eventManager: IEventManager? = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
                let event = Event(type: LocationEventType.UPDATE_LOCATION, data: mLocation, error: nil)
                eventManager?.send(event: event)
            }
        }
    }
}
