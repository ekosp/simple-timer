package com.example.workout_timer.ladder;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.workout_timer.FullScreenActivity;
import com.example.workout_timer.R;
import com.example.workout_timer.util.OnTickListener;
import com.example.workout_timer.util.SimpleTimer;
import com.example.workout_timer.util.SoundPlayer;
import com.example.workout_timer.util.TimeFormatter;

/**
 * Created by mislav on 3/12/14.
 */
public class LadderActivity extends FullScreenActivity {

    private enum State {
        STOPWATCH, COUNTDOWN;
    }

    ;

    private State state;

    private long lastMillis;
    private long totalMilliseconds;

    private Handler stopwatchHandler;
    private Runnable timerThread;

    private StartClickListener startClickListener;
    private OnTickListener onTickListener;
    private SimpleTimer timer;

    private TextView timeView;
    private Button start;
    private Button reset;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ladder);

        stopwatchHandler = new Handler();
        timerThread = new StopwatchRunnable();
        startClickListener = new StartClickListener();
        onTickListener = new LadderTickListener();


        timeView = (TextView) findViewById(R.id.ladderTime);
        start = (Button) findViewById(R.id.startLadder);
        reset = (Button) findViewById(R.id.resetLadder);


        start.setOnClickListener(startClickListener);
        reset.setOnClickListener(new ResetClickListener());

    }

    private class StartClickListener implements View.OnClickListener {

        private boolean started = false;

        @Override
        public void onClick(View view) {

            if (started) {
                countdown();
                return;
            }

            if (!started) {

                start();
                return;
            }

        }

        private void start() {

            state = State.STOPWATCH;
            start.setText(getText(R.string.countdown));
            started = true;

            lastMillis = SystemClock.uptimeMillis();

            stopwatchHandler.post(timerThread);
        }

        private void countdown() {


            start.setText(getText(R.string.stop));
            started = false;
            stopwatchHandler.removeCallbacks(timerThread);
            timer = SimpleTimer.getTimerFromMillis(onTickListener, totalMilliseconds);
            timer.start();
            state = State.COUNTDOWN;
        }
    }

    private class ResetClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {


            stopwatchHandler.removeCallbacks(timerThread);
            cancelLadder();
            lastMillis = 0;
            totalMilliseconds = 0;
            timeView.setText(TimeFormatter.getTimeFromMillis(totalMilliseconds));

        }
    }

    private class StopwatchRunnable implements Runnable {

        @Override
        public void run() {

            long currentMillis = SystemClock.uptimeMillis();
            totalMilliseconds += currentMillis - lastMillis;
            lastMillis = currentMillis;


            timeView.setText(TimeFormatter.getTimeFromMillis(totalMilliseconds));
            stopwatchHandler.post(timerThread);
        }
    }

    private class LadderTickListener implements OnTickListener {
        @Override
        public void onTick(long millisUntilFinished) {
            timeView.setText(TimeFormatter.getTimeFromMillis(millisUntilFinished));
        }

        @Override
        public void onFinish() {
            timeView.setText(TimeFormatter.getStartTime());

            SoundPlayer.playNotify();
            state = State.STOPWATCH;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelLadder();
    }


    @Override
    protected void onPause() {
        super.onPause();

        cancelLadder();
    }


    private void cancelLadder() {
        if (state == State.COUNTDOWN) {
            timer.cancel();
        }
    }
}