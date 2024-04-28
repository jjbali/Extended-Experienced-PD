package com.shatteredpixel.shatteredpixeldungeon.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Rohit Agarwal
 * @category Date and Time
 * @problem How to get day name of given date?
 *
 */
public class RTX {

    //public static void main(String[] args) {
/*
        Scanner input = null;
        try {
            input = new Scanner(System.in);
            System.out.println("Enter date in dd/MM/yyyy format :");
            String date = input.next();
            // If Date is valid, converting String to Date
            Date mydate = getValidDate(date);
            if (mydate != null) {
                // Creating Calendar class instance.
                Calendar calendar = Calendar.getInstance();
                // Converting Date to Calendar.
                calendar.setTime(mydate);
                int dayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
                String dayName = getDayName(dayofWeek);
                System.out.println("Day : " + dayName);
            } else {
                System.out.println("Date is invalid.");
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }

 */

    //}

    public static Date getValidDate(String date) {

        Date mydate = null;
        if (isValidDateFormat(date)) {
            /*
             * d -> Day of month
             * M -> Month of year
             * y -> Year
             */
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            /*
             * By default setLenient() is true. We should make it false for
             * strict date validations.
             *
             * If setLenient() is true - It accepts all dates. If setLenient()
             * is false - It accepts only valid dates.
             */
            dateFormat.setLenient(false);
            try {
                mydate = dateFormat.parse(date);
            } catch (ParseException e) {
                mydate = null;
            }
        }
        return mydate;
    }

    public static String getDayName(int dayofWeek) {

        String dayName = null;
        switch (dayofWeek) {
            case 1:
                dayName = "Sunday";
                break;
            case 2:
                dayName = "Monday";
                break;
            case 3:
                dayName = "Tuesday";
                break;
            case 4:
                dayName = "Wednesday";
                break;
            case 5:
                dayName = "Thursday";
                break;
            case 6:
                dayName = "Friday";
                break;
            case 7:
                dayName = "Saturday";
                break;
        }
        return dayName;
    }

    public static boolean isValidDateFormat(String date) {

        /*
         * Regular Expression that matches String with format dd/MM/yyyy.
         * dd -> 01-31
         * MM -> 01-12
         * yyyy -> 4 digit number
         */
        String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
        boolean result = false;
        if (date.matches(pattern)) {
            result = true;
        }
        return result;
    }

}