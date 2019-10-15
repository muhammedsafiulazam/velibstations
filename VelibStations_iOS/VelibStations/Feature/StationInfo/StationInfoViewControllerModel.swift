//
//  StationInfoViewControllerModel.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import Foundation
import CommonKit

class StationInfoViewControllerModel : BaseViewModel {
    
    private var mEventManager: IEventManager? = nil
    
    private var mRecord: Record? = nil
    
    override func onViewLoad() {
        super.onViewLoad()
        
        mRecord = getView()?.getData() as? Record
        mEventManager = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        receiveEvents(receive: true)
    }
    
    
    private func loadDataBusy(busy: Bool) {
        let event = Event(type: StationInfoEventType.LOAD_DATA_BUSY, data: busy, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func loadDataError(error: String?) {
        let event = Event(type: StationInfoEventType.LOAD_DATA_ERROR, data: error, error: nil)
        mEventManager?.send(event: event)
    }
    
    private func loadDataResponse(response: Any?) {
        let event = Event(type: StationInfoEventType.LOAD_DATA_RESPONSE, data: response, error: nil)
        mEventManager?.send(event: event)
    }
    
    override func onReceiveEvents(event: Event) {
        super.onReceiveEvents(event: event)
        
        if (StationInfoEventType.LOAD_DATA_REQUEST == event.type) {
            // Show loader.
            loadDataBusy(busy: true)
            onCreatePropertyList(record: mRecord)
        }
    }
    
    private func onCreatePropertyList(record: Record?) {
        let fields = record?.fields
        let properties: NSMutableArray = NSMutableArray()
        
        properties.add(Property(title: "StationInfo.Property.Name".localized(), value: (fields?.name)!))
        properties.add(Property(title: "StationInfo.Property.Code".localized(), value: (fields?.code)!))
        properties.add(Property(title: "StationInfo.Property.State".localized(), value: (fields?.state)!))
        properties.add(Property(title: "StationInfo.Property.Due.Date".localized(), value: (fields?.dueDate)!))
        
        let latitude = fields?.geolocation?.object(at: 0) as! Double
        let longitude = fields?.geolocation?.object(at: 1) as! Double
        properties.add(Property(title: "StationInfo.Property.Latitude".localized(), value: "\(latitude)"))
        properties.add(Property(title: "StationInfo.Property.Longitude".localized(), value: "\(longitude)"))
        
        properties.add(Property(title: "StationInfo.Property.Kiosk.State".localized(), value: (fields?.kioskState)!))
        properties.add(Property(title: "StationInfo.Property.Credit.Card".localized(), value: (fields?.creditCard)!))
        properties.add(Property(title: "StationInfo.Property.Overflow.Activation".localized(), value: (fields?.overflowActivation)!))
        properties.add(Property(title: "StationInfo.Property.Max.Bike.Overflow".localized(), value: "\((fields?.kioskState)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.eDock".localized(), value: "\((fields?.nbEDock)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.Free.eDock".localized(), value: "\((fields?.nbFreeEDock)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.Dock".localized(), value: "\((fields?.nbDock)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.Free.Dock".localized(), value: "\((fields?.nbFreeDock)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.eBike".localized(), value: "\((fields?.nbEBike)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.Bike".localized(), value: "\((fields?.nbBike)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.Bike.Overflow".localized(), value: "\((fields?.nbBikeOverflow)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.eBike.Overflow".localized(), value: "\((fields?.nbEBikeOverflow)!)"))
        properties.add(Property(title: "StationInfo.Property.NB.Overflow".localized(), value: (fields?.overflow)!))
        properties.add(Property(title: "StationInfo.Property.Density.Level".localized(), value: (fields?.densityLevel)!))
        
        loadDataResponse(response: properties)
    }
    
    override func onViewUnload() {
        receiveEvents(receive: false)
        super.onViewUnload()
    }
}
