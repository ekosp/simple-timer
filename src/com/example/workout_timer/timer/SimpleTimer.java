package com.example.workout_timer.timer;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.example.workout_timer.util.TimeFormatter;

/**
 * Created by mislav on 3/13/14.
 */
public class SimpleTimer extends CountDownTimer {

    private TextView timeLeft;
    private Ringtone r;

    public SimpleTimer(TextView timeLeft, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        r = RingtoneManager.getRingtone(timeLeft.getContext(), notification);

        this.timeLeft = timeLeft;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        timeLeft.setText(TimeFormatter.getTime(millisUntilFinished));
    }

    @Override
    public void onFinish() {

        timeLeft.setText(TimeFormatter.getStartTime());
        r.play();
    }
}
