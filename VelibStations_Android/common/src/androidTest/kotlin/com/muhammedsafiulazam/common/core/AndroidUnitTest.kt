package com.muhammedsafiulazam.common.core

import android.content.Context
import android.content.res.Resources
import com.muhammedsafiulazam.common.addon.AddOnManager
import com.muhammedsafiulazam.common.utils.CoroutineUtils
import io.mockk.every
import io.mockk.mockk
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

open class AndroidUnitTest {
    val DELAY_MINIMUM: Long = 1000
    val DELAY_AVERAGE: Long = 2500
    val DELAY_MAXIMUM: Long = 5000

    @BeforeTest
    fun beforeTest() {

        CoroutineUtils.DISPATCHER_MAIN = CoroutineUtils.DISPATCHER_IO

        val context: Context = mockk<Context>()
        every { context.resources } returns mockk<Resources>()
        // TODO - Quick Solution.
        every { context.resources.getString(any()) } returns ""
        every { context.resources.getInteger(any()) } returns 0
        AddOnManager.initialize(context)
    }

    @AfterTest
    fun afterTest() {

    }
}