package com.example.veyselburaaydoan.parachutermonkey;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Locale;

public class GameplayScene implements Scene , RewardedVideoAdListener {

    private final String TAG="Gameplay Scene";

    private int sceneNumber;
    private Rect r = new Rect();
    private RectPlayer rectPlayer;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private BulutManager bulutManager;
    private BaslatButonu baslatButonu;
    private BaslatButonu reklamButonu;

    private boolean atFirstGameOver;

    private long bilinenElapsedTime;

    private int rewardedScore =0;

    private RewardedVideoAd mRewardedVideoAd;

    private int screenWidth;

    private Paint arkaPlanRengi;

    private boolean movingPlayer = false;
    private boolean gameOver = false, ilk = true;


    private float speed;

    private Rect playerinDortgeni;

    private SettingsButton settingsButton;

    //private OrientationData orientationData;
    private int deger = 0;
    private boolean moveRight = false, moveLeft = false;

    private long startTime;

    private Rect arkaPlan;
    
    
    public GameplayScene(int sceneNumber) {

        this.sceneNumber = sceneNumber;
        screenWidth = Constants.SCREEN_WIDTH;
        init();

        playerinDortgeni = rectPlayer.getRectangle();

        //Log.v("PLAYERİN DORTGENİ :: ","lEFT : " + playerinDortgeni.left);

        gameOver = true;

        arkaPlanRengi = new Paint();
        arkaPlanRengi.setColor(Color.rgb(150, 255, 255));

        arkaPlan = new Rect(0,0,screenWidth,Constants.SCREEN_HEIGHT);

        Log.v(TAG,"Screen height :" + Constants.SCREEN_HEIGHT);
        Log.v(TAG,"Screen width :" + Constants.SCREEN_WIDTH);

        MobileAds.initialize(Constants.CURRENT_CONTEXT, "ca-app-pub-3940256099942544~3347511713");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(Constants.CURRENT_CONTEXT);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();


    }

    public void setmRewardedVideoAd(RewardedVideoAd mRewardedVideoAd){
        this.mRewardedVideoAd=mRewardedVideoAd;
        this.mRewardedVideoAd.setRewardedVideoAdListener(this);
    }

    private void init() {

        rectPlayer = new RectPlayer(new Rect(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_WIDTH / 4,
                ((Constants.SCREEN_WIDTH) / 3), (Constants.SCREEN_WIDTH) / 3), Color.rgb(255, 0, 0));

        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 4);

        obstacleManager = new ObstacleManager(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 3,
                Constants.SCREEN_HEIGHT / 13, Color.rgb(33, 75, 0));

        bulutManager = new BulutManager();
        baslatButonu = new BaslatButonu(new Point(0,0),
                15*Constants.SCREEN_WIDTH/64,3*Constants.SCREEN_WIDTH/8);
        reklamButonu = new BaslatButonu(new Point(0,0),
                15*Constants.SCREEN_WIDTH/64,3*Constants.SCREEN_WIDTH/8);
        settingsButton = new SettingsButton(Constants.SCREEN_WIDTH - 100, 100);
        startTime =  System.currentTimeMillis();
        atFirstGameOver=true;

    }

    @Override
    public void doTheMath() {

        if(((System.currentTimeMillis() - Constants.START_TIME ) - Constants.ELAPSED_TIME)>2000){
            Constants.START_TIME = System.currentTimeMillis() - Constants.ELAPSED_TIME; }

        Constants.ELAPSED_TIME = System.currentTimeMillis() - Constants.START_TIME;
        speed = (float) ((Math.sqrt(Constants.ELAPSED_TIME) / 100) + Constants.SCREEN_HEIGHT/120);
        deger = (int) (speed* 2);
        obstacleManager.doTheMath(speed);

    }

    @Override
    public void update() {
                bulutManager.update();
                if (!gameOver) {

                    if (playerPoint.x < 0)
                        playerPoint.x = 0;
                    else if (playerPoint.x > screenWidth)
                        playerPoint.x = screenWidth;

                    if (movingPlayer) {
                        if (moveRight) {
                            playerPoint.set(playerPoint.x + deger, playerPoint.y);
                        } else {
                            playerPoint.set(playerPoint.x - deger, playerPoint.y);
                        }
                    }

                    rectPlayer.update(playerPoint);
                    obstacleManager.update();

                    if (obstacleManager.playerCollide(rectPlayer.getRectangle())) {
                        gameOver = true;
                    }


                }


    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawColor(Color.rgb(150, 255, 255));
        canvas.drawRect(arkaPlan,arkaPlanRengi);
/*
        canvas.save();

        for (Rect r : obstacleManager.kirpilacakDortgenler()) {
            canvas.clipRect(r);
        }

        canvas.restore();
*/
        bulutManager.draw(canvas);
        rectPlayer.draw(canvas);
        obstacleManager.draw(canvas);


        if (gameOver) {
            if (!(ilk)) {
                /*Burasi oyuncu gercekten gameover omdugunda acilan oyur kismi*/
                gameOverTetiklemesi();
                Paint paint = new Paint();
                paint.setTextSize(Constants.BUYUK_YAZI);
                paint.setColor(Color.BLACK);
                drawCenterText(canvas, paint, "Game Over",Constants.SCREEN_HEIGHT/3,r);
                int highScore=gameOver();
                drawCenterText(canvas, paint, ""+ highScore ,50 + paint.descent() - paint.ascent(),r);
                paint.setColor(Color.RED);
                paint.setTextSize(Constants.KUCUK_YAZI);
                drawCenterText(canvas, paint, "Best Score" ,150 + paint.descent() - paint.ascent(),r);
                reklamButonu.setButtonCoordinate(new Point(Constants.SCREEN_WIDTH/4,2*Constants.SCREEN_HEIGHT/3));
                reklamButonu.draw(canvas);
                baslatButonu.setButtonCoordinate(new Point(3*Constants.SCREEN_WIDTH/4,2*Constants.SCREEN_HEIGHT/3));
                baslatButonu.draw(canvas);
            }else{
                /*Burasi oyun ilk acildiginda calisan gameover kismi*/
                rectPlayer.update(playerPoint);
                baslatButonu.setButtonCoordinate(new Point(Constants.SCREEN_WIDTH/2,2*Constants.SCREEN_HEIGHT/3));
                baslatButonu.draw(canvas);
            }
        }
        settingsButton.draw(canvas);

    }

    @Override
    public void onTerminate() {
        SceneManager.setActiveScene(sceneNumber);
    }
    private void reset() {
        init();
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void setSceneNumber(int number) {

    }



    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (settingsButton.doesCollide(new Rect((int) event.getX(), (int) event.getY() ,
                    (int) event.getX() , (int) event.getY()))) {

                SceneManager.setScene(sceneNumber,1);



            }

            if (!this.gameOver && (int) event.getY() > Constants.SCREEN_HEIGHT / 2) {
                this.movingPlayer = true;
                if ((int) event.getX() > Constants.SCREEN_WIDTH / 2) {
                    moveRight = true;
                    moveLeft = false;
                }

                if ((int) event.getX() < Constants.SCREEN_WIDTH / 2) {
                    moveRight = false;
                    moveLeft = true;
                }

            }

            if (baslatButonu.doesCollide(new Rect((int) event.getX(), (int) event.getY(),
                    (int) event.getX() , (int) event.getY() )) && gameOver ) {
                reset();
                startTime = Constants.START_TIME =System.currentTimeMillis();
                ilk = false;
                gameOver = false;
                Log.v(TAG,"Başlat butonuna basıldı");
                return;
            }

            if (reklamButonu.doesCollide(new Rect((int) event.getX(), (int) event.getY(),
                    (int) event.getX() , (int) event.getY() )) && gameOver ) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                Log.v(TAG,"reklam butonuna basıldı");
                return;
            }

        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            if (!this.gameOver && (int) event.getY() > Constants.SCREEN_HEIGHT / 2) {
                this.movingPlayer = true;
                if ((int) event.getX() > Constants.SCREEN_WIDTH / 2) {
                    moveRight = true;
                    moveLeft = false;
                }

                if ((int) event.getX() < Constants.SCREEN_WIDTH / 2) {

                    moveRight = false;
                    moveLeft = true;


                }

            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            movingPlayer = false;
            moveRight = false;
            moveLeft = false;
        }

    }

    public static void drawCenterText(Canvas canvas, Paint paint, String text,float y,Rect r) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        //float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }


    private void gameOverTetiklemesi(){
        /*Bu fonksiyon oyuncu game over oldugunda yalnizca bir sefer calismak uzere yazilmistir
        * Fonksiyon simdilik sadece reklam sonrasinda donus yapildiginda oyunun bitisini bilmeli ki
        * onAdClosed da elapsed time i guncelleyebilsin.*/
        if(atFirstGameOver){
            bilinenElapsedTime=Constants.ELAPSED_TIME;
            atFirstGameOver=false;
            Log.v(TAG,"gameOverTetiklemesi");
        }

    }

    private int gameOver(){

        /*Her draw da sharedpref yazmasi oldugu icin fps dusuyor
        * Bu fonksiyon her sefeninde cagirilmayabilir.*/

            SharedPreferences sharedPref = Constants.MAIN_ACTIVITY.getSharedPreferences(
                    Constants.MAIN_ACTIVITY.getLocalClassName(), Context.MODE_PRIVATE);
            int defaultValue = 0;
            int highScore = sharedPref.getInt("saved_high_score_key", defaultValue);

            if (obstacleManager.getScore() > highScore) {
                sharedPref = Constants.MAIN_ACTIVITY.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("saved_high_score_key", obstacleManager.getScore());
                editor.apply();
            }

        //Log.v("GAME OVER İÇİ","gameover içi");
       // Log.v("GAME OVER İÇİ","highscrore = "+highScore);
        highScore = sharedPref.getInt("saved_high_score_key", defaultValue);
        return highScore;


    }


    @Override
    public void onRewardedVideoAdLoaded() {
        //Toast.makeText(Constants.CURRENT_CONTEXT,"on loaded",Toast.LENGTH_SHORT).show();
        Log.v(TAG,"onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //Toast.makeText(Constants.CURRENT_CONTEXT,"on opened",Toast.LENGTH_SHORT).show();
        Log.v(TAG,"onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.v(TAG,"onRewardedVideoStarted");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.v(TAG,"onRewardedVideoAdClosed");
        loadRewardedVideoAd();
        Constants.ELAPSED_TIME=bilinenElapsedTime;
        ilk = false;
        gameOver = false;
        Toast.makeText(Constants.CURRENT_CONTEXT,"on video ad closed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        int score=obstacleManager.getScore();
        reset();
        obstacleManager.setScore(score);

        Toast.makeText(Constants.CURRENT_CONTEXT,"on Rewarded",Toast.LENGTH_SHORT).show();
        Log.v(TAG,"onRewarded");
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.v(TAG,"onRewardedVideoAdLeftApplication");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.v(TAG,"onRewardedVideoAdFailedToLoad");
    }

    @Override
    public void onRewardedVideoCompleted() {
        Log.v(TAG,"onRewardedVideoCompleted");
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }
}
