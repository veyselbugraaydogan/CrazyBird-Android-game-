package com.example.veyselburaaydoan.parachutermonkey;

import android.content.Context;
import android.content.SharedPreferences;
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
    private final long ACILIS_SURESI=5000;
    private int sceneNumber;
    private int saniye=0;
    private int opacity=0;
    private Paint paint;
    private Rect rect;

    private SharedPreferences sharedPref;


    private Bitmap initBitmap;

    public InitializationScreenScene(int sceneNumber){
        this.sceneNumber = sceneNumber;
        Constants.START_TIME = System.currentTimeMillis();

        BitmapFactory bf = new BitmapFactory();
        initBitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.atena_logo_with_text);

        int imageHeight =initBitmap.getHeight();
        int imageWidth =initBitmap.getWidth();

        Log.w(TAG,"height ="+imageHeight+" width =" + imageWidth);

        int realWidth=2*Constants.SCREEN_WIDTH/4;
        int realHeight = imageHeight * realWidth / imageWidth;



        paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH/10);
        paint.setColor(Color.WHITE);

        rect = new Rect(Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGHT/2-realHeight/2,
                3*Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGHT/2+realHeight/2);
    }

    @Override
    public void update() {
        if(((System.currentTimeMillis() - Constants.START_TIME ) - Constants.ELAPSED_TIME)>2000){
            Constants.START_TIME = System.currentTimeMillis() - Constants.ELAPSED_TIME;
        }

        Constants.ELAPSED_TIME = System.currentTimeMillis() - Constants.START_TIME;

        Log.v(TAG,"Elapsed Time ::" + Constants.ELAPSED_TIME);

        if(Constants.ELAPSED_TIME>=ACILIS_SURESI){

            /*Burada scene değiştirilme işlemi var*/

            if (isFirst()){
                SceneManager.setScene(sceneNumber,4);
            }else{
                SceneManager.setScene(sceneNumber,0);
            }
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
                            1000,
                                (int)ACILIS_SURESI/3))),
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
        Log.v(TAG,"onRewardedVideoAdLeftApplication");
    }

    @Override
    public void setSceneNumber(int number) {
        sceneNumber=number;
    }

    @Override
    public void doTheMath() {


        saniye = (int) Constants.ELAPSED_TIME / 1000;
    }

    private boolean isFirst(){
        sharedPref = Constants.MAIN_ACTIVITY.getSharedPreferences(
                Constants.MAIN_ACTIVITY.getLocalClassName(), Context.MODE_PRIVATE);
        return sharedPref.getBoolean("ilkMiPref", true);
    }

}
