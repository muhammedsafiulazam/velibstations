package com.muhammedsafiulazam.mobile.event

import com.muhammedsafiulazam.mobile.service.model.Error

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

data class Event (val type: String, val data: Any? = null, val error: Error? = null)