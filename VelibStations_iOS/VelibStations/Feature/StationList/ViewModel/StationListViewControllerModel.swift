//
//  StationListViewControllerModel.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import Foundation
import CommonKit

class StationListViewControllerModel : BaseViewModel {

    private var mLocation: Location? = nil
    private var mEventManager: IEventManager? = nil
    private var mServiceManager: IServiceManager? = nil
    private var mDatabaseManager: IDatabaseManager? = nil

    override func onViewLoad() {
        super.onViewLoad()
        
        mServiceManager = getAddOn(type: AddOnType().SERVICE_MANAGER) as? IServiceManager
        mDatabaseManager = getAddOn(type: AddOnType().DATABASE_MANAGER) as? IDatabaseManager
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        receiveEvents(receive: true)
    }
    
    private func updateLoader(show: Bool) {
        let event = Event(type: StationListEventType.UPDATE_LOADER, data: show, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func updateMessage(message: String?) {
        let event = Event(type: StationListEventType.UPDATE_MESSAGE, data: message, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func responseLoadData(response: Any?) {
        let event = Event(type: StationListEventType.RESPONSE_LOAD_DATA, data: response, error: nil)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationListEventType.REQUEST_LOAD_DATA == event.type) {
            // Show loader.
            updateLoader(show: true)
            
            mLocation = event.data as? Location
            
            // Call service.
            mServiceManager?.getVelibService().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: Constants().DEFAULT_RADIUS, index: Constants().DEFAULT_INDEX, count: Constants().DEFAULT_COUNT)
        } else if (VelibServiceEventType().GET_DATA == event.type) {
            if (event.error != nil) {
                // Show loader.
                updateLoader(show: true)
                
                // Call database.
                mDatabaseManager?.getVelibDatabase().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: Constants().DEFAULT_RADIUS)
            } else {
                // Hide loader.
                updateLoader(show: false)
                
                // Load data.
                responseLoadData(response: event.data)
            }
        } else if (VelibDatabaseEventType().GET_DATA == event.type) {
            if (event.error != nil) {
                // Hide loader.
                updateLoader(show: true)
                
                updateMessage(message: L10n.StationList.Error.data)
            } else {
                // Hide loader.
                updateLoader(show: false)
                
                // Load data.
                responseLoadData(response: event.data)
            }
        }
    }
    
    override func onViewUnload() {
        receiveEvents(receive: false)
        super.onViewUnload()
    }
}
