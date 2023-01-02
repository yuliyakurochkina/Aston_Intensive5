package com.example.aston_intensive5

data class Contact(
    var name: String,
    var lastName: String,
    var number: String
) {
    override fun toString(): String {
        return String.format("%s %s \n%s", name, lastName, number)
    }
}