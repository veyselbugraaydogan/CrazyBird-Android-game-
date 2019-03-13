package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.ArrayList;


public class SettingsScene implements Scene {

    private Dugme resumeDugmesi;
    private Dugme creditsDugmesi;
    private Dugme introductionDugmesi;
    private SesButonu sesButonu;

    private int sceneNumber;

    private int buttonGenisligi=Constants.SCREEN_WIDTH/2,buttonYuksekligi=100;

    private ArrayList<ButtonInterface> objects ;

    public SettingsScene(int sceneNumber) {

        this.sceneNumber = sceneNumber;

        initialize();

        setButtonCoordinate();



    }

    private void initialize(){
        resumeDugmesi = new Dugme(0, 0,"RESUME",buttonGenisligi,buttonYuksekligi);

        creditsDugmesi = new Dugme(0,0,"CREDITS"
                ,buttonGenisligi,buttonYuksekligi);

        introductionDugmesi = new Dugme(0,0,"HOW TO PLAY",buttonGenisligi,buttonYuksekligi);

        sesButonu = new SesButonu(0,0);


        objects = new ArrayList<>();
        objects.add(resumeDugmesi);
        objects.add(introductionDugmesi);
        objects.add(creditsDugmesi);
        objects.add(sesButonu);
    }

    private void setButtonCoordinate(){
        int y=0;
        int spaceBetweenY=Constants.SCREEN_HEIGHT/(objects.size()+1);
        for (ButtonInterface buttonInterface:objects){
            y +=spaceBetweenY;
            buttonInterface.setButtonCoordinate(new Point(Constants.SCREEN_WIDTH / 2,y));
        }
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(150, 255, 255));//arka plan
        for (ButtonInterface buttons:objects) {
            buttons.draw(canvas);
        }
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

            if(introductionDugmesi.doesCollide(new Rect(x,y,x+1,y+1))){
                SceneManager.setScene(sceneNumber,4);
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
