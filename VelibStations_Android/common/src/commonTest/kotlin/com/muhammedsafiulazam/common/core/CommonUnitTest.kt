package com.muhammedsafiulazam.common.core

import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

open class CommonUnitTest {
    val DELAY_MINIMUM: Long = 1000
    val DELAY_AVERAGE: Long = 2500
    val DELAY_MAXIMUM: Long = 5000

    @BeforeTest
    fun beforeTest() {
        CoroutineUtils.DISPATCHER_MAIN = CoroutineUtils.DISPATCHER_IO
        // TODO - Don't make any sense.
        AddOnManager.initialize(this)
    }

    @AfterTest
    fun afterTest() {

    }
}