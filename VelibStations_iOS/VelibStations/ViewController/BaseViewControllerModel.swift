//
//  BaseViewController.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 01/10/2019.
//

import Foundation
import UIKit
import CommonKit

class BaseViewControllerModel : IAddOn {
    
    private var mViewController: BaseViewController? = nil
    
    required init() {
        
    }
    
    func getViewController() -> BaseViewController? {
        return mViewController
    }
    
    func setViewController(viewController: BaseViewController?) {
        mViewController = viewController
    }
    
    func viewDidLoad() {
        // Essential addons for activity model.
        addAddOn(type: AddOnType().SERVICE_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().SERVICE_MANAGER)!)
        addAddOn(type: AddOnType().EVENT_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().EVENT_MANAGER)!)
        addAddOn(type: AddOnType().DATABASE_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().DATABASE_MANAGER)!)
    }
    
    func viewDidAppear() {
    }
    
    func viewWillDisappear() {
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
