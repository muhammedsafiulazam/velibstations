package com.muhammedsafiulazam.mobile.model

data class Person (val name: String, val gender: Gender) {

    enum class Gender {
        Male,
        Female
    }

    fun info() {
        println("Person: " + name + " - " + gender)
    }
}