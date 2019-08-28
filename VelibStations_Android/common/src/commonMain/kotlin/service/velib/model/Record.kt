package com.muhammedsafiulazam.common.service.velib.model

import kotlinx.serialization.Serializable

@Serializable
data class Record(val datasetid: String?,
                  val recordid: String?,
                  val fields: Fields?,
                  val geometry: Geometry?,
                  val record_timestamp: String?)