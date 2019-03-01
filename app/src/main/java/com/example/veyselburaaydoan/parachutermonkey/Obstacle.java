package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private Bitmap img;
    private int obstacleHeight,playerGap;


    public Obstacle (int rectHeight,int color,int startX,int startY,int playerGap,Bitmap img){
        this.color = color;
        obstacleHeight=rectHeight;
        this.playerGap=playerGap;
        //l,t,r,b
        rectangle = new Rect(0,startY,startX,startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap , startY , Constants.SCREEN_WIDTH , startY+rectHeight);

        this.img =img;

    }


    public void yerDegistir(int xStart,int yStart){
        rectangle.set(0,yStart,xStart,yStart+obstacleHeight);
        rectangle2.set(xStart+playerGap,yStart,Constants.SCREEN_WIDTH , yStart+obstacleHeight);
    }

    public Rect getRectangle() {
        return rectangle;
    }
    public Rect getRectangle2() {
        return rectangle2;
    }

    public void yukarıCıkar(float y){
        rectangle.top -= y;
        rectangle.bottom -= y;
        rectangle2.top -= y;
        rectangle2.bottom -= y;
    }

    public boolean playerCollide(Rect player){
        return Rect.intersects(rectangle,player ) || Rect.intersects(rectangle2,player );
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(img,null,rectangle,null);
        canvas.drawBitmap(img,null,rectangle2,null);
    }

    @Override
    public void update() {

    }

}
