//
//  BaseViewControllerModel.swift
//  VelibStations
//
//  Created by Safi on 01/10/2019.
//

import Foundation
import UIKit
import CommonKit

class BaseViewController : UIViewController, IAddOn {
    
    private var mViewControllerModel: BaseViewControllerModel? = nil
    private var mData: AnyObject? = nil
    private var isViewControllerReady: Bool = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        addAddOn(type: AddOnType().EVENT_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().EVENT_MANAGER)!)
        addAddOn(type: AddOnTypeNative.VIEW_CONTROLLER_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnTypeNative.VIEW_CONTROLLER_MANAGER)!)
        addAddOn(type: AddOnType().LOCATION_MANAGER, addOn: AddOnManager().getAddOn(type: AddOnType().LOCATION_MANAGER)!)
        
        isViewControllerReady = false
    }
    
    func getViewControllerMode() -> BaseViewControllerModel? {
        return mViewControllerModel
    }
    
    func setViewControllerModel(viewControllerModel: BaseViewControllerModel.Type) {
        mViewControllerModel = viewControllerModel.init()
        mViewControllerModel?.setViewController(viewController: self)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        if (!isViewControllerReady) {
            isViewControllerReady = true
            mViewControllerModel?.viewDidLoad()
        }
        
        mViewControllerModel?.viewDidAppear()
        onViewAppear()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        onViewDisappear()
        mViewControllerModel?.viewWillDisappear()
        super.viewWillDisappear(animated)
    }
    
    private func onViewAppear() {
        let viewControllerManager: IViewControllerManager? = getAddOn(type: AddOnTypeNative.VIEW_CONTROLLER_MANAGER) as? IViewControllerManager
        viewControllerManager?.onAppearViewController(viewController: self)
    }
    
    private func onViewDisappear() {
        let viewControllerManager: IViewControllerManager? = getAddOn(type: AddOnTypeNative.VIEW_CONTROLLER_MANAGER) as? IViewControllerManager
        viewControllerManager?.onDisappearViewController(viewController: self)
    }
    
    func getData() -> AnyObject? {
        return mData
    }
    
    func setData(data: AnyObject?) {
        mData = data
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
