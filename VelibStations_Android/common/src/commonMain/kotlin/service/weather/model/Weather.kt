package com.muhammedsafiulazam.mobile.service.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(val coord: Coordinate, val base: String)