package com.example.techbook.common

object Utils {

    //function to check valid college year
    //eg: Valid college year is 2020-2021
    //eg: Valid college year is 2021-2022
    fun isValidCollegeYear(year: String): Boolean {
        val regex = Regex("^[0-9]{4}-[0-9]{4}$")
        return regex.matches(year)
    }


    //function to get date in format dd/mm/yyyy hh:mm
    fun getDateAndTime():String{
        val date = java.util.Date()
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
        return formatter.format(date)
    }
}