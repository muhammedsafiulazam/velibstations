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
    
    private var mServiceManager: ServiceManager = ServiceManager()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        mServiceManager.getWeather(callback: { (response: Weather?, error: Error?) in
            var message: String? = nil
            
            if (error != nil) {
                message = error?.message!
            } else {
                message = response?.description()
            }
            
            print(message!)
        })
    }
}

