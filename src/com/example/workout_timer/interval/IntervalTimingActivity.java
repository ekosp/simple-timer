package com.example.workout_timer.interval;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.workout_timer.FullScreenActivity;
import com.example.workout_timer.R;
import com.example.workout_timer.util.OnTickListener;
import com.example.workout_timer.util.SimpleTimer;
import com.example.workout_timer.util.SoundPlayer;
import com.example.workout_timer.util.TimeFormatter;

/**
 * Created by mislav on 3/17/14.
 */
public class IntervalTimingActivity extends FullScreenActivity {


    private boolean isCanceled;

    private static final int FIRST_ROUND = 1;

    private int readyTime;
    private int roundTime;
    private int restTime;
    private int rounds;

    private int currentRound;

    private TextView mode;
    private TextView timeLeft;

    private SimpleTimer readyTimer;
    private SimpleTimer roundTimer;
    private SimpleTimer restTimer;
    private ProgressBar progressBarr;

    private OnTickListener restTickListener;
    private OnTickListener roundTickListener;
    private OnTickListener readyTickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interval_timing);

        timeLeft = (TextView) findViewById(R.id.intervalLeft);
        mode = (TextView) findViewById(R.id.mode);
        progressBarr = (ProgressBar) findViewById(R.id.progressBar);

        readyTickListener = new ReadyTickListener();
        restTickListener = new RestTickListener();
        roundTickListener = new RoundTickListener();


        getValues();
        startInterval();

    }

    private void startInterval() {

        restTimer = SimpleTimer.getTimerFromSeconds(restTickListener, restTime);

        readyTimer = SimpleTimer.getTimerFromSeconds(readyTickListener, readyTime);
        roundTimer = SimpleTimer.getTimerFromSeconds(roundTickListener, roundTime);

        currentRound = FIRST_ROUND;
        mode.setText(getText(R.string.get_ready));
        readyTimer.start();

    }

    // TODO magic numbers?
    public void getValues() {

        isCanceled = false;

        Intent parent = getIntent();
        readyTime = parent.getIntExtra(getString(R.string.interval_ready_time), 0);
        roundTime = parent.getIntExtra(getString(R.string.interval_round_time), 0);
        restTime = parent.getIntExtra(getString(R.string.interval_rest_time), 0);
        rounds = parent.getIntExtra(getString(R.string.interval_rounds), 0);

    }

    public void playSound() {
        SoundPlayer.playNotify();
    }

    public void loopSound() {
        SoundPlayer.loopNotify();
    }

    private String getRoundText(int round) {

        StringBuilder result = new StringBuilder();
        result.append("Round: ");
        result.append(round);
        result.append("!");
        return result.toString();
    }


    private abstract class BaseTickListener implements OnTickListener {

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft.setText(TimeFormatter.getTimeFromMillis(millisUntilFinished));
        }

        public void baseOnFinish(SimpleTimer timer, String text) {

            if (isCanceled)
                return;

            mode.setText(text);
            timer.start();
            startSoundNotification();
        }

        public abstract void startSoundNotification();
    }


    private class RestTickListener extends BaseTickListener {


        @Override
        public void onFinish() {


            playSound();

            ++currentRound;
            if (currentRound > rounds) {

                mode.setText(getString(R.string.end));
                progressBarr.setVisibility(View.GONE);
            } else {

                baseOnFinish(roundTimer, getRoundText(currentRound));
            }

        }

        @Override
        public void startSoundNotification() {
            loopSound();
        }
    }


    private class RoundTickListener extends BaseTickListener {


        @Override
        public void onFinish() {


            baseOnFinish(restTimer, getString(R.string.rest));
        }

        @Override
        public void startSoundNotification() {
            playSound();
        }
    }

    private class ReadyTickListener extends BaseTickListener {


        @Override
        public void onFinish() {

            baseOnFinish(roundTimer, getRoundText(currentRound));

        }

        @Override
        public void startSoundNotification() {
            loopSound();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        cancelTimers();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelTimers();
    }

    private void cancelTimers() {

        isCanceled = true;

        readyTimer.cancel();
        restTimer.cancel();
        readyTimer.cancel();
        SoundPlayer.stopSound();
    }
}
