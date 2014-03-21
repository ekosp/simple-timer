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

    private static final long countDownInterval = 50;

    private int readyTime;
    private int roundTime;
    private int restTime;
    private int rounds;

    private int currentRound;

    private TextView mode;
    private TextView timeLeft;

    private SimpleTimer roundTimer;
    private SimpleTimer restTimer;
    private ProgressBar progressBarr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interval_timing);

        timeLeft = (TextView) findViewById(R.id.intervalLeft);
        mode = (TextView) findViewById(R.id.mode);
        progressBarr = (ProgressBar) findViewById(R.id.progressBar);

        getValues();
        startInterval();
    }

    private void startInterval() {

        restTimer = new SimpleTimer(new RestTickListener(), restTime * 1000, countDownInterval);
        SimpleTimer readyTimer = new SimpleTimer(new ReadyTickListener(), readyTime * 1000, countDownInterval);
        roundTimer = new SimpleTimer(new RoundTickListener(), roundTime * 1000, countDownInterval);


        currentRound = 1;
        mode.setText("Get ready!");
        readyTimer.start();

    }

    // TODO put keys to constants
    public void getValues() {

        Intent parent = getIntent();
        readyTime = parent.getIntExtra("ready-time", 0);
        roundTime = parent.getIntExtra("round-time", 0);
        restTime = parent.getIntExtra("rest-time", 0);
        rounds = parent.getIntExtra("rounds", 0);

        Log.i("UniqueTag", "Rounds: " + rounds);
    }

    public void playSound() {
        SoundPlayer.playNotify();
    }

    private class RestTickListener implements OnTickListener {

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft.setText(TimeFormatter.getTime(millisUntilFinished));
        }

        @Override
        public void onFinish() {

            playSound();

            ++currentRound;
            if (currentRound > rounds) {
                mode.setText("End!");
                progressBarr.setVisibility(View.GONE);
            } else {
                mode.setText("Round: " + currentRound + "!");
                roundTimer.start();

            }

        }
    }

    private class RoundTickListener implements OnTickListener {

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft.setText(TimeFormatter.getTime(millisUntilFinished));
        }

        @Override
        public void onFinish() {

            playSound();
            mode.setText("Rest!");
            restTimer.start();
        }
    }

    private class ReadyTickListener implements OnTickListener {

        @Override
        public void onTick(long millisUntilFinished) {
            timeLeft.setText(TimeFormatter.getTime(millisUntilFinished));
        }

        @Override
        public void onFinish() {


            mode.setText("Round: " + currentRound + "!");
            roundTimer.start();
            playSound();
        }
    }
}
