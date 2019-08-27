package com.muhammedsafiulazam.mobile.service.model

import kotlinx.serialization.Serializable

@Serializable
data class Error(val code: String?, val message: String?)