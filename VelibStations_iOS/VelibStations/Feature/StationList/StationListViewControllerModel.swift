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
    private var mEventSubscriber: IEventSubscriber? = nil
    private var mLocation: Location? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        mServiceManager = getAddOn(type: AddOnType().SERVICE_MANAGER) as? IServiceManager
        mDatabaseManager = getAddOn(type: AddOnType().DATABASE_MANAGER) as? IDatabaseManager
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
    }
    
    override func viewDidAppear() {
        super.viewDidAppear()
        subscribeToEvents()
    }
    
    private func subscribeToEvents() {
        mEventSubscriber = mEventManager?.subscribe(callback: { event in
            self.onReceiveEvents(event: event)
        })
    }
    
    private func unsubscribeFromEvents() {
        mEventManager?.unsubscribe(eventSubscriber: mEventSubscriber)
    }
    
    private func loadDataBusy(busy: Bool) {
        let event = Event(type: StationListEventType.LOAD_DATA_BUSY, data: busy, error: nil)
        let eventManager: IEventManager? = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        eventManager?.send(event: event)
    }
    
    private func loadDataError(error: String?) {
        let event = Event(type: StationListEventType.LOAD_DATA_ERROR, data: error, error: nil)
        let eventManager: IEventManager? = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        eventManager?.send(event: event)
    }
    
    private func loadDataResponse(response: Any?) {
        let event = Event(type: StationListEventType.LOAD_DATA_RESPONSE, data: response, error: nil)
        let eventManager: IEventManager? = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        eventManager?.send(event: event)
    }
    
    private func onReceiveEvents(event: Event) {
        if (StationListEventType.LOAD_DATA_REQUEST == event.type) {
            // Show loader.
            loadDataBusy(busy: true)
            
            mLocation = event.data as? Location
            
            // Call service.
            mServiceManager?.getVelibService().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: ConstantUtils().DEFAULT_RADIUS, index: ConstantUtils().DEFAULT_INDEX, count: ConstantUtils().DEFAULT_COUNT)
        } else if (VelibServiceEventType().GET_DATA == event.type) {
            if (event.error != nil) {
                // Show loader.
                loadDataBusy(busy: true)
                
                // Call database.
                mDatabaseManager?.getVelibDatabase().getData(latitude: mLocation!.latitude, longitude: mLocation!.longitude, radius: ConstantUtils().DEFAULT_RADIUS)
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
                
                loadDataError(error: "Sorry! Failed to load data.")
            } else {
                // Hide loader.
                loadDataBusy(busy: false)
                
                // Load data.
                loadDataResponse(response: event.data)
            }
        }
    }
    
    override func viewWillDisappear() {
        unsubscribeFromEvents()
        super.viewWillDisappear()
    }
}
