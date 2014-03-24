package com.example.workout_timer.util;

import android.os.CountDownTimer;

/**
 * Created by mislav on 3/13/14.
 */
public class SimpleTimer extends CountDownTimer {

    private static final int DEFAULT_COUNTDOWN_INTERVAL = 10;
    private static final long MILLIS_IN_SECONDS = 1000;

    private OnTickListener listener;

    private SimpleTimer(OnTickListener listener, long millisInFuture, long countDownInterval) {
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

    public static SimpleTimer getTimer(OnTickListener listener,int seconds){

        return getTimer(listener, seconds, DEFAULT_COUNTDOWN_INTERVAL);
    }

    public static SimpleTimer getTimer(OnTickListener listener,int seconds, long countDownInterval){

        return new SimpleTimer(listener,seconds * MILLIS_IN_SECONDS,countDownInterval);
    }
}

