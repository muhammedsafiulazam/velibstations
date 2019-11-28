//
//  String.swift
//  VelibStations
//
//  Created by Safi on 09/10/2019.
//

import Foundation

extension String {
    func localized(bundle: Bundle = .main) -> String {
        return NSLocalizedString(self, value: "**\(self)**", comment: "")
    }
}
