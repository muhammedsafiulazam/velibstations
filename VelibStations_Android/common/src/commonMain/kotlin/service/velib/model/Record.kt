package com.muhammedsafiulazam.common.service.velib.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Record(@SerialName("recordid")
                   val id: String?,

                  @SerialName("record_timestamp")
                   val timestamp: String?,

                  @SerialName("fields")
                   val fields: Fields?)