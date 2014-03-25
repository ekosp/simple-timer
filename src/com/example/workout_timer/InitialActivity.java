package com.example.workout_timer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.workout_timer.interval.IntervalActivity;
import com.example.workout_timer.ladder.LadderActivity;
import com.example.workout_timer.stopwatch.StopwatchActivity;
import com.example.workout_timer.timer.TimerActivity;
import com.example.workout_timer.util.SoundPlayer;

public class InitialActivity extends FullScreenActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        SoundPlayer.initSounds(getApplicationContext());
    }

    /**
     * Callback defined in layout.
     *
     * @param ignored
     */
    public void showTimer(View ignored) {

        Intent timerIntent = new Intent(this, TimerActivity.class);
        startActivity(timerIntent);
    }

    /**
     * Callback defined in layout.
     *
     * @param ignored
     */
    public void showInterval(View ignored) {

        Intent intervalIntent = new Intent(this, IntervalActivity.class);
        startActivity(intervalIntent);
    }

    /**
     * Callback defined in layout.
     *
     * @param ignored
     */
    public void showStopwatch(View ignored) {

        Intent stopwatchIntent = new Intent(this, StopwatchActivity.class);
        startActivity(stopwatchIntent);
    }

    /**
     * Callback defined in layout.
     *
     * @param ignored
     */
    public void showLadder(View ignored) {

        Intent ladderIntent = new Intent(this, LadderActivity.class);
        startActivity(ladderIntent);
    }
}
