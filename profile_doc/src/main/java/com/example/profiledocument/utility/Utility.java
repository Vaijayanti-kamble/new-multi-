package com.example.profiledocument.utility;

import java.sql.Timestamp;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;



public class Utility {



    Utility() {

        throw new UnsupportedOperationException("Utility class cannot be instantiated");

    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");



    public static String getTime(LocalDateTime localDateTime){

    if (localDateTime == null) {
        localDateTime = LocalDateTime.now(); // Default to current time if null
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
}

    public static Timestamp getTimeStamp(String dateTime)

    {

        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        return Timestamp.valueOf(localDateTime);

    }

}

