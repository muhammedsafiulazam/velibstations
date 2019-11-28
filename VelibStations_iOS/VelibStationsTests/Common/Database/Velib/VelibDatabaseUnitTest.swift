//
//  VelibDatabaseUnitTest.swift
//  VelibStationsTests
//
//  Created by Safi on 28/11/2019.
//

import XCTest
@testable import VelibStations
@testable import CommonKit

class VelibDatabaseUnitTest : UnitTest {
    
    override func setUp() {
        // Before every test.
        super.setUp()
    }

    override func tearDown() {
        // After every test.
        super.tearDown()
    }

    func testSaveData() {
        DispatchQueue.main.async {
            var e: Event? = nil
            
            let eventManager: IEventManager = AddOnManager().getAddOn(type: AddOnType().EVENT_MANAGER) as! IEventManager
            eventManager.subscribe(callback: { event in
                e = event
            })
            
            let databaseManager: IDatabaseManager = AddOnManager().getAddOn(type: AddOnType().DATABASE_MANAGER) as! IDatabaseManager
            let dataset: Dataset = Dataset(hits: 0, parameters: nil, records: nil)
            databaseManager.getVelibDatabase().saveData(dataset: dataset)
            
            sleep(self.DELAY_MINIMUM)
            XCTAssertTrue(e != nil && e!.data != nil && e!.error != nil)
        }
    }
}

