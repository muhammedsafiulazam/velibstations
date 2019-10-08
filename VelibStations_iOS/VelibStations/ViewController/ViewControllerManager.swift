//
//  ViewControllerManager.swift
//  VelibStations
//
//  Created by Safi on 01/10/2019.
//

import Foundation
import UIKit
import CommonKit

class ViewControllerManager: AddOn, IViewControllerManager {
    private var mCurrentViewController: BaseViewController? = nil
    
    func getCurrentViewController() -> BaseViewController? {
        return mCurrentViewController
    }
    
    func onAppearViewController(viewController: BaseViewController) {
        if (!viewController.isBeingPresented) {
            mCurrentViewController = viewController
        }
    }
    
    func onDisappearViewController(viewController: BaseViewController) {
        if (viewController == mCurrentViewController) {
            mCurrentViewController = nil
        }
    }
    
    func loadViewController(storyboard: String, identifier: String, root: Bool) {
        loadViewController(storyboard: storyboard, identifier: identifier, root: root, data: nil)
    }
    
    func loadViewController(storyboard: String, identifier: String, root: Bool, data: AnyObject?) {
        if (mCurrentViewController != nil) {
            let viewController = UIStoryboard(name: storyboard, bundle: nil).instantiateViewController(withIdentifier: identifier) as? BaseViewController
            viewController?.setData(data: data)
            if (viewController != nil) {
                if (!root) {
                    mCurrentViewController?.present(viewController!, animated: true, completion: nil)
                } else {
                    UIApplication.shared.delegate?.window??.rootViewController = viewController
                    UIApplication.shared.delegate?.window??.makeKeyAndVisible()
                }
                
            }
        }
    }
}
