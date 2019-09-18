package com.muhammedsafiulazam.velibstations.location

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.muhammedsafiulazam.common.addon.AddOn
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.service.model.Error
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import com.muhammedsafiulazam.velibstations.activity.IActivityManager
import com.muhammedsafiulazam.velibstations.addon.AddOnTypeNative
import com.muhammedsafiulazam.velibstations.location.event.LocationEventType



class LocationManager : AddOn(), ILocationManager {

    private val REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 12345
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLocation: LatLng? = null

    private val mLocationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(60000)
        .setFastestInterval(1000)

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation
            if (location != null) {
                onLocationUpdate(location.latitude, location.longitude)
            }
        }
    }

    override fun requestUpdates() {

        cancelUpdates()

        val activityManager: IActivityManager? = getAddOn(AddOnTypeNative.ACTIVITY_MANAGER) as IActivityManager?
        val activity: BaseActivity = activityManager?.getCurrentActivity()!!

        val isPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (isPermission) {
            changeLocationUpdates(isPermission)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            val event = Event(LocationEventType.REQUEST_UPDATES, null, null)
            eventManager?.send(event)
        } else {
            activity.requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSION_ACCESS_FINE_LOCATION)
        }
    }

    override fun cancelUpdates() {
        changeLocationUpdates(false)
    }

    // Just for activities.
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_ACCESS_FINE_LOCATION) {
            val isPermission: Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED
            changeLocationUpdates(isPermission)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            var error: Error? = null
            if (!isPermission) {
                error = Error("", "")
            }
            val event = Event(LocationEventType.REQUEST_UPDATES, null, error)
            eventManager?.send(event)
        }
    }

    private fun changeLocationUpdates(enable: Boolean) {
        if (enable) {
            val activityManager: IActivityManager? = getAddOn(AddOnTypeNative.ACTIVITY_MANAGER) as IActivityManager?
            val activity: BaseActivity = activityManager?.getCurrentActivity()!!
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
            mFusedLocationProviderClient?.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
        } else {
            mFusedLocationProviderClient?.removeLocationUpdates(mLocationCallback)
            mFusedLocationProviderClient = null
        }
    }

    private fun onLocationUpdate(latitude: Double, longitude: Double) {
        if (mLocation == null || mLocation?.latitude != latitude || mLocation?.longitude != longitude) {
            mLocation = LatLng(latitude, longitude)
            val eventManager: IEventManager? = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
            val event = Event(LocationEventType.UPDATE_LOCATION, mLocation, null)
            eventManager?.send(event)
        }
    }

    override fun getLocation(): LatLng? {
        return mLocation
    }
}