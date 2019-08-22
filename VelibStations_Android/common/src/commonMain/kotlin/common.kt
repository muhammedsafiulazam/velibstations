package com.muhammedsafiulazam.mobile

expect fun getPlatform(): String

fun getPlatformName() : String {
    return "Kotlin Rocks on ${getPlatform()}"
}