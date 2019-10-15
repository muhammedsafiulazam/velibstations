package com.muhammedsafiulazam.velibstations.feature.stationinfo

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.muhammedsafiulazam.common.view.BaseView
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.event.Event
import com.muhammedsafiulazam.common.event.IEventManager
import com.muhammedsafiulazam.common.model.Location
import com.muhammedsafiulazam.common.service.velib.model.Fields
import com.muhammedsafiulazam.common.service.velib.model.Record
import com.muhammedsafiulazam.common.view.IBaseViewModel
import com.muhammedsafiulazam.velibstations.R
import com.muhammedsafiulazam.velibstations.feature.stationinfo.event.StationInfoEventType
import com.muhammedsafiulazam.velibstations.utils.MapUtils
import kotlinx.android.synthetic.main.activity_stationinfo.*
import kotlin.reflect.KClass

class StationInfoActivity : BaseView(), OnMapReadyCallback {

    private lateinit var mRecord: Record

    private lateinit var mEventManager: IEventManager

    private var mMap: GoogleMap? = null
    private lateinit var mMapFragment: SupportMapFragment

    private lateinit var mContent: View
    private var mSnackbar: Snackbar? = null

    private val mPropertyList: MutableList<Property> = mutableListOf()
    private val mPropertyListAdapter: PropertyListAdapter by lazy {
        PropertyListAdapter(mPropertyList)
    }

    override fun onViewLoad() {
        super.onViewLoad()

        setContentView(R.layout.activity_stationinfo)
        setViewModel(StationInfoActivityModel::class.java.canonicalName)

        mContent = findViewById(android.R.id.content)

        updateMessage(null)
        updateLoader(false)
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

    override fun onReceiveEvents(event: Event) {
        super.onReceiveEvents(event)

        if (TextUtils.equals(StationInfoEventType.LOAD_DATA_BUSY, event.type)) {
            updateLoader(event.data as Boolean)
        }
        else if (TextUtils.equals(StationInfoEventType.LOAD_DATA_ERROR, event.type)) {
            updateMessage(getString(R.string.stationinfo_error_data))
        }
        else if (TextUtils.equals(StationInfoEventType.LOAD_DATA_RESPONSE, event.type)) {
            updateView(event.data as List<Property>)
        }
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
        loadDataRequest()
    }

    private fun loadDataRequest() {
        val event = Event(StationInfoEventType.LOAD_DATA_REQUEST, null, null)
        mEventManager.send(event)
    }

    private fun updateLoader(show: Boolean) {
        if (show) {
            stationinfo_pgb_loader.visibility = View.VISIBLE
        } else {
            stationinfo_pgb_loader.visibility = View.GONE
        }
    }

    private fun updateMessage(message: String?) {
        if (message != null) {
            updateMessage(null)
            mSnackbar = Snackbar.make(mContent, message, Snackbar.LENGTH_INDEFINITE)
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

    private fun onClickRetry() {
        if (mMap == null) {
            mMapFragment.getMapAsync(this)
        } else {
            loadDataRequest()
        }
    }


    override fun onViewUnload() {
        receiveEvents(false)
        super.onViewUnload()
    }
}