//
//  ViewController.swift
//  VelibStations
//
//  Created by Safi on 13/08/2019.
//  Copyright © 2019 Viseo. All rights reserved.
//

import UIKit
import CommonKit

class ViewController: BaseViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        let viewControllerManager: IViewControllerManager? = getAddOn(type: AddOnTypeNative.VIEW_CONTROLLER_MANAGER) as? IViewControllerManager
        viewControllerManager?.loadViewController(storyboard: ViewControllerID.STORYBOARD_STATION_LIST, identifier: ViewControllerID.VIEW_CONTROLLER_DEFAULT, root: true)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
    }
}

