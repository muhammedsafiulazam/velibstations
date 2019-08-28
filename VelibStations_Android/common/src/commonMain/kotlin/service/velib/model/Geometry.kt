package com.muhammedsafiulazam.mobile.service.velib.model

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(val type: String?,
                    val coordinates: ArrayList<Float>?)