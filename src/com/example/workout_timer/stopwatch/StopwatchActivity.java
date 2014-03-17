package com.example.workout_timer.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.workout_timer.FullScreenActivity;
import com.example.workout_timer.R;
import com.example.workout_timer.util.TimeFormatter;

/**
 * Created by mislav on 3/12/14.
 */
public class StopwatchActivity extends FullScreenActivity {

    private static final String TIME_SPENT_KEY = "time-spent";

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
        outState.putLong(TIME_SPENT_KEY, totalMilliseconds);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        totalMilliseconds = savedInstanceState.getLong(TIME_SPENT_KEY);
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

            stopwatchHandler.post(timerThread);
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
            timeSpent.setText(TimeFormatter.getTime(totalMilliseconds));

        }
    }

    private class StopwatchRunnable implements Runnable {

        @Override
        public void run() {

            long currentMillis = SystemClock.uptimeMillis();
            totalMilliseconds += currentMillis - lastMillis;
            lastMillis = currentMillis;


            timeSpent.setText(TimeFormatter.getTime(totalMilliseconds));
            stopwatchHandler.post(timerThread);
        }
    }
}