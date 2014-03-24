package com.example.workout_timer.util;

/**
 * Created by mislav on 3/14/14.
 */
public class TimeFormatter {

    private static final String TWO_DIGIT_FORMAT = "%02d";
    private static final String ONE_DIGIT_FORMAT = "%01d";

    private static final long MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = 60;
    private static final long MILLIS_IN_SECONDS = 1000;


    public static final String COLON = ":";
    public static final int START_TIME = 0;

    public static String getTimeInMillis(long millis) {

        long hours = getHours(millis);
        long minutes = getMinutes(millis);
        long seconds = getSeconds(millis);
        long tenthOf = getTenthOfASecond(millis);

        StringBuilder result = new StringBuilder();
        result.append(String.format(TWO_DIGIT_FORMAT, hours));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, minutes));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, seconds));
        result.append(COLON);
        result.append(String.format(ONE_DIGIT_FORMAT, tenthOf));

        return result.toString();
    }

    public static String getTimeInSeconds(int totalSeconds) {

        long millis = totalSeconds * MILLIS_IN_SECONDS;

        long hours = getHours(millis);
        long minutes = getMinutes(millis);
        long seconds = getSeconds(millis);


        StringBuilder result = new StringBuilder();
        result.append(String.format(TWO_DIGIT_FORMAT, hours));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, minutes));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, seconds));

        return result.toString();
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

    public static String getStartTime() {
        return getTimeInMillis(START_TIME);
    }


}
