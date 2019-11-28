//
//  DatabaseManagerUnitTest.swift
//  VelibStationsTests
//
//  Created by Safi on 28/11/2019.
//

import XCTest
@testable import VelibStations
@testable import CommonKit

class DatabaseManagerUnitTest : UnitTest {
    
    override func setUp() {
        // Before every test.
        super.setUp()
    }

    override func tearDown() {
        // After every test.
        super.tearDown()
    }

    func testAccessVelibDatabase() {
        let databaseManager: IDatabaseManager = AddOnManager().getAddOn(type: AddOnType().DATABASE_MANAGER) as! IDatabaseManager
        let velibDatabase: IVelibDatabase? = databaseManager.getVelibDatabase() as IVelibDatabase
        XCTAssertTrue(velibDatabase != nil)
    }
}
