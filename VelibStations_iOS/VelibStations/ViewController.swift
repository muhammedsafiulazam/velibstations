//
//  ViewController.swift
//  VelibStations
//
//  Created by Safi on 13/08/2019.
//  Copyright © 2019 Viseo. All rights reserved.
//

import UIKit
import CommonKit



class ViewController: UIViewController {
    
    private var mReceiveChannel: Kotlinx_coroutines_coreReceiveChannel? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        mReceiveChannel = Knowledge().getEventManager().subscribe(callback: { event in
            self.onReceiveEvents(event: event)
        })
        
        Knowledge().getServiceManager().getVelibService().getData(latitude: 48.864716, longitude: 2.349014, radius: 1000, index: 0, count: 10)
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
    
    override func viewDidDisappear(_ animated: Bool) {
        Knowledge().getEventManager().unsubscribe(receiveChannel: mReceiveChannel)
        super.viewDidDisappear(animated)
    }
}

