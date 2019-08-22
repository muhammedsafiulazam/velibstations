//
//  ViewController.swift
//  VelibStations
//
//  Created by Safi on 13/08/2019.
//  Copyright © 2019 Viseo. All rights reserved.
//

import UIKit
import common


class ViewController: UIViewController {
    
    @IBOutlet var lblMessage: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Test expect / actual.
        lblMessage.text = CommonKt.getPlatformName()
    }
}

