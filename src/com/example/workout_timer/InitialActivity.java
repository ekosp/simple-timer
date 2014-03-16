package com.example.workout_timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.workout_timer.interval.IntervalActivity;
import com.example.workout_timer.stopwatch.StopwatchActivity;
import com.example.workout_timer.timer.TimerActivity;

public class InitialActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
    }

    public void showTimer(View view) {

        Intent timerIntent = new Intent(this, TimerActivity.class);
        startActivity(timerIntent);
    }

    public void showInterval(View view) {

        Intent intervalIntent = new Intent(this, IntervalActivity.class);
        startActivity(intervalIntent);
    }

    public void showStopwatch(View view) {

        Intent stopwatchIntent = new Intent(this, StopwatchActivity.class);
        startActivity(stopwatchIntent);
    }
}
