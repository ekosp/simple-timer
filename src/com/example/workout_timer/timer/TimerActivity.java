package com.example.workout_timer.timer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.workout_timer.FullScreenActivity;
import com.example.workout_timer.R;
import com.example.workout_timer.util.OnTickListener;
import com.example.workout_timer.util.SimpleTimer;
import com.example.workout_timer.util.SoundPlayer;
import com.example.workout_timer.util.TimeFormatter;

/**
 * TODO add stop option and continuation
 * <p/>
 * Created by mislav on 3/12/14.
 */
public class TimerActivity extends FullScreenActivity implements OnTimerSetListener {


    private static final long MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = 60;

    private static final long countDownInterval = 50;

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

        initialize();
    }


    private void initialize() {

        tickListener = new TickListener();

        if (timeLeft == null) {
            timeLeft = (TextView) findViewById(R.id.timer);
        }

        updateTimer();
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getText(R.string.end));
    }

    private int getTimeInSeconds() {

        int timeInSeconds = 0;
        timeInSeconds += hours * MINUTES_IN_HOUR * SECONDS_IN_HOUR;
        timeInSeconds += minutes * SECONDS_IN_HOUR;
        timeInSeconds += seconds;


        return timeInSeconds;
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

        timer = SimpleTimer.getTimer(tickListener, getTimeInSeconds());
        timeLeft.setText(getTimerAsString());
    }

    private String getTimerAsString() {
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    private class TickListener implements OnTickListener {

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft.setText(TimeFormatter.getTime(millisUntilFinished));

        }

        @Override
        public void onFinish() {
            timeLeft.setText(TimeFormatter.getStartTime());

            SoundPlayer.playNotify();
        }
    }
}