package com.example.workout_timer.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.workout_timer.R;

/**
 * Created by mislav on 3/12/14.
 */
public class StopwatchActivity extends Activity {

    private static final long MINUTES_IN_HOUR = 60;
    private static final long SECONDS_IN_HOUR = 60;
    private static final long MILLIS_IN_SECONDS = 1000;


    private long lastMillis;
    private long totalMilliseconds;

    private Handler stopwatchHandler;
    private Runnable timerThread;

    private StartClickListener startClickListener;

    private TextView timeSpent;
    private Button start;
    private Button reset;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch);

        stopwatchHandler = new Handler();
        timerThread = new StopwatchRunnable();
        startClickListener = new StartClickListener();


        timeSpent = (TextView) findViewById(R.id.stopwatch);
        start = (Button) findViewById(R.id.startStopwatch);
        reset = (Button) findViewById(R.id.resetStopwatch);


        start.setOnClickListener(startClickListener);
        reset.setOnClickListener(new ResetClickListener());

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putLong("start", lastMillis);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        lastMillis = savedInstanceState.getLong("start");
    }

    private class StartClickListener implements View.OnClickListener {

        private boolean started = false;

        @Override
        public void onClick(View view) {

            if (started) {
                stop();
                return;
            }

            if (!started) {
                start();
            }

        }

        private void start() {

            start.setText("Stop");
            started = true;


            lastMillis = SystemClock.uptimeMillis();

            stopwatchHandler.postDelayed(timerThread, 0);
        }

        private void stop() {
            start.setText("Start");
            started = false;
            stopwatchHandler.removeCallbacks(timerThread);

        }
    }

    private class ResetClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            startClickListener.stop();
            lastMillis = 0;
            totalMilliseconds = 0;
            timeSpent.setText("00:00:00:0");

        }
    }

    private class StopwatchRunnable implements Runnable {

        @Override
        public void run() {


            long currentMillis = SystemClock.uptimeMillis();
            totalMilliseconds += currentMillis - lastMillis;
            lastMillis = currentMillis;
            long hours = getHours(totalMilliseconds);
            long minutes = getMinutes(totalMilliseconds);
            long seconds = getSeconds(totalMilliseconds);
            long tenthOf = getTenthOfASecond(totalMilliseconds);

            timeSpent.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%01d", tenthOf));
            stopwatchHandler.postDelayed(timerThread, 0);
        }

        private long getTenthOfASecond(long millis) {
            return (millis % MILLIS_IN_SECONDS) / 100;
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
    }
}