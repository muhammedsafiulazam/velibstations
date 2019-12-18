package com.muhammedsafiulazam.velibstations.feature.stationlist.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.location.ILocationManager
import com.muhammedsafiulazam.common.location.event.LocationEventType
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import com.muhammedsafiulazam.common.view.BaseView
import com.muhammedsafiulazam.common.view.IViewManager
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.view.StationInfoActivity
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType
import com.muhammedsafiulazam.velibstations.feature.stationlist.viewmodel.StationListActivityModel
import com.muhammedsafiulazam.velibstations.utils.MapUtils
import kotlinx.android.synthetic.main.activity_stationlist.*
import java.util.*


class StationListActivity : BaseView(), OnMapReadyCallback {

    private val AUTOCOMPLETE_REQUEST_CODE = 1598

    private lateinit var mEventManager: IEventManager
    private lateinit var mLocationManager: ILocationManager
    private lateinit var mViewManager: IViewManager

    private var mMap: GoogleMap? = null
    private lateinit var mMapFragment: SupportMapFragment
    private var mUserLocation: Location? = null
    private var mCameraLocation: Location? = null
    private var mDataset: Dataset? = null

    private lateinit var mContent: View
    private var mSnackbar: Snackbar? = null
    private lateinit var mSearchMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stationlist)
        setViewModel(StationListActivityModel::class.java.canonicalName)

        mContent = findViewById(android.R.id.content)

        updateMessage(null)
        updateLoader(false)
        updateView(null)

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mLocationManager = getAddOn(AddOnType.LOCATION_MANAGER) as ILocationManager
        mViewManager = getAddOn(AddOnType.VIEW_MANAGEER) as IViewManager

        Places.initialize(getApplicationContext(), getString(R.string.google_api_key), Locale.getDefault());
        mMapFragment = supportFragmentManager.findFragmentById(R.id.stationlist_mpv_map) as SupportMapFragment
        mMapFragment.getMapAsync(this)

        // Receive events.
        receiveEvents(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_stationlist, menu)

        mSearchMenuItem = menu.findItem(R.id.stationlist_search)
        mSearchMenuItem.setOnMenuItemClickListener {
            showAutocompletePlaces()
            true
        }

        return true
    }

    private fun requestLoadData(location: Location) {
        val event = Event(StationListEventType.REQUEST_LOAD_DATA, location, null)
        mEventManager.send(event)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        MapUtils.initializeDynamicMap(this, mMap!!)
        mMap?.setOnMyLocationButtonClickListener {
            onClickMyLocation()
            true
        }
        mMap?.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { marker ->
            onClickMarker(marker)
            true
        })
        mMap?.setOnInfoWindowClickListener { marker ->
            onClickInfoWindow(marker)
        }
        mMap?.setOnCameraIdleListener(GoogleMap.OnCameraIdleListener {
            var latLng = mMap?.cameraPosition?.target!!
            var location = Location(latLng.latitude, latLng.longitude)
            onChangeCameraLocation(location)
        })
        mLocationManager.requestUpdates()
    }

    private fun updateLoader(show: Boolean) {
        if (show) {
            stationlist_pgb_loader.visibility = View.VISIBLE
        } else {
            stationlist_pgb_loader.visibility = View.GONE
        }
    }

    private fun updateMessage(message: Any?) {
        if (message != null) {
            updateMessage(null)

            var text: String? = ""
            if (message is Int) {
                text = getString(message)
            } else if (message is String) {
                text = message
            }

            mSnackbar = Snackbar.make(mContent, text!!, Snackbar.LENGTH_INDEFINITE)
            mSnackbar?.setAction(R.string.stationlist_button_retry, View.OnClickListener {
                onClickRetry()
            })
            mSnackbar?.show()

        } else {
            if (mSnackbar != null) {
                mSnackbar?.dismiss()
                mSnackbar = null
            }
        }
    }

    private fun updateView(dataset: Dataset?) {
        CoroutineUtils.execute(CoroutineUtils.DISPATCHER_MAIN, block = {
            mMap?.clear()
            dataset?.records?.forEach { record ->
                val fields: Fields = record.fields!!
                val name: String = fields.name!!
                val latitude: Double = fields.geolocation!!.get(0)
                val longitude: Double = fields.geolocation!!.get(1)

                val markerOptions = MarkerOptions()
                    .title(name)
                    .position(LatLng(latitude, longitude))

                val marker = mMap?.addMarker(markerOptions)
                marker?.tag = record
            }

            updateMessage(null)
            updateLoader(false)
        })
    }

    private fun onClickRetry() {
        if (mMap == null) {
            mMapFragment.getMapAsync(this)
        } else {
            mLocationManager.requestUpdates()
        }
    }

    private fun onClickMyLocation() {
        MapUtils.zoomOnLocation(mMap!!, mUserLocation!!)
    }

    private fun onClickMarker(marker: Marker) {
        val record: Record = marker.tag as Record
        marker.showInfoWindow()
    }

    private fun onClickInfoWindow(marker: Marker) {
        val record: Record = marker.tag as Record
        mViewManager.loadView(StationInfoActivity::class.java.canonicalName, record)
    }

    private fun onChangeCameraLocation(location: Location) {
        mCameraLocation = Location(location.latitude, location.longitude)
        requestLoadData(mCameraLocation!!)
    }

    private fun showAutocompletePlaces() {
        val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .setTypeFilter(TypeFilter.ADDRESS)
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                val location: LatLng = Autocomplete.getPlaceFromIntent(data!!).latLng!!
                MapUtils.zoomOnLocation(mMap!!, Location(location.latitude, location.longitude))
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Snackbar.make(mContent, getString(R.string.stationlist_error_address_location), Snackbar.LENGTH_LONG)
            }
        }
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)

        if (TextUtils.equals(LocationEventType.REQUEST_UPDATES, event.type)) {
            if (event.error != null) {
                updateMessage(getString(R.string.stationlist_error_location))
            } else {
                mMap?.isMyLocationEnabled = true
            }
        } else if (TextUtils.equals(LocationEventType.UPDATE_LOCATION, event.type)) {
            if (mUserLocation == null) {
                mUserLocation = event.data as Location
                MapUtils.zoomOnLocation(mMap!!, mUserLocation!!)
            }
        } else if (TextUtils.equals(StationListEventType.UPDATE_LOADER, event.type)) {
            updateLoader(event.data as Boolean)
        } else if (TextUtils.equals(StationListEventType.UPDATE_MESSAGE, event.type)) {
            updateMessage(getString(R.string.stationlist_error_data))
        }
        else if (TextUtils.equals(StationListEventType.RESPONSE_LOAD_DATA, event.type)) {
            mDataset = event.data as Dataset
            updateView(mDataset)
        }
    }

    override fun onDestroy() {
        mLocationManager.cancelUpdates()
        receiveEvents(false)
        super.onDestroy()
    }
}