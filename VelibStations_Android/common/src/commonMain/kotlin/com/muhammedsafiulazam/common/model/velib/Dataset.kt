package com.muhammedsafiulazam.common.service.velib.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.ArrayClassDesc

@Serializable
data class Dataset(@SerialName("nhits")
                   val hits: Int?,

                   @SerialName("parameters")
                   val parameters: Parameters?,

                   @SerialName("records")
                   val records: ArrayList<Record>?)