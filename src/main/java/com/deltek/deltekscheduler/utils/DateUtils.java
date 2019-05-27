/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deltek.deltekscheduler.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hemachandrankalaimani
 */
public class DateUtils {

    /**
     * Checks if the passed timestamp is in the future
     * @param milliSeconds
     * @return 
     */
    public static boolean checkIfMeetingStartIsInFuture(long milliSeconds){
        return milliSeconds > new Date().getTime();
    }
    
    /**
     * Check if the given time is in hours only
     *
     * @param milliSeconds
     * @return
     */
    public static boolean checkIfMilliSecondIsInHour(long milliSeconds) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(milliSeconds);
        return (date.get(Calendar.MINUTE) == 0 && date.get(Calendar.SECOND) == 0
                && date.get(Calendar.MILLISECOND) == 0);
    }

    /**
     * Check if the start and end time are valid time intervals
     *
     * @param startMilliSeconds
     * @param endMilliSeconds
     * @return
     */
    public static boolean checkIfValidTimeIntervalForMeetings(long startMilliSeconds, long endMilliSeconds) {
        return ((endMilliSeconds - startMilliSeconds) == (60 * 60 * 1000));
    }

    /**
     * Get the next nearest hour
     *
     * @return
     */
    public static long getNextNearestHour() {
        return getNextNearestHourForTheDate(new Date());
    }
    
    /**
     * GEt the next nearest hour for the passed date
     * @param dt
     * @return 
     */
    public static long getNextNearestHourForTheDate(Date dt) {
        // Get nearest hour
        Date now = dt;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        //
        // Find the next hour
        // Get the current minute
        // find it's difference to 60 and add it to the current time
        //
        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 60;
        calendar.add(Calendar.MINUTE, 60 - mod);

        //
        // This is to make sure the minutes and seconds are set to 0
        //
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY), 0, 0);

        // rounding off to nearest milliseconds
        return (calendar.getTimeInMillis() /1000) * 1000;
    }

}
