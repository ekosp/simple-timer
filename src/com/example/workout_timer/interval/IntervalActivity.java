package com.example.workout_timer.interval;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.workout_timer.FullScreenActivity;
import com.example.workout_timer.R;

/**
 * Created by mislav on 3/12/14.
 */
public class IntervalActivity extends FullScreenActivity {

    private Button start;
    private Button increaseReadyTime;
    private Button decreaseReadyTime;
    private Button increaseRoundTime;
    private Button decreaseRoundTime;
    private Button increaseRoundsNumber;
    private Button decreaseRoundsNumber;
    private Button increaseRestTime;
    private Button decreaseRestTime;

    private TextView totalTime;
    private TextView readyTimeAsString;
    private TextView roundsAsString;
    private TextView roundTimeAsString;
    private TextView restTimeAsString;

    private int readyTime;
    private int roundTime;
    private int restTime;
    private int rounds;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interval);

        setupTextViews();
        setupButtons();
    }

    private void setupTextViews() {

        readyTimeAsString = (TextView) findViewById(R.id.ready_time);
        restTimeAsString = (TextView) findViewById(R.id.rest_time);
        roundTimeAsString = (TextView) findViewById(R.id.round_time);
        roundsAsString = (TextView) findViewById(R.id.rounds_total);
        totalTime = (TextView) findViewById(R.id.total_time);
    }

    private void setupButtons() {

        start = (Button) findViewById(R.id.istart);

        increaseRoundTime = (Button) findViewById(R.id.iround);
        decreaseRoundTime = (Button) findViewById(R.id.rround);

        increaseRoundsNumber = (Button) findViewById(R.id.irounds);
        decreaseRoundsNumber = (Button) findViewById(R.id.rrounds);

        increaseReadyTime = (Button) findViewById(R.id.iready);
        decreaseReadyTime = (Button) findViewById(R.id.rready);

        increaseRestTime = (Button) findViewById(R.id.irest);
        decreaseRestTime = (Button) findViewById(R.id.rrest);

        start.setOnClickListener(new StartOnClickListener());

        IntervalSettingsOnClickListener intervalsListener = new IntervalSettingsOnClickListener();

        increaseRoundTime.setOnClickListener(intervalsListener);
        decreaseRoundTime.setOnClickListener(intervalsListener);

        increaseRoundsNumber.setOnClickListener(intervalsListener);
        decreaseRoundsNumber.setOnClickListener(intervalsListener);

        increaseReadyTime.setOnClickListener(intervalsListener);
        decreaseReadyTime.setOnClickListener(intervalsListener);

        increaseRestTime.setOnClickListener(intervalsListener);
        decreaseRestTime.setOnClickListener(intervalsListener);
    }

    private void redrawTotalTime() {
        totalTime.setText(getTimeAsString(getTotalTime()));
    }

    private void redrawRoundNumber() {
        roundsAsString.setText(String.valueOf(rounds));
    }

    private void redrawRoundTime() {
        roundTimeAsString.setText(getTimeAsString(roundTime));
    }

    private void redrawRest() {

        restTimeAsString.setText(getTimeAsString(restTime));
    }

    private void redrawReady() {

        readyTimeAsString.setText(getTimeAsString(readyTime));
    }


    private int getTotalTime() {
        return readyTime + rounds * (roundTime + restTime);
    }

    private String getTimeAsString(int seconds) {

        StringBuilder result = new StringBuilder();
        result.append(String.format("%02d", seconds / 60));
        result.append(":");
        result.append(String.format("%02d", seconds % 60));

        return result.toString();
    }


    private class StartOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent toTiming = new Intent(v.getContext(), IntervalTimingActivity.class);
            toTiming.putExtra(getString(R.string.interval_ready_time), readyTime);
            toTiming.putExtra(getString(R.string.interval_rest_time), restTime);
            toTiming.putExtra(getString(R.string.interval_round_time), roundTime);
            toTiming.putExtra(getString(R.string.interval_rounds), rounds);

            startActivity(toTiming);
        }
    }


    private class IntervalSettingsOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (v.equals(increaseReadyTime)) {
                readyTime += 1;
                redrawReady();
            }

            if (v.equals(decreaseReadyTime)) {
                readyTime = Math.max(0, readyTime - 1);
                redrawReady();
            }

            if (v.equals(increaseRestTime)) {
                restTime += 1;
                redrawRest();
            }

            if (v.equals(decreaseRestTime)) {
                restTime = Math.max(0, restTime - 1);
                redrawRest();
            }

            if (v.equals(increaseRoundTime)) {
                roundTime += 1;
                redrawRoundTime();
            }

            if (v.equals(decreaseRoundTime)) {
                roundTime = Math.max(0, roundTime - 1);
                redrawRoundTime();
            }

            if (v.equals(increaseRoundsNumber)) {
                rounds += 1;
                redrawRoundNumber();
            }

            if (v.equals(decreaseRoundsNumber)) {
                rounds = Math.max(0, rounds - 1);
                redrawRoundNumber();
            }

            redrawTotalTime();
        }
    }

}