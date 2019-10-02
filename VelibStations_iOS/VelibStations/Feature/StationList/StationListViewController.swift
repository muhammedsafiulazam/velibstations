//
//  StationListViewController.swift
//  VelibStations
//
//  Created by Muhammed Safiul Azam on 02/10/2019.
//

import Foundation

class StationListViewController : BaseViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setViewControllerModel(viewControllerModel: StationListViewControllerModel.self)
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
    }
}
