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
    private var mEventManager: IEventManager? = nil
    private var mServiceManager: IServiceManager? = nil
    private var mDatabaseManager: IDatabaseManager? = nil
    private var mEventSubscriber: IEventSubscriber? = nil
    private var mLocationManager: ILocationManager? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setViewControllerModel(viewControllerModel: ViewControllerModel.self)
        
        mEventManager = AddOnManager().getAddOn(type: AddOnType().EVENT_MANAGER) as? IEventManager
        mServiceManager = AddOnManager().getAddOn(type: AddOnType().SERVICE_MANAGER) as? IServiceManager
        mDatabaseManager = AddOnManager().getAddOn(type: AddOnType().DATABASE_MANAGER) as? IDatabaseManager
        mLocationManager = getAddOn(type: AddOnType().LOCATION_MANAGER) as? ILocationManager
    
        mEventSubscriber = mEventManager?.subscribe(callback: { event in
            self.onReceiveEvents(event: event)
        })
        
        mServiceManager?.getVelibService().getData(latitude: ConstantUtils().DEFAULT_LATITUDE, longitude: ConstantUtils().DEFAULT_LONGITUDE, radius: ConstantUtils().DEFAULT_RADIUS, index: 0, count: 10)
        
        //mDatabaseManager?.getVelibDatabase().getData(latitude: ConstantUtils().DEFAULT_LATITUDE, longitude: ConstantUtils().DEFAULT_LONGITUDE, radius: ConstantUtils().DEFAULT_RADIUS)
        
        mLocationManager?.requestUpdates()
    }
    
    func onReceiveEvents(event: Event) {
        /*if (VelibServiceEventType().GET_DATA == event.type) {
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
        }*/
        
        if (LocationEventType().REQUEST_UPDATES == event.type) {
            print("BEGIN: REQUEST_UPDATES")
            print("...")
            print("END: REQUEST_UPDATES")
        } else if (LocationEventType().UPDATE_LOCATION == event.type) {
            print("BEGIN: UPDATE_LOCATION")
            print("\(event.data!)")
            print("END: UPDATE_LOCATION")
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        mEventManager?.unsubscribe(receiveChannel: mEventSubscriber)
        super.viewDidDisappear(animated)
    }
}

