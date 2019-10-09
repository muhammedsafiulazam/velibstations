//
//  PropertyTableCell.swift
//  VelibStations
//
//  Created by Safi on 09/10/2019.
//

import Foundation
import UIKit

class PropertyTableCell : UITableViewCell {
    
    @IBOutlet weak var mTitleLabel: UILabel!
    @IBOutlet weak var mValueLabel: UILabel!
    
    func updateView(property: Property) {
        mTitleLabel.text = property.title
        mValueLabel.text = property.value.capitalized
    }
}
