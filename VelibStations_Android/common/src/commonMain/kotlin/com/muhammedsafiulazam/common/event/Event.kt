package com.muhammedsafiulazam.common.event

import com.muhammedsafiulazam.common.service.model.Error

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

data class Event (val type: String, val data: Any? = null, val error: Error? = null)