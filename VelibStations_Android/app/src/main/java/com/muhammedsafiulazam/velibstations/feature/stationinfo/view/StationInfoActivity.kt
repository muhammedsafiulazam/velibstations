package com.muhammedsafiulazam.velibstations.feature.stationinfo.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.view.BaseView
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType
import com.muhammedsafiulazam.velibstations.feature.stationinfo.model.Property
import com.muhammedsafiulazam.velibstations.feature.stationinfo.viewmodel.StationInfoActivityModel
import com.muhammedsafiulazam.velibstations.utils.MapUtils
import kotlinx.android.synthetic.main.activity_stationinfo.*

class StationInfoActivity : BaseView(), OnMapReadyCallback {

    private lateinit var mRecord: Record

    private lateinit var mEventManager: IEventManager

    private var mMap: GoogleMap? = null
    private lateinit var mMapFragment: SupportMapFragment

    private lateinit var mContent: View
    private var mSnackbar: Snackbar? = null

    private val mPropertyList: MutableList<Property> = mutableListOf()
    private val mPropertyListAdapter: PropertyListAdapter by lazy {
        PropertyListAdapter(
            mPropertyList
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stationinfo)
        setViewModel(StationInfoActivityModel::class.java.canonicalName)

        mContent = findViewById(android.R.id.content)

        updateMessage(null)
        updateLoader(true)
        updateView(null)

        mRecord = getData() as Record

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager

        mMapFragment = supportFragmentManager.findFragmentById(R.id.stationinfo_mpv_map) as SupportMapFragment
        mMapFragment.getMapAsync(this)

        // Initialize recycler view.
        stationinfo_ryv_fields.setLayoutManager(LinearLayoutManager(this))
        stationinfo_ryv_fields.adapter = mPropertyListAdapter

        receiveEvents(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        MapUtils.initializeStaticMap(this, mMap!!)

        val fields: Fields = mRecord.fields!!
        val name: String = fields.name!!
        val latitude: Double = fields.geolocation!!.get(0)
        val longitude: Double = fields.geolocation!!.get(1)
        val location = Location(latitude, longitude)
        MapUtils.zoomOnLocation(mMap!!, location)

        val markerOptions = MarkerOptions()
            .title(name)
            .position(LatLng(latitude, longitude))

        mMap?.addMarker(markerOptions)

        // Load data.
        requestLoadData()
    }

    private fun requestLoadData() {
        updateLoader(true)
        val event = Event(StationInfoEventType.VIEWMODEL_REQUEST_LOAD_DATA, mRecord, null)
        mEventManager.send(event)
    }

    private fun updateLoader(show: Boolean) {
        if (show) {
            updateMessage(null)
            stationinfo_pgb_loader.visibility = View.VISIBLE
        } else {
            stationinfo_pgb_loader.visibility = View.GONE
        }
    }

    private fun updateMessage(message: Any?) {
        if (message != null) {
            updateLoader(false)
            updateMessage(null)

            var text: String? = ""
            if (message is Int) {
                text = getString(message)
            } else if (message is String) {
                text = message
            }

            mSnackbar = Snackbar.make(mContent, text!!, Snackbar.LENGTH_INDEFINITE)
            mSnackbar?.setAction(R.string.stationinfo_button_retry, View.OnClickListener {
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

    fun updateView(propertyList: List<Property>?) {
        mPropertyList.clear()
        if (propertyList != null) {
            mPropertyList.addAll(propertyList)
        }
        mPropertyListAdapter.notifyDataSetChanged()

        updateMessage(null)
        updateLoader(false)
    }

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)

        if (TextUtils.equals(StationInfoEventType.VIEWMODEL_RESPONSE_LOAD_DATA, event.type)) {
            updateView(event.data as List<Property>)
        }
    }

    private fun onClickRetry() {
        if (mMap == null) {
            mMapFragment.getMapAsync(this)
        } else {
            requestLoadData()
        }
    }

    override fun onDestroy() {
        receiveEvents(false)
        super.onDestroy()
    }
}