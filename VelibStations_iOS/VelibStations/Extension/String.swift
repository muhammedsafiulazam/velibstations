//
//  String.swift
//  VelibStations
//
//  Created by Safi on 09/10/2019.
//

import Foundation

extension String {
    func localized(bundle: Bundle = .main, tableName: String = "English") -> String {
        return NSLocalizedString(self, tableName: tableName, value: "**\(self)**", comment: "")
    }
}
