package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bulut2 implements GameObject{
    private Rect rectangle,rectangle2;
    private int bulutGenisligi=Constants.SCREEN_WIDTH/6;
    private int bulutYuksekligi = Constants.SCREEN_WIDTH/10;

    private Bitmap duvarImg;

    public Bulut2(Bitmap img){


        int x = Constants.SCREEN_WIDTH;
        int y = (int)(Math.random() * ( Constants.SCREEN_HEIGHT ));

        rectangle = new Rect(x,y,x+bulutGenisligi,bulutYuksekligi+y);

        x = x + Constants.SCREEN_WIDTH/2 ;
        y = (int)(Math.random() * ( Constants.SCREEN_HEIGHT));

        rectangle2 = new Rect(x,y,x+bulutGenisligi,bulutYuksekligi+y);

        duvarImg =img;


    }

    public Rect getRectangle() {
        return rectangle2;
    }

    public void solaIt(float x){
        rectangle.right  -= x;
        rectangle.left   -= x;
        rectangle2.right  -= x;
        rectangle2.left   -= x;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(duvarImg,null,rectangle,null);
        canvas.drawBitmap(duvarImg,null,rectangle2,null);

    }

    @Override
    public void update() {

    }

}
