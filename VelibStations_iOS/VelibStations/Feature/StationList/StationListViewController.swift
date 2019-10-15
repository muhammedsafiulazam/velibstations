//
//  StationListViewController.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import UIKit
import Foundation
import CommonKit
import GoogleMaps
import GooglePlaces
import MaterialComponents.MaterialSnackbar

class StationListViewController : BaseView, GMSMapViewDelegate, UISearchBarDelegate, GMSAutocompleteViewControllerDelegate {
    
    private let KEY_MARKER: String = "KEY_MARKER"
    
    @IBOutlet weak var mMapView: GMSMapView!
    @IBOutlet weak var mActivityIndicatorView: UIActivityIndicatorView!
    @IBOutlet weak var mSearchBar: UISearchBar!
    
    private var mEventManager: IEventManager? = nil
    private var mLocationManager: ILocationManager? = nil
    
    private var mUserLocation: Location? = nil
    private var mCameraLocation: Location? = nil
    private var mDataset: Dataset? = nil
    
    private var mSnackbar: MDCSnackbarMessage? = nil
    
    override func onViewLoad() {
        super.onViewLoad()
        
        setViewModel(viewModel: NSStringFromClass(StationListViewControllerModel.self))
        
        updateMessage(message: nil)
        updateLoader(show: false)
        updateView(dataset: nil)
        
        mMapView.delegate = self
        MapUtils.initializeDynamicMap(map: mMapView)
        
        mSearchBar.delegate = self
        mSearchBar.placeholder = "StationList.Search".localized()
        
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        mLocationManager = getAddOn(type: AddOnType().LOCATION_MANAGER) as? ILocationManager
        
        receiveEvents(receive: true)
    }
    
    override func onViewStart() {
        super.onViewStart()
        mLocationManager?.requestUpdates()
    }
    
    func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
        onClickMarker(marker: marker)
        return true
    }
    
    func mapView(_ mapView: GMSMapView, didTapInfoWindowOf marker: GMSMarker) {
        onClickInfoWindow(marker: marker)
    }
    
    func mapView(_ mapView: GMSMapView, idleAt cameraPosition: GMSCameraPosition) {
        let latLng = mMapView.camera.target
        let location = Location(latitude: latLng.latitude, longitude: latLng.longitude)
        onChangeCameraLocation(location: location)
    }
    
    private func loadDataRequest(location: Location) {
        let event = Event(type: StationListEventType.LOAD_DATA_REQUEST, data: location, error: nil)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if(LocationEventType().REQUEST_UPDATES == event.type) {
            if (event.error != nil) {
                updateMessage(message: "StationList.Error.Location".localized())
            }
        } else if (LocationEventType().UPDATE_LOCATION == event.type) {
            if (mUserLocation == nil) {
                mUserLocation = event.data as? Location
                MapUtils.zoomOnLocation(map: mMapView, location: mUserLocation!)
            }
        } else if (StationListEventType.LOAD_DATA_BUSY == event.type) {
            updateLoader(show: event.data as! Bool)
        } else if (StationListEventType.LOAD_DATA_ERROR == event.type) {
            updateMessage(message: "StationList.Error.Data".localized())
        } else if (StationListEventType.LOAD_DATA_RESPONSE == event.type) {
            mDataset = event.data as? Dataset
            updateView(dataset: mDataset)
        }
    }
    
    private func updateLoader(show: Bool) {
        if (show) {
            mActivityIndicatorView.startAnimating()
        } else {
            mActivityIndicatorView.stopAnimating()
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
    
    private func addMarker(record: Record) {
        let fields: Fields = record.fields!
        let name: String = fields.name!
        let latitude: Double = fields.geolocation![0] as! Double
        let longitude: Double = fields.geolocation![1] as! Double

        let location = CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
        let marker = GMSMarker(position: location)
        marker.title = name
        marker.isFlat = true
        marker.userData = record
        marker.map = self.mMapView
    }
    
    private func updateView(dataset: Dataset?) {
        CoroutineUtils().execute(dispatcher: CoroutineUtils().DISPATCHER_MAIN, block: {
            self.mMapView.clear()
            if (dataset != nil) {
                let records = dataset!.records!
                for object in records {
                    let record: Record = object as! Record
                    self.addMarker(record: record)
                }
            }
            
            self.updateMessage(message: nil)
            self.updateLoader(show: false)
        })
    }
    
    private func onClickRetry() {
        mLocationManager?.requestUpdates()
    }
    
    private func onClickMarker(marker: GMSMarker) {
        mMapView.selectedMarker = marker
    }
    
    private func onClickInfoWindow(marker: GMSMarker) {
        let record: Record = marker.userData as! Record
        let viewManager: IViewManager? = getAddOn(type: AddOnType().VIEW_MANAGEER) as? IViewManager
        viewManager?.loadView(view: ViewID.VIEW_CONTROLLER_DEFAULT, story: ViewID.STORYBOARD_STATION_INFO, info: false, data: record)
    }
    
    private func onChangeCameraLocation(location: Location) {
        mCameraLocation = Location(latitude: location.latitude, longitude: location.longitude)
        loadDataRequest(location: location)
    }
    
    func searchBarShouldBeginEditing(_ searchBar: UISearchBar) -> Bool {
        onClickSearchBar()
        return false
    }
    
    private func onClickSearchBar() {
        let autocompleteController = GMSAutocompleteViewController()
        autocompleteController.delegate = self
        
        let filter = GMSAutocompleteFilter()
        filter.type = .address
        autocompleteController.autocompleteFilter = filter
        
        present(autocompleteController, animated: true, completion: nil)
    }
    
    func viewController(_ viewController: GMSAutocompleteViewController, didAutocompleteWith place: GMSPlace) {
        let location = Location(latitude: place.coordinate.latitude, longitude: place.coordinate.longitude)
        MapUtils.zoomOnLocation(map: mMapView, location: location)
        dismiss(animated: true, completion: nil)
    }
    
    func viewController(_ viewController: GMSAutocompleteViewController, didFailAutocompleteWithError error: Swift.Error) {
        let snackbar = MDCSnackbarMessage()
        snackbar.text = "StationList.Error.Address.Location".localized()
        MDCSnackbarManager.show(snackbar)
    }
    
    func wasCancelled(_ viewController: GMSAutocompleteViewController) {
        dismiss(animated: true, completion: nil)
    }
    
    override func onViewUnload() {
        mLocationManager?.cancelUpdates()
        receiveEvents(receive: false)
        super.onViewUnload()
    }
}
