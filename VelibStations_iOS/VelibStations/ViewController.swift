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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        Knowledge().getEventManager().subscribe(callback: { event in
            self.onReceiveEvents(event: event)
        })
        
        Knowledge().getServiceManager().getWeatherService().getWeather()
    }
    
    func onReceiveEvents(event: Event) {
        var message: String? = nil
        
        if (event.error != nil) {
            message = "\(event.error!)"
        } else {
            message = "\(event.data!)"
        }
        
        print(message!)
    }
}

