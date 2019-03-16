package com.example.veyselburaaydoan.parachutermonkey;

import android.content.Context;
import android.content.SharedPreferences;
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

    private DialogClass dialog;

    private Paint paint;
    private Paint textPaint;

    private SharedPreferences sharedPref;

    public IntroductionScene(int sceneNumber){
        this.sceneNumber=sceneNumber;
        int y = 2*Constants.SCREEN_HEIGHT / 3;
        int x = Constants.SCREEN_WIDTH / 2;
        okDugmesi = new Dugme(x, y,"OK",Constants.SCREEN_WIDTH/2,100);

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
        paint =new Paint();
        textPaint =new Paint();
        textPaint.setTextSize(Constants.KUCUK_YAZI);
        textPaint.setColor(Color.WHITE);
        //dialog.setBitmapBuyuklugu(150);
        //dialog.setBitmapPadding(20);

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

        dialog.update();


    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(arkaPlan,arkaPlanRengi);
        bulutManager.draw(canvas);
        rectPlayer.draw(canvas);
        if(seviye==0){
            paint = new Paint();
            paint.setColor(Constants.CURRENT_CONTEXT.getResources().getColor(R.color.gudeRect));
            canvas.drawRect(new Rect(0,Constants.SCREEN_HEIGHT/2,
                    Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT),paint);
           // paint = new Paint();
            dialog =new DialogClass(textPaint,Constants.SCREEN_WIDTH/2,
                    new Point(Constants.SCREEN_WIDTH/2,5*Constants.SCREEN_HEIGHT/8),Constants.solagit,
                    Constants.CURRENT_CONTEXT.getResources().getColor(R.color.dialog_primary),
                    Constants.CURRENT_CONTEXT.getResources().getColor(R.color.dialog_second),null);
            dialog.draw(canvas);
        }

        else if(seviye==1){
            paint = new Paint();
            paint.setColor(Constants.CURRENT_CONTEXT.getResources().getColor(R.color.gudeRect));
            canvas.drawRect(new Rect(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,
                    Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT),paint);
            // paint = new Paint();

            dialog = new DialogClass(textPaint,Constants.SCREEN_WIDTH/2,
                    new Point(0,5*Constants.SCREEN_HEIGHT/8),Constants.sagaGit,
                    Constants.CURRENT_CONTEXT.getResources().getColor(R.color.dialog_primary),
                    Constants.CURRENT_CONTEXT.getResources().getColor(R.color.dialog_second),null);
            dialog.draw(canvas);

        }
        else if(seviye==2){
            dialog = new DialogClass(textPaint,2*Constants.SCREEN_WIDTH/3,
                    new Point(Constants.SCREEN_WIDTH/6,3*Constants.SCREEN_HEIGHT/8),Constants.tuzakYazisi,
                    Constants.CURRENT_CONTEXT.getResources().getColor(R.color.dialog_primary),
                    Constants.CURRENT_CONTEXT.getResources().getColor(R.color.dialog_second),trap);
            dialog.draw(canvas);

        }else{
            if (isFirst()){
                //applyPrefFalse();
                SceneManager.setScene(sceneNumber,0);

            }else{
                SceneManager.setScene(sceneNumber,1);
            }
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


            if (dialog.doesCollide(new Rect(x,y,x+1,y+1))){
                seviye++;
            }


            if ((int) event.getY() > Constants.SCREEN_HEIGHT / 2) {
                movingPlayer = true;
                if (seviye == 1 &&(int) event.getX() > Constants.SCREEN_WIDTH / 2) {
                    moveRight = true;
                }

                if (seviye == 0 &&(int) event.getX() < Constants.SCREEN_WIDTH / 2 ) {
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
        seviye=0;
    }

    @Override
    public void setSceneNumber(int number) {

    }

    @Override
    public void doTheMath() {

    }

    private boolean isFirst(){
        sharedPref = Constants.MAIN_ACTIVITY.getSharedPreferences(
                Constants.MAIN_ACTIVITY.getLocalClassName(), Context.MODE_PRIVATE);
        return sharedPref.getBoolean("ilkMiPref", true);
    }

    private void applyPrefFalse(){
        sharedPref = Constants.MAIN_ACTIVITY.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("ilkMiPref",false);
        editor.apply();
    }


}
