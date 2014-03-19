package com.example.workout_timer.util;

/**
 * Created by mislav on 3/19/14.
 */
public interface OnTickListener {
    void onTick(long millisUntilFinished);

    void onFinish();
}
