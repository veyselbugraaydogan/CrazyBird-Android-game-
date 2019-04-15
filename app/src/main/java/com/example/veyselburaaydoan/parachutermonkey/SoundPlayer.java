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
    final int SOUND_POOL_MAX = 3;

    private static SoundPool soundPool;
    private static int scoreSound;
    private static int overSound;
    private static int wingSound;

    private MediaPlayer mediaPlayer;

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

        initializeEffects();


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
        //soundPool.play(wingSound,1.0f,1.0f,1,0,1.0f);
        if(isSoundOn)
        mediaPlayer.start();
    }

    public void stopWingSound(){

        mediaPlayer.stop();
        //mediaPlayer.reset();
        //soundPool.stop(wingSound);

    }

    public void releaseAllSoundEffects(){

        mediaPlayer.stop();
        mediaPlayer.release();
        //soundPool.stop(wingSound);
    }

    public void initializeEffects(){
        scoreSound = soundPool.load(context,R.raw.score_sound,1);

        overSound = soundPool.load(context,R.raw.lose_sound,1);

        mediaPlayer = MediaPlayer.create(context, R.raw.wing_sound);
        mediaPlayer.setLooping(true);

        wingSound = soundPool.load(context,R.raw.wing_sound,1);
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
