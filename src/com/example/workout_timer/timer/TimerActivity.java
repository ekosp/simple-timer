package com.example.workout_timer.timer;

import android.app.AlertDialog;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.workout_timer.FullScreenActivity;
import com.example.workout_timer.R;
import com.example.workout_timer.util.OnTickListener;
import com.example.workout_timer.util.SimpleTimer;
import com.example.workout_timer.util.TimeFormatter;

/**
 * TODO add stop option and continuation
 * <p/>
 * Created by mislav on 3/12/14.
 */
public class TimerActivity extends FullScreenActivity implements OnTimerSetListener {


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
    private static TickListener tickListener;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.timer);
        Log.i("UniqueTag", "OnCreateInvoked");

        initialize();
    }


    private void initialize() {

        tickListener = new TickListener();

        if (timeLeft == null) {
            timeLeft = (TextView) findViewById(R.id.timer);
        }

        updateTimer();
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


        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        updateTimer();
    }

    private void updateTimer() {

        timer = new SimpleTimer(tickListener, getTimeInMillis(), countDownInterval);
        timeLeft.setText(getTimerAsString());
    }

    private String getTimerAsString() {
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    private class TickListener implements OnTickListener {


        private Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        private Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft.setText(TimeFormatter.getTime(millisUntilFinished));

        }

        @Override
        public void onFinish() {
            timeLeft.setText(TimeFormatter.getStartTime());
            r.play();
        }
    }
}