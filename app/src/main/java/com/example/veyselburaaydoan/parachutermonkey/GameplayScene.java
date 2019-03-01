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

import java.util.ArrayList;

public class GameplayScene implements Scene {


    private int sceneNumber;
    private Rect r = new Rect();
    private RectPlayer rectPlayer;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private BulutManager bulutManager;
    private BaslatButonu baslatButonu;

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


    }

    private void init() {

        rectPlayer = new RectPlayer(new Rect(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_WIDTH / 4,
                ((Constants.SCREEN_WIDTH) / 3), (Constants.SCREEN_WIDTH) / 3), Color.rgb(255, 0, 0));

        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 4);

        obstacleManager = new ObstacleManager(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 3,
                Constants.SCREEN_HEIGHT / 13, Color.rgb(33, 75, 0));

        bulutManager = new BulutManager();
        baslatButonu = new BaslatButonu();
        settingsButton = new SettingsButton(Constants.SCREEN_WIDTH - 100, 100);
        startTime =  System.currentTimeMillis();

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
                Paint paint = new Paint();
                paint.setTextSize(Constants.SCREEN_WIDTH/10);
                paint.setColor(Color.BLACK);
                drawCenterText(canvas, paint, "Game Over",Constants.SCREEN_HEIGHT/3,r);
                int highScore=gameOver();
                drawCenterText(canvas, paint, ""+ highScore ,50 + paint.descent() - paint.ascent(),r);
                paint.setColor(Color.RED);
                paint.setTextSize((float)(paint.getTextSize()/1.6));
                drawCenterText(canvas, paint, "Best Score" ,150 + paint.descent() - paint.ascent(),r);
            }else{
                rectPlayer.update(playerPoint);
            }
            baslatButonu.draw(canvas);
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
                startTime =  System.currentTimeMillis();
                ilk = false;
                gameOver = false;
                Log.v("RESET","Başlat butonuna basıldı");
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

    private int gameOver(){
        SharedPreferences sharedPref = Constants.MAIN_ACTİVİTY.getSharedPreferences(
                Constants.MAIN_ACTİVİTY.getLocalClassName(), Context.MODE_PRIVATE);
        int defaultValue = Constants.MAIN_ACTİVİTY.getResources().getInteger(R.integer.saved_high_score_default_key);
        int highScore = sharedPref.getInt("saved_high_score_key", defaultValue);

        if(obstacleManager.getScore()>highScore) {
            sharedPref = Constants.MAIN_ACTİVİTY.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("saved_high_score_key", obstacleManager.getScore());
            editor.commit();
        }

        //Log.v("GAME OVER İÇİ","gameover içi");
       // Log.v("GAME OVER İÇİ","highscrore = "+highScore);
        highScore = sharedPref.getInt("saved_high_score_key", defaultValue);
        return highScore;
    }



}
