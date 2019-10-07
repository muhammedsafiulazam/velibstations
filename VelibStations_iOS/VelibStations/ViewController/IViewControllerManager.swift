//
//  IViewControllerManager.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 01/10/2019.
//

import CommonKit

protocol IViewControllerManager : IAddOn {
    func getCurrentViewController() -> BaseViewController?
    func onAppearViewController(viewController: BaseViewController)
    func onDisappearViewController(viewController: BaseViewController)
    func loadViewController(storyboard: String, identifier: String, root: Bool)
    func loadViewController(storyboard: String, identifier: String, root: Bool, data: AnyObject?)
}
