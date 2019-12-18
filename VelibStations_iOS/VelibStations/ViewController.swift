//
//  ViewController.swift
//  VelibStations
//
//  Created by Safi on 13/08/2019.
//  Copyright © 2019 Viseo. All rights reserved.
//

import UIKit
import CommonKit

class ViewController: BaseView {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let viewManager: IViewManager? = getAddOn(type: AddOnType().VIEW_MANAGEER) as? IViewManager
        viewManager?.loadView(view: ViewID.VIEW_CONTROLLER_DEFAULT, story: ViewID.STORYBOARD_STATION_LIST)
    }
}

