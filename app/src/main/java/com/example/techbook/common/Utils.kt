package com.example.techbook.common

object Utils {

    //function to check valid college year
    //eg: Valid college year is 2020-2021
    //eg: Valid college year is 2021-2022
    fun isValidCollegeYear(year: String): Boolean {
        val regex = Regex("^[0-9]{4}-[0-9]{4}$")
        return regex.matches(year)
    }
}