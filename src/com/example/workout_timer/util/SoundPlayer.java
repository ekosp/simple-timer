package com.example.workout_timer.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import com.example.workout_timer.R;

/**
 * Created by mislav on 3/21/14.
 */
public class SoundPlayer {

    private static SoundPool soundPool;
    private static int BEEP_SOUND;

    public static final int HIGH_QUALITY = 100;
    public static final int MAX_STREAMS = 2;

    private static Context context;

    public static void initSounds(Context inContext) {

        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, HIGH_QUALITY);

        context = inContext;
        BEEP_SOUND = soundPool.load(context, R.raw.beep, 1);

    }

    public static void playNotify() {

        soundPool.play(BEEP_SOUND, 1,1,1,1,1F);
    }

}

