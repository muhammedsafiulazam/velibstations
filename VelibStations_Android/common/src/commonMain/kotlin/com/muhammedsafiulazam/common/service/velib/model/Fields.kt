package com.muhammedsafiulazam.common.service.velib.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fields(@SerialName("station_name")
                  val name: String?,

                  @SerialName("station_code")
                  val code: String?,

                  @SerialName("station_state")
                  val state: String?,

                  @SerialName("station_type")
                  val type: String?,

                  @SerialName("duedate")
                  val dueDate: String?,

                  @SerialName("geo")
                  val geolocation: ArrayList<Float>?,

                  @SerialName("kioskstate")
                  val kioskState: String?,

                  @SerialName("creditcard")
                  val creditCard: String?,

                  @SerialName("overflowactivation")
                  val overflowActivation: String?,

                  @SerialName("maxbikeoverflow")
                  val maxBikeOverflow: Int?,

                  @SerialName("nbedock")
                  val nbEDock: Int?,

                  @SerialName("nbfreeedock")
                  val nbFreeEDock: Int?,

                  @SerialName("nbdock")
                  val nbDock: Int?,

                  @SerialName("nbfreedock")
                  val nbFreeDock: Int?,

                  @SerialName("nbebike")
                  val nbEBike: Int?,

                  @SerialName("nbbike")
                  val nbBike: Int?,

                  @SerialName("nbbikeoverflow")
                  val nbBikeOverflow: Int?,

                  @SerialName("nbebikeoverflow")
                  val nbEBikeOverflow: Int?,

                  @SerialName("overflow")
                  val overflow: String?,

                  @SerialName("densitylevel")
                  val densityLevel: String?)