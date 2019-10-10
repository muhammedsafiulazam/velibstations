//
//  StationListViewControllerModel.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import Foundation
import CommonKit

class StationListViewControllerModel : BaseViewControllerModel {
    
    private var mEventManager: IEventManager? = nil
    private var mServiceManager: IServiceManager? = nil
    private var mDatabaseManager: IDatabaseManager? = nil
    private var mLocation: Location? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        mServiceManager = getAddOn(type: AddOnType().SERVICE_MANAGER) as? IServiceManager
        mDatabaseManager = getAddOn(type: AddOnType().DATABASE_MANAGER) as? IDatabaseManager
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        receiveEvents(receive: true)
    }
    
    override func viewDidAppear() {
        super.viewDidAppear()
    }
    
    private func loadDataBusy(busy: Bool) {
        let event = Event(type: StationListEventType.LOAD_DATA_BUSY, data: busy, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func loadDataError(error: String?) {
        let event = Event(type: StationListEventType.LOAD_DATA_ERROR, data: error, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func loadDataResponse(response: Any?) {
        let event = Event(type: StationListEventType.LOAD_DATA_RESPONSE, data: response, error: nil)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationListEventType.LOAD_DATA_REQUEST == event.type) {
            // Show loader.
            loadDataBusy(busy: true)
            
            mLocation = event.data as? Location
            
            // Call service.
            mServiceManager?.getVelibService().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: Constants().DEFAULT_RADIUS, index: Constants().DEFAULT_INDEX, count: Constants().DEFAULT_COUNT)
        } else if (VelibServiceEventType().GET_DATA == event.type) {
            if (event.error != nil) {
                // Show loader.
                loadDataBusy(busy: true)
                
                // Call database.
                mDatabaseManager?.getVelibDatabase().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: Constants().DEFAULT_RADIUS)
            } else {
                // Hide loader.
                loadDataBusy(busy: false)
                
                // Load data.
                loadDataResponse(response: event.data)
            }
        } else if (VelibDatabaseEventType().GET_DATA == event.type) {
            if (event.error != nil) {
                // Hide loader.
                loadDataBusy(busy: true)
                
                loadDataError(error: "StationList.Error.Data".localized())
            } else {
                // Hide loader.
                loadDataBusy(busy: false)
                
                // Load data.
                loadDataResponse(response: event.data)
            }
        }
    }
    
    override func viewWillDisappear() {
        receiveEvents(receive: false)
        super.viewWillDisappear()
    }
}
