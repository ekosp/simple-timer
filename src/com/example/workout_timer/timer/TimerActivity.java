package com.example.workout_timer.timer;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.workout_timer.FullScreenActivity;
import com.example.workout_timer.R;
import com.example.workout_timer.util.*;

/**
 * TODO add stop option and continuation
 * <p/>
 * Created by mislav on 3/12/14.
 */
public class TimerActivity extends FullScreenActivity implements OnTimerSetListener {

    private SimpleTimer timer;

    private int seconds;

    private TextView timeLeft;
    private AlertDialog alertDialog;
    private static TickListener tickListener;

    private SharedPreferences preferences;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        preferences = getPreferences(MODE_PRIVATE);
        initialize();
    }


    @Override
    protected void onResume() {
        super.onResume();

        restoreState();
    }


    @Override
    protected void onPause() {
        super.onPause();

        timer.cancel();
        saveState();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        timer.cancel();
    }


    private void saveState() {

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.timer_value), seconds);
        editor.commit();
    }


    private void restoreState() {

        seconds = preferences.getInt(getString(R.string.timer_value),seconds);
        updateTimer();
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


    public void start(View view) {

        timer.start();
    }

    // TODO get better casting handling
    public void setTimer(View view) {

        CustomizedTimePicker timePicker = new CustomizedTimePicker(this, this, true);
        timePicker.setTimerPicker(
                (int) TimeConverter.getHours(seconds), (int) TimeConverter.getMinutes(seconds), (int) TimeConverter.getSeconds(seconds));
        timePicker.show();
    }

    public void restart(View view) {
        timer.cancel();
        timeLeft.setText(TimeFormatter.getTimeFromSeconds(seconds));
    }

    @Override
    public void onTimerSet(int hours, int minutes, int seconds) {


        this.seconds = TimeConverter.toSeconds(hours, minutes, seconds);
        updateTimer();
    }

    private void updateTimer() {

        timer = SimpleTimer.getTimerFromSeconds(tickListener, seconds);
        timeLeft.setText(TimeFormatter.getTimeFromSeconds(seconds));
    }


    private class TickListener implements OnTickListener {

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft.setText(TimeFormatter.getTimeFromMillis(millisUntilFinished));

        }

        @Override
        public void onFinish() {
            timeLeft.setText(TimeFormatter.getStartTime());

            SoundPlayer.playNotify();
        }
    }


}