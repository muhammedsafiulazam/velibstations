//
//  StationListViewController.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import Foundation
import CommonKit
import GoogleMaps
import MaterialComponents.MaterialSnackbar

class StationListViewController : BaseViewController, GMSMapViewDelegate {
  
    private let KEY_MARKER: String = "KEY_MARKER"
    
    private var mEventManager: IEventManager? = nil
    private var mEventSubscriber: IEventSubscriber? = nil
    private var mLocationManager: ILocationManager? = nil
    
    @IBOutlet var mMap: GMSMapView? = nil
    private var mUserLocation: Location? = nil
    private var mCameraLocation: Location? = nil
    private var mDataset: Dataset? = nil
    
    private var mSnackbar: MDCSnackbarMessage? = nil
    
    @IBOutlet var mActivityIndicator: UIActivityIndicatorView? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setViewControllerModel(viewControllerModel: StationListViewControllerModel.self)
        
        updateMessage(message: nil)
        updateLoader(show: false)
        updateView(dataset: nil)
        
        mMap?.delegate = self
        MapUtils.initializeDynamicMap(map: mMap!)
        
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        mLocationManager = getAddOn(type: AddOnType().LOCATION_MANAGER) as? ILocationManager
        
        subscribeToEvents()
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        subscribeToEvents()
        mLocationManager?.requestUpdates()
    }
    
    private func subscribeToEvents() {
        mEventSubscriber = mEventManager?.subscribe(callback: { event in
            self.onReceiveEvents(event: event)
        })
    }
    
    private func unsubscribeFromEvents() {
        mEventManager?.unsubscribe(eventSubscriber: mEventSubscriber)
    }
    
    func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
        onClickMarker(marker: marker)
        return true
    }
    
    func mapView(_ mapView: GMSMapView, didTapInfoWindowOf marker: GMSMarker) {
        onClickInfoWindow(marker: marker)
    }
    
    func mapView(_ mapView: GMSMapView, idleAt cameraPosition: GMSCameraPosition) {
        let latLng = mMap!.camera.target
        let location = Location(latitude: latLng.latitude, longitude: latLng.longitude)
        onChangeCameraLocation(location: location)
    }
    
    private func loadDataRequest(location: Location) {
        let event = Event(type: StationListEventType.LOAD_DATA_REQUEST, data: location, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func onReceiveEvents(event: Event) {
        if(LocationEventType().REQUEST_UPDATES == event.type) {
            if (event.error != nil) {
                updateMessage(message: "StationList.Error.Location".localized())
            }
        } else if (LocationEventType().UPDATE_LOCATION == event.type) {
            if (mUserLocation == nil) {
                mUserLocation = event.data as? Location
                MapUtils.zoomOnLocation(map: mMap!, location: mUserLocation!)
            }
        } else if (StationListEventType.LOAD_DATA_BUSY == event.type) {
            updateLoader(show: event.data as! Bool)
        } else if (StationListEventType.LOAD_DATA_ERROR == event.type) {
            updateMessage(message: "StationList.Error.Data".localized())
        } else {
            mDataset = event.data as? Dataset
            updateView(dataset: mDataset)
        }
    }
    
    private func updateLoader(show: Bool) {
        if (show) {
            mActivityIndicator?.startAnimating()
        } else {
            mActivityIndicator?.stopAnimating()
        }
    }
    
    private func updateMessage(message: String?) {
        if (message != nil) {
            updateMessage(message: nil)
            
            mSnackbar = MDCSnackbarMessage()
            mSnackbar?.text = message
                    
            let action = MDCSnackbarMessageAction()
            let actionHandler = {() in
                self.onClickRetry()
            }
            action.handler = actionHandler
            action.title = "StationList.Button.Retry".localized()
            mSnackbar?.action = action
            
            MDCSnackbarManager.show(mSnackbar)
            
        } else {
            if (mSnackbar != nil) {
                MDCSnackbarManager.suspendAllMessages()
                mSnackbar = nil
            }
        }
    }
    
    private func updateView(dataset: Dataset?) {
        DispatchQueue.main.async {
            self.mMap?.clear()
            if (dataset != nil) {
                let records = dataset!.records!
                for object in records {
                    let record: Record = object as! Record
                    let fields: Fields = record.fields!
                    let name: String = fields.name!
                    let latitude: Double = fields.geolocation![0] as! Double
                    let longitude: Double = fields.geolocation![1] as! Double

                    let location = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
                    let marker = GMSMarker(position: location)
                    marker.title = name
                    marker.isFlat = true
                    marker.userData = record
                    marker.map = self.mMap
                }
            }
            
            self.updateMessage(message: nil)
            self.updateLoader(show: false)
        }
    }
    
    private func onClickRetry() {
        mLocationManager?.requestUpdates()
    }
    
    private func onClickMarker(marker: GMSMarker) {
        mMap?.selectedMarker = marker
    }
    
    private func onClickInfoWindow(marker: GMSMarker) {
        let record: Record = marker.userData as! Record
        let viewControllerManager: IViewControllerManager? = getAddOn(type: AddOnTypeNative.VIEW_CONTROLLER_MANAGER) as? IViewControllerManager
        viewControllerManager?.loadViewController(storyboard: ViewControllerID.STORYBOARD_STATION_INFO, identifier: ViewControllerID.STORYBOARD_DEFAULT, root: false, data: record)
    }
    
    private func onChangeCameraLocation(location: Location) {
        mCameraLocation = Location(latitude: location.latitude, longitude: location.longitude)
        loadDataRequest(location: location)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        mLocationManager?.cancelUpdates()
        unsubscribeFromEvents()
        super.viewWillDisappear(animated)
    }
}
