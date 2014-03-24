package com.example.workout_timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.workout_timer.interval.IntervalActivity;
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

    public void showTimer(View ignored) {

        Intent timerIntent = new Intent(this, TimerActivity.class);
        startActivity(timerIntent);
    }

    public void showInterval(View ignored) {

        Intent intervalIntent = new Intent(this, IntervalActivity.class);
        startActivity(intervalIntent);
    }

    public void showStopwatch(View ignored) {

        Intent stopwatchIntent = new Intent(this, StopwatchActivity.class);
        startActivity(stopwatchIntent);
    }
}
