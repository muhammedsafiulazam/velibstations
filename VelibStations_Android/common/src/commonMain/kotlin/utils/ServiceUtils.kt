package com.muhammedsafiulazam.mobile.utils

import io.ktor.client.HttpClient

/**
 * Created by Muhammed Safiul Azam on 26/08/2019.
 */
expect object ServiceUtils {
    // Http client.
    val HTTP_CLIENT: HttpClient
}