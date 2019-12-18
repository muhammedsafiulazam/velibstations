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
        
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        receiveEvents(receive: true)
    }
    
    
    private func updateLoader(show: Bool) {
        let event = Event(type: StationInfoEventType.UPDATE_LOADER, data: show, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func updateMessage(message: String?) {
        let event = Event(type: StationInfoEventType.UPDATE_MESSAGE, data: message, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func responseLoadData(response: Any?) {
        let event = Event(type: StationInfoEventType.RESPONSE_LOAD_DATA, data: response, error: nil)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationInfoEventType.REQUEST_LOAD_DATA == event.type) {
            // Show loader.
            updateLoader(show: true)
            
            mRecord = event.data as? Record
            onCreatePropertyList(record: mRecord)
        }
    }
    
    private func onCreatePropertyList(record: Record?) {
        let fields = record?.fields
        let properties: NSMutableArray = NSMutableArray()
        
        properties.add(Property(title: L10n.StationInfo.Property.name, value: (fields?.name)!))
        properties.add(Property(title: L10n.StationInfo.Property.code, value: (fields?.code)!))
        properties.add(Property(title: L10n.StationInfo.Property.state, value: (fields?.state)!))
        properties.add(Property(title: L10n.StationInfo.Property.Due.date, value: (fields?.dueDate)!))
        
        let latitude = fields?.geolocation?.object(at: 0) as! Double
        let longitude = fields?.geolocation?.object(at: 1) as! Double
        properties.add(Property(title: L10n.StationInfo.Property.latitude, value: "\(latitude)"))
        properties.add(Property(title: L10n.StationInfo.Property.longitude, value: "\(longitude)"))
        
        properties.add(Property(title: L10n.StationInfo.Property.Kiosk.state, value: (fields?.kioskState)!))
        properties.add(Property(title: L10n.StationInfo.Property.Credit.card, value: (fields?.creditCard)!))
        properties.add(Property(title: L10n.StationInfo.Property.Overflow.activation, value: (fields?.overflowActivation)!))
        properties.add(Property(title: L10n.StationInfo.Property.Max.Bike.overflow, value: "\((fields?.kioskState)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.eDock, value: "\((fields?.nbEDock)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.Free.eDock, value: "\((fields?.nbFreeEDock)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.dock, value: "\((fields?.nbDock)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.Free.dock, value: "\((fields?.nbFreeDock)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.eBike, value: "\((fields?.nbEBike)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.bike, value: "\((fields?.nbBike)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.Bike.overflow, value: "\((fields?.nbBikeOverflow)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.EBike.overflow, value: "\((fields?.nbEBikeOverflow)!)"))
        properties.add(Property(title: L10n.StationInfo.Property.Nb.overflow, value: (fields?.overflow)!))
        properties.add(Property(title: L10n.StationInfo.Property.Density.level, value: (fields?.densityLevel)!))
        
        responseLoadData(response: properties)
    }
    
    override func onUnload() {
        receiveEvents(receive: false)
        super.onUnload()
    }
}
