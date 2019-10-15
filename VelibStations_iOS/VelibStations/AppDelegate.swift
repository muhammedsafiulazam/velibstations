//
//  AppDelegate.swift
//  VelibStations
//
//  Created by Safi on 13/08/2019.
//  Copyright © 2019 Viseo. All rights reserved.
//

import UIKit
import CommonKit
import GoogleMaps
import GooglePlaces

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        var dictionary: NSDictionary?
        if let path = Bundle.main.path(forResource: "Keys", ofType: "plist") {
           dictionary = NSDictionary(contentsOfFile: path)
        }
        
        let googleAPIKey: String? = dictionary?.value(forKey: "GoogleAPIKey") as? String
        if (googleAPIKey != nil) {
            GMSServices.provideAPIKey(googleAPIKey!)
            GMSPlacesClient.provideAPIKey(googleAPIKey!)
        }
        
        let viewManager: IViewManager = AddOnManager().getAddOn(type: AddOnType().VIEW_MANAGEER) as! IViewManager
        viewManager.loadViewMechanism(mechanism: { view, story, info, data in
            let currentViewController: UIViewController? = viewManager.getCurrentView() as? UIViewController
            let viewController = UIStoryboard(name: story!, bundle: nil).instantiateViewController(withIdentifier: view!) as? BaseView
            viewController?.setData(data: data)
            if (viewController != nil) {
                
                if (currentViewController != nil && info != nil && !(info as! Bool)) {
                    currentViewController?.present(viewController!, animated: true, completion: nil)
                } else {
                    UIApplication.shared.delegate?.window??.rootViewController = viewController
                    UIApplication.shared.delegate?.window??.makeKeyAndVisible()
                }
            }
        })
        
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

