//
//  UnitTest.swift
//  VelibStationsTests
//
//  Created by Safi on 28/11/2019.
//

import XCTest
@testable import VelibStations
@testable import CommonKit

class UnitTest: XCTestCase {
    
    let DELAY_MINIMUM: UInt32 = 1000
    let DELAY_AVERAGE: UInt32 = 2500
    let DELAY_MAXIMUM: UInt32 = 5000

    override func setUp() {
        CoroutineUtils().DISPATCHER_MAIN = CoroutineUtils().DISPATCHER_IO
        // TODO - Doesn't make any sense.
        AddOnManager().initialize(context: self)
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
}
