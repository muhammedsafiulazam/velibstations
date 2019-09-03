package com.muhammedsafiulazam.common.service.velib.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Parameters(@SerialName("dataset")
                      val id: String?,

                      @SerialName("start")
                      val index: Int?,

                      @SerialName("rows")
                      val count: Int?,

                      @SerialName("timezone")
                      val timezone: String?)