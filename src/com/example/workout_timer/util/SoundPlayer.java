package com.example.workout_timer.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
import com.example.workout_timer.R;

/**
 * Created by mislav on 3/21/14.
 */
public class SoundPlayer {

    private static SoundPool soundPool;
    private static int BEEP_SOUND;

    public static final int HIGH_QUALITY = 100;
    public static final int MAX_STREAMS = 1;

    private static Context context;

    private static int playingSound;

    public static void initSounds(Context inContext) {

        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, HIGH_QUALITY);

        context = inContext;
        BEEP_SOUND = soundPool.load(context, R.raw.beep, 1);

    }

    public static void playNotify() {


        soundPool.stop(BEEP_SOUND);
        playingSound = soundPool.play(BEEP_SOUND, 1,1,1,1,1F);
    }


    public static void loopNotify() {

        soundPool.stop(BEEP_SOUND);
        playingSound = soundPool.play(BEEP_SOUND, 1,1,1,-1,1F);
    }

    public static void stopSound(){

        soundPool.stop(playingSound);
    }
}

