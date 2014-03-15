package com.example.workout_timer.timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.workout_timer.R;

/**
 * TODO add stop option and continuation
 * <p/>
 * Created by mislav on 3/12/14.
 */
public class TimerActivity extends Activity implements OnTimerSetListener {


    private static final long MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = 60;
    private static final long MILLIS_IN_SECONDS = 1000;
    private static final long countDownInterval = 50;

    public static final String HOURS = "hours";
    public static final String MINUTES = "minutes";
    public static final String SECONDS = "seconds";
    public static final String SET_TIMER = "Set timer";

    private SimpleTimer timer;
    private long hours;
    private long minutes;
    private long seconds;

    private TextView timeLeft;
    private AlertDialog alertDialog;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        initialize();
    }

    private void initialize() {

        timeLeft = (TextView) findViewById(R.id.timer);


        timer = new SimpleTimer(timeLeft, getTimeInMillis(), countDownInterval);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(SET_TIMER);
    }

    private long getTimeInMillis() {

        long timeInMillis = 0;
        timeInMillis += hours * MINUTES_IN_HOUR * SECONDS_IN_HOUR * MILLIS_IN_SECONDS;
        timeInMillis += minutes * SECONDS_IN_HOUR * MILLIS_IN_SECONDS;
        timeInMillis += seconds * MILLIS_IN_SECONDS;


        return timeInMillis;
    }


    public void start(View view) {

        timer.start();
    }

    public void setTimer(View view) {

        CustomizedTimePicker timePicker = new CustomizedTimePicker(this, this, true);
        timePicker.setTimerPicker((int) hours, (int) minutes, (int) seconds);
        timePicker.show();
    }

    public void restart(View view) {
        timer.cancel();
        timeLeft.setText(getTimerAsString());
    }

    @Override
    public void onTimerSet(int hours, int minutes, int seconds) {

        updateTimer(hours, minutes, seconds);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(HOURS, hours);
        outState.putLong(MINUTES, minutes);
        outState.putLong(SECONDS, seconds);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        long hours = savedInstanceState.getLong(HOURS);
        long minutes = savedInstanceState.getLong(MINUTES);
        long seconds = savedInstanceState.getLong(SECONDS);

        updateTimer(hours, minutes, seconds);

    }

    private void updateTimer(long hours, long minutes, long seconds) {

        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;

        timer = new SimpleTimer(timeLeft, getTimeInMillis(), countDownInterval);
        timeLeft.setText(getTimerAsString());
    }

    private String getTimerAsString() {
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }
}