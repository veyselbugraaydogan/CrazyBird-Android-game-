package com.example.veyselburaaydoan.parachutermonkey;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaExtractor;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;

    private static SoundPool soundPool;
    private static int scoreSound;
    private static int overSound;

    private static MediaPlayer mediaPlayer;

    private Context context;

    private boolean isSoundOn;

    private SharedPreferences sharedPref;


    public SoundPlayer(Context context){

        this.context = context;


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }
        else {

            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);


        }

        scoreSound = soundPool.load(context,R.raw.score_sound,1);

        overSound = soundPool.load(context,R.raw.lose_sound,1);

        sharedPref = Constants.CURRENT_CONTEXT.getSharedPreferences(Constants.isSoundOnSharedPref,Context.MODE_PRIVATE);

        isSoundOn = sharedPref.getBoolean(Constants.isSoundOnSharedPref, true);



    }

    public void playScoreSound(){

        if(isSoundOn)
        soundPool.play(scoreSound,1.0f,1.0f,1,0,1.0f);
    }

    public void playOverSound(){
        if(isSoundOn)
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }

    public void playWingSound(){

            if (mediaPlayer == null) {

                mediaPlayer = MediaPlayer.create(context, R.raw.wing_sound);
                mediaPlayer.setLooping(true);

            }
        if(isSoundOn) {
            mediaPlayer.start();
        }
    }

    public void stopWingSound(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }


    public void setSound(boolean soundStatus){
        isSoundOn = soundStatus;
        if(!isSoundOn)
            stopWingSound();
    }

    public MediaPlayer getMediaPlayer(){
        return  mediaPlayer;
    }

    public boolean isSoundOn(){
        return isSoundOn;
    }

}
