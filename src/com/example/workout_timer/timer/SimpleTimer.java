package com.example.workout_timer.timer;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by mislav on 3/13/14.
 */
public class SimpleTimer extends CountDownTimer {

    private static final long MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = 60;
    private static final long MILLIS_IN_SECONDS = 1000;

    private TextView timeLeft;
    private Ringtone r;

    public SimpleTimer(TextView timeLeft, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        r = RingtoneManager.getRingtone(timeLeft.getContext(), notification);

        this.timeLeft = timeLeft;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        long hours = getHours(millisUntilFinished);
        long minutes = getMinutes(millisUntilFinished);
        long seconds = getSeconds(millisUntilFinished);

        Log.println(Log.ERROR, "Debug", "onTick: " + millisUntilFinished);


        timeLeft.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    private long getSeconds(long millisUntilFinished) {
        return (millisUntilFinished / MILLIS_IN_SECONDS) % SECONDS_IN_HOUR;
    }

    private long getMinutes(long millisUntilFinished) {
        return millisUntilFinished / (SECONDS_IN_HOUR * MILLIS_IN_SECONDS) % MINUTES_IN_HOUR;
    }

    private long getHours(long millisUntilFinished) {
        return millisUntilFinished / (MINUTES_IN_HOUR * SECONDS_IN_HOUR * MILLIS_IN_SECONDS);
    }

    @Override
    public void onFinish() {

        timeLeft.setText("00:00:00");
        Log.println(Log.ERROR, "Debug", "onFinish");
        r.play();
    }
}
