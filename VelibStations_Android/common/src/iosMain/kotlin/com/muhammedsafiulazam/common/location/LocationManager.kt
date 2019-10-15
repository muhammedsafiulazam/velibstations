package com.muhammedsafiulazam.common.location

import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.location.event.LocationEventType
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.model.Error
import kotlinx.cinterop.useContents
import platform.CoreLocation.*
import platform.UIKit.UIApplication
import platform.darwin.NSObject

actual class LocationManager : AddOn(), ILocationManager {
    private var mLocation: Location? = null
    private var mLocationManager: CLLocationManager? = null

    init {
        mLocationManager = CLLocationManager()
        mLocationManager?.delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
                onLocationManager(manager, didChangeAuthorizationStatus)
            }

            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                onLocationManager(manager, didUpdateLocations)
            }
        }

        mLocationManager?.distanceFilter = kCLDistanceFilterNone
        mLocationManager?.desiredAccuracy = kCLLocationAccuracyBest
    }

    actual override fun requestUpdates() {
        cancelUpdates()

        val isPermission = (CLLocationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedWhenInUse) || (CLLocationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedAlways)

        if (isPermission) {
            changeLocationUpdates(isPermission)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            val event = Event(LocationEventType.REQUEST_UPDATES, null, null)
            eventManager?.send(event)

        } else {
            mLocationManager?.requestWhenInUseAuthorization()
        }
    }
    actual override fun cancelUpdates() {
        changeLocationUpdates(false)

    }
    actual override fun getLocation() : Location? {
        return mLocation
    }

    private fun changeLocationUpdates(enable: Boolean) {
        if (enable) {
            mLocationManager?.startUpdatingLocation()
        } else {
            mLocationManager?.stopUpdatingLocation()
        }
    }

    private fun onLocationManager(manager: CLLocationManager, didChangeAuthorizationStatus: CLAuthorizationStatus) {
        val isPermission = (CLLocationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedWhenInUse) || (CLLocationManager.authorizationStatus() == kCLAuthorizationStatusAuthorizedAlways)
        changeLocationUpdates(isPermission)
        val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        var error: Error? = null
        if (!isPermission) {
            error = Error("", "")
        }
        val event = Event(LocationEventType.REQUEST_UPDATES, null, error)
        eventManager?.send(event)
    }

    private fun onLocationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        (didUpdateLocations.last() as CLLocation).coordinate.useContents {
            if (mLocation == null || mLocation?.latitude != latitude || mLocation?.longitude != longitude) {
                mLocation = Location(latitude, longitude)
                val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
                val event = Event(LocationEventType.UPDATE_LOCATION, mLocation, null)
                eventManager?.send(event)
            }
        }
    }
}