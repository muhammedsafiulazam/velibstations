package com.muhammedsafiulazam.velibstations.feature.stationinfo

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import com.muhammedsafiulazam.velibstations.feature.stationlist.StationListActivityModel
import com.muhammedsafiulazam.velibstations.utils.MapUtils

class StationInfoActivity : BaseActivity(), OnMapReadyCallback {

    private var mRecord: Record? = null

    private var mEventManager: IEventManager? = null
    private var mEventSubscriber: IEventSubscriber? = null

    private var mMap: GoogleMap? = null
    private lateinit var mMapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stationinfo)
        setActivityModel(StationInfoActivityModel::class.java)

        mRecord = getData() as Record?

        mEventManager = AddOnManager.getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?

        mMapFragment = supportFragmentManager.findFragmentById(R.id.stationinfo_mpv_map) as SupportMapFragment
        mMapFragment.getMapAsync(this)

        subscribeToEvents()
    }

    override fun onStart() {
        super.onStart()
        subscribeToEvents()
    }

    override fun onStop() {
        unsubscribeFromEvents()
        super.onStop()
    }

    private fun subscribeToEvents() {
        mEventSubscriber = mEventManager?.subscribe( callback = { event : Event -> Unit
            onReceiveEvents(event)
        })
    }

    private fun unsubscribeFromEvents() {
        mEventManager?.unsubscribe(mEventSubscriber)
    }

    fun onReceiveEvents(event: Event) {
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        MapUtils.initializeStaticMap(this, mMap!!)

        val fields: Fields = mRecord?.fields!!
        val name: String = fields.name!!
        val latitude: Double = fields.geolocation!!.get(0)
        val longitude: Double = fields.geolocation!!.get(1)
        val location = LatLng(latitude, longitude)
        MapUtils.zoomOnLocation(mMap!!, location)

        val markerOptions = MarkerOptions()
            .title(name)
            .position(LatLng(latitude, longitude))

        mMap?.addMarker(markerOptions)
    }

    override fun onDestroy() {
        unsubscribeFromEvents()
        super.onDestroy()
    }
}