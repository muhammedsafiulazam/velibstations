//
//  ViewControllerModel.swift
//  VelibStations
//
//  Created by Safi on 02/10/2019.
//

import Foundation

class ViewControllerModel : BaseViewControllerModel {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print("viewDidLoad")
    }
    
    override func viewDidAppear() {
        super.viewDidAppear()
        print("viewDidAppear")
    }
    
    override func viewWillDisappear() {
        super.viewWillDisappear()
        print("viewWillDisappear")
    }
}
