package com.example.rawgdb.util

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ChangeDateFormat {

   companion object{
       fun dateToYear(date: String): String {
           val input = SimpleDateFormat("yyyy-MM-dd")
           val output = SimpleDateFormat("yyyy")
           try {
               val getAbbreviate = input.parse(date)    // parse input
               return output.format(getAbbreviate)    // format output
           } catch (e: ParseException) {
               e.printStackTrace()
           }

           return ""
       }
   }
}