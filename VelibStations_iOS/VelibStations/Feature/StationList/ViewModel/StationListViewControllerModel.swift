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

    override func onLoad() {
        super.onLoad()
        
        // Set model.
        setModel(model: NSStringFromClass(StationListModel.self))
        
        // Addons.
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        // Enable events.
        receiveEvents(receive: true)
    }
    
    /*
     * Request to load data.
     */
    private func requestLoadData(location: Location?) {
        let event = Event(type: StationListEventType.MODEL_REQUEST_LOAD_DATA, data: location, error: nil)
        mEventManager?.send(event: event)
    }
    
    /*
     * Response with data.
     */
    private func responseLoadData(response: Any?, error: Error?) {
        let event = Event(type: StationListEventType.VIEWMODEL_RESPONSE_LOAD_DATA, data: response, error: error)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationListEventType.VIEWMODEL_REQUEST_LOAD_DATA == event.type) {
            mLocation = event.data as? Location
            requestLoadData(location: mLocation)
        } else if (StationListEventType.MODEL_RESPONSE_LOAD_DATA == event.type) {
            responseLoadData(response: event.data, error: event.error)
        }
    }
    
    override func onUnload() {
        // Disable events.
        receiveEvents(receive: false)
        super.onUnload()
    }
}
