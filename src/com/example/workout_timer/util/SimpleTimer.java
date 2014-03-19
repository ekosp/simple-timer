package com.example.workout_timer.util;

import android.os.CountDownTimer;

/**
 * Created by mislav on 3/13/14.
 */
public class SimpleTimer extends CountDownTimer {


    private OnTickListener listener;

    public SimpleTimer(OnTickListener listener, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.listener = listener;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        listener.onTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {

        listener.onFinish();
    }
}
