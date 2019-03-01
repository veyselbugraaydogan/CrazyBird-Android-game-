package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class InitializationScreenScene implements Scene {


    private final String TAG = "INITIALIZATION SCREEN::";
    private final long ACILIS_SURESI=0;
    private int sceneNumber;
    private int saniye=0;
    private int opacity=0;
    private Paint paint;
    private Rect rect;


    private Bitmap initBitmap;

    public InitializationScreenScene(int sceneNumber){
        this.sceneNumber = sceneNumber;
        Constants.START_TIME = System.currentTimeMillis();
        BitmapFactory bf = new BitmapFactory();
        initBitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.v3_1);
        paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH/10);
        paint.setColor(Color.WHITE);

        rect = new Rect(Constants.SCREEN_WIDTH/4,Constants.SCREEN_WIDTH/4,
                3*Constants.SCREEN_WIDTH/4,3*Constants.SCREEN_WIDTH/4);
    }

    @Override
    public void update() {
        if(((System.currentTimeMillis() - Constants.START_TIME ) - Constants.ELAPSED_TIME)>2000){
            Constants.START_TIME = System.currentTimeMillis() - Constants.ELAPSED_TIME;
        }

        Constants.ELAPSED_TIME = System.currentTimeMillis() - Constants.START_TIME;

        Log.v(TAG,"Elapsed Time ::" + Constants.ELAPSED_TIME);

        if(Constants.ELAPSED_TIME>=ACILIS_SURESI){
            SceneManager.setScene(sceneNumber,4);
        }
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(
                GnrlUtils.adjustOpacity(
                        initBitmap,
                        (int)(GnrlUtils.getAnimationOpacity(
                            ACILIS_SURESI,
                            Constants.ELAPSED_TIME,
                            2000,
                            2000))),
                null, rect, null);

    }

    @Override
    public void onTerminate() {
        SceneManager.setActiveScene(sceneNumber);
    }

    @Override
    public void recieveTouch(MotionEvent event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void setSceneNumber(int number) {
        sceneNumber=number;
    }

    @Override
    public void doTheMath() {


        saniye = (int) Constants.ELAPSED_TIME / 1000;
    }

}
