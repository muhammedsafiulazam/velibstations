package com.muhammedsafiulazam.velibstations.utils

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.velibstations.R


object MapUtils {

    private val ZOOM_MIN: Float = 12.0f
    private val ZOOM_MAX: Float = 18.0f
    private val ZOOM_DEFAULT: Float = 16.0f

    fun initializeDynamicMap(context: Context, map: GoogleMap) {
        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isScrollGesturesEnabled = true
        map.uiSettings.isRotateGesturesEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.setMinZoomPreference(ZOOM_MIN)
        map.setMaxZoomPreference(ZOOM_MAX)
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_map_style))
    }

    fun initializeStaticMap(context: Context, map: GoogleMap) {
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
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_map_style))
    }

    fun zoomOnLocation(map: GoogleMap, location: Location, zoom: Float = ZOOM_DEFAULT) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), zoom)
        )
    }
}