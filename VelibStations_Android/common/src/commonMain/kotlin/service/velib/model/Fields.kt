package com.muhammedsafiulazam.common.service.velib.model

import kotlinx.serialization.Serializable

@Serializable
data class Fields(val nbfreeedock: Int?,
                  val station_state: String?,
                  val maxbikeoverflow: Int?,
                  val creditcard: String?,
                  val station_type: String?,
                  val overflowactivation: String?,
                  val station_code: String?,
                  val overflow: String?,
                  val nbbikeoverflow: Int?,
                  val duedate: String?,
                  val densitylevel: String?,
                  val nbedock: Int?,
                  val station: String?,
                  val nbfreedock: Int?,
                  val nbbike: Int?,
                  val station_name: String?,
                  val nbdock: Int?,
                  val geo: ArrayList<Float>?,
                  val nbebike: Int?,
                  val kioskstate: String?,
                  val nbebikeoverflow: Int?)