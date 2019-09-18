package com.muhammedsafiulazam.velibstations.feature.stationlist

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.database.IDatabaseManager
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.event.IEventSubscriber
import com.muhammedsafiulazam.common.service.IServiceManager
import com.muhammedsafiulazam.common.service.velib.model.Dataset
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.activity.BaseActivity
import com.muhammedsafiulazam.velibstations.activity.IActivityManager
import com.muhammedsafiulazam.velibstations.addon.AddOnTypeNative
import com.muhammedsafiulazam.velibstations.feature.stationinfo.StationInfoActivity
import com.muhammedsafiulazam.velibstations.feature.stationlist.event.StationListEventType
import com.muhammedsafiulazam.velibstations.location.ILocationManager
import com.muhammedsafiulazam.velibstations.location.event.LocationEventType
import com.muhammedsafiulazam.velibstations.utils.MapUtils
import kotlinx.android.synthetic.main.activity_stationlist.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class StationListActivity : BaseActivity(), OnMapReadyCallback {

    private val AUTOCOMPLETE_REQUEST_CODE = 1598

    private var mEventManager: IEventManager? = null
    private var mEventSubscriber: IEventSubscriber? = null
    private var mLocationManager: ILocationManager? = null

    private var mMap: GoogleMap? = null
    private lateinit var mMapFragment: SupportMapFragment
    private var mUserLocation: LatLng? = null
    private var mCameraLocation: LatLng? = null
    private var mDataset: Dataset? = null

    private lateinit var mContent: View
    private var mSnackbar: Snackbar? = null
    private var mSearchMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stationlist)
        setActivityModel(StationListActivityModel::class.java)

        mContent = findViewById(android.R.id.content)

        updateMessage(null)
        updateLoader(false)
        updateView(null)

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager?
        mLocationManager = getAddOn(AddOnTypeNative.LOCATION_MANAGER) as ILocationManager?

        Places.initialize(getApplicationContext(), getString(R.string.google_api_key), Locale.getDefault());
        mMapFragment = supportFragmentManager.findFragmentById(R.id.stationlist_mpv_map) as SupportMapFragment
        mMapFragment.getMapAsync(this)

        subscribeToEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_stationlist, menu)

        mSearchMenuItem = menu.findItem(R.id.stationlist_search)
        mSearchMenuItem?.setOnMenuItemClickListener {
            showAutocompletePlaces()
            true
        }

        return true
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

    private fun loadDataRequest(latitude: Double, longitude: Double) {
        val event = Event(StationListEventType.LOAD_DATA_REQUEST, listOf(latitude, longitude), null)
        mEventManager?.send(event)
    }

    fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(LocationEventType.REQUEST_UPDATES, event.type)) {
            if (event.error != null) {
                updateMessage(getString(R.string.stationlist_error_location))
            }
        } else if (TextUtils.equals(LocationEventType.UPDATE_LOCATION, event.type)) {
            if (mUserLocation == null) {
                mUserLocation = event.data as LatLng
                MapUtils.zoomOnLocation(mMap!!, mUserLocation!!)
            }
        } else if (TextUtils.equals(StationListEventType.LOAD_DATA_BUSY, event.type)) {
            updateLoader(true)
        } else if (TextUtils.equals(StationListEventType.LOAD_DATA_ERROR, event.type)) {
            updateMessage(getString(R.string.stationlist_error_data))
        }
        else if (TextUtils.equals(StationListEventType.LOAD_DATA_RESPONSE, event.type)) {
            mDataset = event.data as Dataset
            updateView(mDataset)
        }
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
            onChangeCameraLocation(mMap?.cameraPosition?.target!!)
        })
        mLocationManager?.requestUpdates()
    }

    private fun updateLoader(show: Boolean) {
        if (show) {
            stationlist_pgb_loader.visibility = View.VISIBLE
        } else {
            stationlist_pgb_loader.visibility = View.GONE
        }
    }

    private fun updateMessage(message: String?) {
        if (message != null) {
            updateMessage(null)
            mSnackbar = Snackbar.make(mContent, message, Snackbar.LENGTH_INDEFINITE)
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
        GlobalScope.launch(Dispatchers.Main) {
            mMap?.clear()

            dataset?.records?.forEach { record ->
                val fields: Fields = record.fields!!
                val name: String = fields.name!!
                val latitude: Double = fields.geolocation!!.get(0)
                val longitude: Double = fields.geolocation!!.get(1)
                val snippet = getString(R.string.stationlist_snippet_bikes, fields.nbBike, fields.nbEBike)

                val markerOptions = MarkerOptions()
                    .title(name)
                    .snippet(snippet)
                    .position(LatLng(latitude, longitude))

                val marker = mMap?.addMarker(markerOptions)
                marker?.tag = record
            }

            updateMessage(null)
            updateLoader(false)
        }
    }

    private fun onClickRetry() {
        if (mMap == null) {
            mMapFragment.getMapAsync(this)
        } else {
            mLocationManager?.requestUpdates()
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
        val activityManager: IActivityManager? = getAddOn(AddOnTypeNative.ACTIVITY_MANAGER) as IActivityManager?
        activityManager?.loadActivity(StationInfoActivity::class.java, null /* data */)
    }

    private fun onChangeCameraLocation(location: LatLng) {
        mCameraLocation = location
        loadDataRequest(mCameraLocation?.latitude!!, mCameraLocation?.longitude!!)
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
                val place: Place = Autocomplete.getPlaceFromIntent(data!!)
                MapUtils.zoomOnLocation(mMap!!, place.latLng!!)
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Snackbar.make(mContent, getString(R.string.stationlist_error_address_location), Snackbar.LENGTH_LONG)
            }
        }
    }

    override fun onDestroy() {
        mLocationManager?.cancelUpdates()
        unsubscribeFromEvents()
        super.onDestroy()
    }
}