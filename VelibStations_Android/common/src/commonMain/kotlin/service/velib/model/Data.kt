package com.muhammedsafiulazam.mobile.service.velib.model

import kotlinx.serialization.Serializable

@Serializable
data class Data(val nhits: Int?,
                val parameters: Parameters?,
                val records: ArrayList<Record>?)