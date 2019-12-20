//
//  StationInfoViewControllerModel.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import Foundation
import CommonKit

class StationInfoViewControllerModel : BaseViewModel {
    
    private var mRecord: Record? = nil
    private var mEventManager: IEventManager? = nil
    
    override func onLoad() {
        super.onLoad()
        
        // Set model.
        setModel(model: NSStringFromClass(StationInfoModel.self))
        
        // Addons.
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        // Enable events.
        receiveEvents(receive: true)
    }
    
    /*
     * Request to load data.
     */
    private func requestLoadData(record: Record?) {
        let event = Event(type: StationInfoEventType.MODEL_REQUEST_LOAD_DATA, data: record, error: nil)
        mEventManager?.send(event: event)
    }
    
    /*
     * Response with data.
     */
    private func responseLoadData(response: Any?, error: Error?) {
        let event = Event(type: StationInfoEventType.VIEWMODEL_RESPONSE_LOAD_DATA, data: response, error: error)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationInfoEventType.VIEWMODEL_REQUEST_LOAD_DATA == event.type) {
            mRecord = event.data as? Record
            requestLoadData(record: mRecord)
        } else if (StationInfoEventType.MODEL_RESPONSE_LOAD_DATA == event.type) {
            responseLoadData(response: event.data, error: event.error)
        }
    }
    
    override func onUnload() {
        // Disable events.
        receiveEvents(receive: false)
        super.onUnload()
    }
}
