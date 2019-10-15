//
//  BaseViewModel.swift
//  VelibStations
//
//  Created by Safi on 15/10/2019.
//

import Foundation
import UIKit
import CommonKit

class BaseViewModel : IBaseViewModel {
    
    private var mView: IBaseView? = nil
    private var mEventSubscriber: IEventSubscriber? = nil
    
    required init() {
        
    }
    
    func getView() -> IBaseView? {
        return mView
    }
    
    func setView(view: IBaseView?) {
        mView = view
    }
    
    func onViewLoad() {
        // Essential addons for activity model.
        addAddOn(type: AddOnType().SERVICE_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().SERVICE_MANAGER)!)
        addAddOn(type: AddOnType().EVENT_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().EVENT_MANAGER)!)
        addAddOn(type: AddOnType().DATABASE_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().DATABASE_MANAGER)!)
    }
    
    func onViewStart() {
        
    }
    
    func onViewResume() {
        
    }
    
    func onViewPause() {
        
    }
    
    func onViewStop() {
        
    }
    
    func onViewUnload() {
        clearAddOns()
        receiveEvents(receive: false)
    }
    
    func receiveEvents(receive: Bool) {
        let eventManager: IEventManager? = getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        
        if (receive) {
            mEventSubscriber = eventManager?.subscribe(callback: { event in
                self.onReceiveEvents(event: event)
            })
        } else {
            if (mEventSubscriber != nil) {
                eventManager?.unsubscribe(eventSubscriber: mEventSubscriber)
            }
            mEventSubscriber = nil
        }
    }
    
    func onReceiveEvents(event: Event) {
        
    }
    
    // Addons related methods.
    private let mAddOn: IAddOn = AddOn()
    
    func getAddOn(type: String) -> IAddOn? {
        return mAddOn.getAddOn(type: type)
    }
    
    func getAddOns() -> [String : IAddOn] {
        return mAddOn.getAddOns()
    }
    
    func addAddOn(type: String, addOn: IAddOn) {
        mAddOn.addAddOn(type: type, addOn: addOn)
    }
    
    func addAddOns(addons: [String : IAddOn]) {
        mAddOn.addAddOns(addons: addons)
    }
    
    func removeAddOn(type: String) {
        mAddOn.removeAddOn(type: type)
    }
    
    func removeAddOns(types: [String]) {
        mAddOn.removeAddOns(types: types)
    }
    
    func clearAddOns() {
        mAddOn.clearAddOns()
    }
}

