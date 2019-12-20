//
//  StationListModel.swift
//  VelibStations
//
//  Created by Safi on 20/12/2019.
//

import Foundation
import CommonKit

class StationListModel : BaseModel {
    
    private var mEventManager: IEventManager? = nil
    private var mServiceManager: IServiceManager? = nil
    private var mDatabaseManager: IDatabaseManager? = nil
    private var mLocation: Location? = nil
    
    override func onLoad() {
        super.onLoad()
        
        // Addons.
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        mServiceManager = getAddOn(type: AddOnType().SERVICE_MANAGER) as? IServiceManager
        mDatabaseManager = getAddOn(type: AddOnType().DATABASE_MANAGER) as? IDatabaseManager
        
        // Enable events.
        receiveEvents(receive: true)
    }
    
    private func responseLoadData(response: Any?, error: Error?) {
        let event = Event(type: StationListEventType.MODEL_RESPONSE_LOAD_DATA, data: response, error: error)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationListEventType.MODEL_REQUEST_LOAD_DATA == event.type) {
            mLocation = event.data as? Location
            // Call service.
            mServiceManager?.getVelibService().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: Constants().DEFAULT_RADIUS, index: Constants().DEFAULT_INDEX, count: Constants().DEFAULT_COUNT)
        } else if (VelibServiceEventType().GET_DATA == event.type) {
            if (event.error != nil) {
                // Call db.
                mDatabaseManager?.getVelibDatabase().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: Constants().DEFAULT_RADIUS)
            } else {
                // Response.
                responseLoadData(response: event.data, error: event.error)
                
                // Save in db.
                mDatabaseManager?.getVelibDatabase().saveData(dataset: event.data as? Dataset)
            }
        } else if (VelibDatabaseEventType().GET_DATA == event.type) {
            // Response.
            responseLoadData(response: event.data, error: event.error)
        }
    }
    
    override func onUnload() {
        // Disable events.
        receiveEvents(receive: false)
        super.onUnload()
    }
}
