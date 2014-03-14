package com.example.workout_timer.util;

/**
 * Created by mislav on 3/14/14.
 */
public class TimeFormatter {


    private static final long MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = 60;
    private static final long MILLIS_IN_SECONDS = 1000;

    public static String getTime(long millis) {

        long hours = getHours(millis);
        long minutes = getMinutes(millis);
        long seconds = getSeconds(millis);
        long tenthOf = getTenthOfASecond(millis);

        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%01d", tenthOf);
    }

    private static long getTenthOfASecond(long millis) {
        return (millis % MILLIS_IN_SECONDS) / 100;
    }

    private static long getSeconds(long millisUntilFinished) {
        return (millisUntilFinished / MILLIS_IN_SECONDS) % SECONDS_IN_HOUR;
    }

    private static long getMinutes(long millisUntilFinished) {
        return millisUntilFinished / (SECONDS_IN_HOUR * MILLIS_IN_SECONDS) % MINUTES_IN_HOUR;
    }

    private static long getHours(long millisUntilFinished) {
        return millisUntilFinished / (MINUTES_IN_HOUR * SECONDS_IN_HOUR * MILLIS_IN_SECONDS);
    }

}
