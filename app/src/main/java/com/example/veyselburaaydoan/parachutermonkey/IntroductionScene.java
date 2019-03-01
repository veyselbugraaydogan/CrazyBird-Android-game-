package com.example.veyselburaaydoan.parachutermonkey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class IntroductionScene implements Scene {
    private int sceneNumber;
    private int seviye=0;
    private Dugme okDugmesi;

    private Bitmap trap;

    private Rect rect;

    private RectPlayer rectPlayer;
    private BulutManager bulutManager;
    private Rect arkaPlan;
    private Paint arkaPlanRengi;
    private Point playerPoint;

    private boolean movingPlayer = false,moveRight = false;

    public IntroductionScene(int sceneNumber){
        this.sceneNumber=sceneNumber;
        int y = 2*Constants.SCREEN_HEIGHT / 3;
        int x = Constants.SCREEN_WIDTH / 2;
        okDugmesi = new Dugme(x, y,"OK");

        BitmapFactory bf = new BitmapFactory();
        trap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.trap);
        rect = new Rect(Constants.SCREEN_WIDTH/4,Constants.SCREEN_WIDTH/4,
                3*Constants.SCREEN_WIDTH/4,3*Constants.SCREEN_WIDTH/4);

        rectPlayer = new RectPlayer(new Rect(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_WIDTH / 4,
                ((Constants.SCREEN_WIDTH) / 3), (Constants.SCREEN_WIDTH) / 3), Color.rgb(255, 0, 0));

        bulutManager = new BulutManager();
        arkaPlanRengi = new Paint();
        arkaPlanRengi.setColor(Color.rgb(150, 255, 255));

        arkaPlan = new Rect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 1 * Constants.SCREEN_HEIGHT / 4);


    }

    @Override
    public void update() {

        bulutManager.update();

        if (playerPoint.x < 0)
            playerPoint.x = 0;
        else if (playerPoint.x > Constants.SCREEN_WIDTH)
            playerPoint.x = Constants.SCREEN_WIDTH;

        if (movingPlayer) {
            if (moveRight) {
                playerPoint.set(playerPoint.x + 3, playerPoint.y);
            } else {
                playerPoint.set(playerPoint.x - 3, playerPoint.y);
            }
        }

        rectPlayer.update(playerPoint);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(arkaPlan,arkaPlanRengi);
        bulutManager.draw(canvas);
        rectPlayer.draw(canvas);
        if(seviye==0){

            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            canvas.drawRect(new Rect(0,Constants.SCREEN_HEIGHT/2,
                    Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT),paint);
           // paint = new Paint();
            paint.setTextSize(60);
            paint.setColor(Color.WHITE);
            GnrlUtils.drawMultilineText(canvas,Constants.solagit,paint,
                    new Rect(Constants.SCREEN_WIDTH/2,
                            Constants.SCREEN_HEIGHT/2,
                            Constants.SCREEN_WIDTH,
                            Constants.SCREEN_HEIGHT),1);
            okDugmesi.setPoint(3*Constants.SCREEN_WIDTH/4,5*Constants.SCREEN_HEIGHT/6);
            okDugmesi.draw(canvas);
            DialogClass dg =new DialogClass(paint,10,new Point(10,10),"Hello world teeütat");

        }

        else if(seviye==1){
            okDugmesi.setPoint(Constants.SCREEN_WIDTH/4,5*Constants.SCREEN_HEIGHT/6);
            okDugmesi.draw(canvas);
        }
        else if(seviye==2){
            /*
            Paint paint = new Paint();
            paint.setTextSize(Constants.KUCUK_YAZI);
            paint.setColor(Color.WHITE);
            okDugmesi.setPoint(Constants.SCREEN_WIDTH/2,2*Constants.SCREEN_HEIGHT/3);
            okDugmesi.draw(canvas);
            GeneralUtils.drawCenterText(canvas,paint,Constants.tuzakYazisi,
                    Constants.SCREEN_HEIGHT/2,new Rect());
            canvas.drawBitmap(trap,null,rect,null);*/
        }
        else if(seviye==3){
            okDugmesi.setPoint(Constants.SCREEN_WIDTH/2,2*Constants.SCREEN_HEIGHT/3);
            okDugmesi.draw(canvas);
        }else{
            SceneManager.setScene(sceneNumber,0);
        }
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if(okDugmesi.doesCollide(new Rect(x,y,x+1,y+1))){
                Log.v("Touch recieved::","resume düğmesine basıldı");
                seviye++;
            }

            if ((int) event.getY() > Constants.SCREEN_HEIGHT / 2) {
                movingPlayer = true;
                if (seviye == 1 &&(int) event.getX() > Constants.SCREEN_WIDTH / 2) {
                    moveRight = true;
                }

                if (seviye == 0 &&(int) event.getX() < Constants.SCREEN_WIDTH / 2) {
                    moveRight = false;
                }

            }


        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            movingPlayer = false;
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void setSceneNumber(int number) {

    }

    @Override
    public void doTheMath() {

    }


}
