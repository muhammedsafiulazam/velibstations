package com.muhammedsafiulazam.velibstations.utils

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory



object MapUtils {

    private val ZOOM_MIN: Float = 12.0f
    private val ZOOM_MAX: Float = 18.0f
    private val ZOOM_DEFAULT: Float = 16.0f

    fun initializeDynamicMap(map: GoogleMap) {
        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isScrollGesturesEnabled = true
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.setMinZoomPreference(ZOOM_MIN)
        map.setMaxZoomPreference(ZOOM_MAX)
    }

    fun initializeStaticMap(map: GoogleMap) {
        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isScrollGesturesEnabled = false
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.setMinZoomPreference(ZOOM_DEFAULT)
        map.setMaxZoomPreference(ZOOM_DEFAULT)
    }

    fun zoomOnLocation(map: GoogleMap, location: LatLng, zoom: Float = ZOOM_DEFAULT) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), zoom)
        )
    }
}