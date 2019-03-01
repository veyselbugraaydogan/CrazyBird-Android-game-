package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class RectPlayer implements GameObject {

    private Rect rectangle;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;

    AnimationManager animManager;

    private Rect dondurulecekDortgen;

    public RectPlayer (Rect rectangle,int color){
        this.rectangle = rectangle;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.v3_1);
        Bitmap idleImg2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.v3_2);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.v3_sag1);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.v3_sag2);

        idle = new Animation(new Bitmap[]{idleImg, idleImg2},0.5f,true);
        walkRight = new Animation(new Bitmap[]{walk1 , walk2},0.5f,true);

        Matrix m = new Matrix();
        m.preScale(-1,1);
        walk1 = Bitmap.createBitmap(walk1,0,0,walk1.getWidth(),walk1.getHeight(),m,false);
        walk2 = Bitmap.createBitmap(walk2,0,0,walk2.getWidth(),walk2.getHeight(),m,false);

        walkLeft = new Animation(new Bitmap[]{walk1,walk2},0.5f,true);

        animManager = new AnimationManager(new Animation[]{idle,walkRight,walkLeft});


    }

    public Rect getRectangle(){

        int d = rectangle.right - rectangle.left;
         d = d/4;
        return new Rect(rectangle.left+d,rectangle.top + d,
                rectangle.right - d ,rectangle.bottom -d);
    }

    @Override
    public void draw(Canvas canvas) {

        animManager.draw(canvas,rectangle);
    }

    @Override
    public void update() {
        animManager.update();

    }

    public void update(Point point){
        //l,t,r,b/*
        float oldLeft = rectangle.left;
        rectangle.set(  point.x - rectangle.width()/2,
                point.y - rectangle.height()/2,
                point.x + rectangle.width()/2,
                point.y + rectangle.height()/2);

        int state=0;

        if (rectangle.left - oldLeft >5 ){
            state=1;
        }
        else if(rectangle.left - oldLeft < -5){
            state=2;
        }
        animManager.playAnimation(state);
        animManager.update();

    }
}
