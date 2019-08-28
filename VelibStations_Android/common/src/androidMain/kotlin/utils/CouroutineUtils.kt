package com.muhammedsafiulazam.mobile.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
actual object CouroutineUtils {
    actual val DISPATCHER: CoroutineDispatcher = Dispatchers.Default
}

