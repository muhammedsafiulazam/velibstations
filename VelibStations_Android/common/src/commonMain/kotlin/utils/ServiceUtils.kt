package com.muhammedsafiulazam.mobile.utils

import io.ktor.client.HttpClient

expect object ServiceUtils {
    val HTTP_CLIENT: HttpClient
}