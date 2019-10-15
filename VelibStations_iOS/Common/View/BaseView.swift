//
//  BaseView.swift
//  VelibStations
//
//  Created by Safi on 15/10/2019.
//

import Foundation
import UIKit
import CommonKit

class BaseView : UIViewController, IBaseView {
    
    private var mData: Any? = nil
    private var mViewModel: IBaseViewModel? = nil
    private var mEventSubscriber: IEventSubscriber? = nil
    private var isViewReady: Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        isViewReady = false
        onViewLoad()
    }
    
    func getData() -> Any? {
        return mData
    }
    
    func setData(data: Any?) {
        mData = data
    }
    
    func getViewModel() -> IBaseViewModel? {
        return mViewModel
    }
    
    func setViewModel(viewModel: String) {
        mViewModel = (NSClassFromString(viewModel) as! BaseViewModel.Type).init()
        mViewModel?.setView(view: self)
    }
    
    func onViewLoad() {
        addAddOn(type: AddOnType().EVENT_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().EVENT_MANAGER)!)
        addAddOn(type: AddOnType().VIEW_MANAGEER, addOn: AddOnManager().getAddOn(type: AddOnType().VIEW_MANAGEER)!)
        addAddOn(type: AddOnType().LOCATION_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().LOCATION_MANAGER)!)
    }
    
    func onViewStart() {
        
    }
    
    func onViewResume() {
        // NEVER USED
    }
    
    func onViewPause() {
        // NEVER USED
    }
    
    func onViewStop() {
        // NEVER USED
    }
    
    func onViewUnload() {
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if (!isViewReady) {
            isViewReady = true
            mViewModel?.onViewLoad()
        }
        onViewStart()
        mViewModel?.onViewStart()
        onChangeView()
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
    
    override func viewWillDisappear(_ animated: Bool) {
        onViewUnload()
        mViewModel?.onViewUnload()
        
        clearAddOns()
        receiveEvents(receive: false)
        super.viewWillDisappear(animated)
    }
    
    private func onChangeView() {
        let viewManager: IViewManager? = getAddOn(type: AddOnType().VIEW_MANAGEER) as? IViewManager
        viewManager?.onChangeView(view: self)
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

