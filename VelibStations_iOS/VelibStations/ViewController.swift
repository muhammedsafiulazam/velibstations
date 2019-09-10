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
        
        //Knowledge().getServiceManager().getVelibService().getData(latitude: ConstantUtils().DEFAULT_LATITUDE, longitude: ConstantUtils().DEFAULT_LONGITUDE, radius: ConstantUtils().DEFAULT_RADIUS, index: 0, count: 10)
        
        Knowledge().getDatabaseManager().getVelibDatabase().getData(latitude: ConstantUtils().DEFAULT_LATITUDE, longitude: ConstantUtils().DEFAULT_LONGITUDE, radius: ConstantUtils().DEFAULT_RADIUS)
    }
    
    func onReceiveEvents(event: Event) {
        if (VelibServiceEventType().GET_DATA == event.type) {
            print("BEGIN: REMOTE DATA")
            if (event.error != nil) {
                print("\(event.error!)")
            } else {
                print("\(event.data!)")
            }
            print("END: REMOTE DATA")
        } else if (VelibDatabaseEventType().GET_DATA == event.type) {
            print("BEGIN: LOCAL_DATA")
            print("\(event.data!)")
            print("END: LOCAL_DATA")
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        Knowledge().getEventManager().unsubscribe(receiveChannel: mReceiveChannel)
        super.viewDidDisappear(animated)
    }
}

