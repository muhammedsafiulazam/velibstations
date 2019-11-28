package com.muhammedsafiulazam.common.database

import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.addon.AddOnType
import com.muhammedsafiulazam.common.core.AndroidUnitTest
import com.muhammedsafiulazam.common.database.velib.IVelibDatabase
import org.junit.Test
import kotlin.test.asserter

class DatabaseManagerUnitTest : AndroidUnitTest() {
    @Test
    /**
     * Access photo database.
     */
    fun accessVelibDatabase() {
        val databaseManager: IDatabaseManager = AddOnManager.getAddOn(AddOnType.DATABASE_MANAGER) as IDatabaseManager
        val velibDatabase: IVelibDatabase? = databaseManager.getVelibDatabase()
        asserter.assertTrue("accessVelibDatabase", velibDatabase != null)
    }
}