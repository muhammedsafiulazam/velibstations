package com.muhammedsafiulazam.common.service.velib.model

import kotlinx.serialization.Serializable

@Serializable
data class Parameters(val dataset: String?,
                      val timezone: String?,
                      val rows: Int?,
                      val start: Int?,
                      val format: String?,
                      val facet: ArrayList<String>?)