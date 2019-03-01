package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Trap implements GameObject  {

    //private Rect rectangle;
    private ArrayList<Rect> rectangles;

    private Bitmap idleImg;

    private Rect rectPlayer;

    public Trap(int high,int low,int tuzakSayisi,Bitmap bitmap,int tuzakBuyuklugu){


        rectangles = new ArrayList<>();

        for(int i=0;i< tuzakSayisi ;i++){
            int x= (int)(Math.random() * (1 + Constants.SCREEN_WIDTH));
            int y= (int)((Math.random() * ( high - low )) +low );

            rectangles.add(0,new Rect(x,y,x+tuzakBuyuklugu,y+tuzakBuyuklugu));
        }

        idleImg=bitmap;

        this.rectPlayer = rectPlayer;

    }

    public int getRectangle() {
        int y=-Constants.SCREEN_HEIGHT;
        for(Rect r:rectangles){
            if (r.bottom > y){
                y=r.bottom;
            }

        }

        return y;
    }


    @Override
    public void draw(Canvas canvas) {

        for(Rect r:rectangles){
            canvas.drawBitmap(idleImg,null,r,null);
        }

    }

    @Override
    public void update() {

    }

    public void yukarıCıkar(float y){

        for(Rect r:rectangles){
            r.top -= y;
            r.bottom -= y;
        }

    }


    public boolean playerCollide(Rect player){

        for(Rect r:rectangles){
           if( Rect.intersects( r,player ) ){
               return true;
           }
        }
        return false;
    }

}