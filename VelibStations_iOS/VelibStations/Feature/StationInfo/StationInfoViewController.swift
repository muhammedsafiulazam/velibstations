//
//  StationInfoViewController.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import Foundation
import GoogleMaps
import CommonKit
import MaterialComponents.MaterialSnackbar

class StationInfoViewController : BaseView, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var mMapView: GMSMapView!
    @IBOutlet weak var mTableView: UITableView!
    @IBOutlet weak var mActivityIndicatorView: UIActivityIndicatorView!
    
    private var mEventManager: IEventManager? = nil
    
    private var mSnackbar: MDCSnackbarMessage? = nil
    
    private var mRecord: Record? = nil
    private var mProperties: NSMutableArray = NSMutableArray()
    
    override func onViewLoad() {
        super.onViewLoad()
        
        setViewModel(viewModel: NSStringFromClass(StationInfoViewControllerModel.self))
        
        updateMessage(message: nil)
        updateLoader(show: false)
        updateView(properties: nil)
        
        mRecord = getData() as? Record
        
        mTableView.delegate = self
        mTableView.dataSource = self
        
        MapUtils.initializeStaticMap(map: mMapView)
        
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        receiveEvents(receive: true)
        
        loadDataRequest()
    }
    
    private func zoomOnRecord(record: Record) {
        let fields: Fields = record.fields!
        let latitude: Double = fields.geolocation![0] as! Double
        let longitude: Double = fields.geolocation![1] as! Double
        let location: Location = Location(latitude: latitude, longitude: longitude)
        MapUtils.zoomOnLocation(map: mMapView, location: location)
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
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationInfoEventType.LOAD_DATA_BUSY == event.type) {
            updateLoader(show: event.data as! Bool)
        } else if (StationInfoEventType.LOAD_DATA_ERROR == event.type) {
            updateMessage(message: "StationInfo.Error.Data".localized())
        } else if (StationInfoEventType.LOAD_DATA_RESPONSE == event.type) {
            updateView(properties: event.data as? NSArray)
        }
    }
    
    private func loadDataRequest() {
        let event = Event(type: StationInfoEventType.LOAD_DATA_REQUEST, data: nil, error: nil)
        mEventManager?.send(event: event)
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
            action.title = "StationInfo.Button.Retry".localized()
            mSnackbar?.action = action
            
            MDCSnackbarManager.show(mSnackbar)
            
        } else {
            if (mSnackbar != nil) {
                MDCSnackbarManager.suspendAllMessages()
                mSnackbar = nil
            }
        }
    }
    
    private func updateView(properties: NSArray?) {
        CoroutineUtils().execute(dispatcher: CoroutineUtils().DISPATCHER_MAIN, block: {
            
            self.addMarker(record: self.mRecord!)
            self.zoomOnRecord(record: self.mRecord!)
            
            self.mProperties.removeAllObjects()
            if (properties != nil) {
                self.mProperties.addObjects(from: properties as! [Any])
            }
            
            self.mTableView.reloadData()
            
            self.updateMessage(message: nil)
            self.updateLoader(show: false)
        })
    }
    
    private func onClickRetry() {
        loadDataRequest()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return mProperties.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "PropertyTableCell", for: indexPath) as! PropertyTableCell
        let property = mProperties.object(at: indexPath.row)
        cell.updateView(property: property as! Property)
        return cell
    }
    
    override func onViewUnload() {
        receiveEvents(receive: false)
        super.onViewUnload()
    }
}
