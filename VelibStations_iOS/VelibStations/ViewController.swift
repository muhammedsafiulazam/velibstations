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
    
    private var mReceiveChannel: Kotlinx_coroutines_coreReceiveChannel? = nil

    override func viewDidLoad() {
        super.viewDidLoad()
        
        mReceiveChannel = Knowledge().getEventManager().subscribe(callback: { event in
            self.onReceiveEvents(event: event)
        })
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

