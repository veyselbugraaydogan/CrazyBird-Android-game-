package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;


public class SettingsScene implements Scene {

    private Dugme resumeDugmesi;
    private Dugme creditsDugmesi;
    private SesButonu sesButonu;
    private int sceneNumber;

    public SettingsScene(int sceneNumber) {

        this.sceneNumber = sceneNumber;

        int y = Constants.SCREEN_HEIGHT / 3;
        int x = Constants.SCREEN_WIDTH / 2;
        resumeDugmesi = new Dugme(x, y,"RESUME",Constants.SCREEN_WIDTH/2,100);

        creditsDugmesi = new Dugme(x,(int)(y+resumeDugmesi.getButonYuksekligi()*1.62),"CREDITS"
                ,Constants.SCREEN_WIDTH/2,100);

        y=2*Constants.SCREEN_HEIGHT/3;  

        sesButonu = new SesButonu(x,y);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(150, 255, 255));//arka plan
        resumeDugmesi.draw(canvas);
        creditsDugmesi.draw(canvas);
        sesButonu.draw(canvas);
/*
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);*/
    }

    @Override
    public void onTerminate() {
        SceneManager.setActiveScene(sceneNumber);
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if(resumeDugmesi.doesCollide(new Rect(x,y,x+1,y+1))){
                Log.v("Touch recieved::","resume düğmesine basıldı");
                SceneManager.setScene(sceneNumber,0);
            }

            if (sesButonu.doesCollide(new Rect(x,y,x+1,y+1))){
                sesButonu.soundOnOf();
            }

            if(creditsDugmesi.doesCollide(new Rect(x,y,x+1,y+1))){
                SceneManager.setScene(sceneNumber,2);
            }

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
