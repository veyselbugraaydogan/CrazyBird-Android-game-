package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private Bitmap img;
    private int obstacleHeight,playerGap;
    private int playerGapOynatmaYonu=1;

    private String TAG = "Obstacle :";


    public Obstacle (int rectHeight,int color,int startX,int startY,int playerGap,Bitmap img){
        this.color = color;
        obstacleHeight=rectHeight;
        this.playerGap=playerGap;
        //l,t,r,b
        rectangle = new Rect(0,startY,startX,startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap , startY , Constants.SCREEN_WIDTH , startY+rectHeight);

        this.img =img;
        setPlayerGapOynatmaYonu();

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

    private void setPlayerGapOynatmaYonu(){
        if (rectangle.right + playerGap/2 < Constants.SCREEN_WIDTH/2)
            playerGapOynatmaYonu=1;
        else
            playerGapOynatmaYonu=-1;
        //Log.v(TAG,"Player gap yonu degisti");
    }

    public void playerGapiOynat(int hiz){
        /*Bu metod player gapi saga veya sola dogru oynatir.
        * Oynatma yonu arti ise saga, eksi ise sola dogru oynatir.*/

        int vektor = hiz*playerGapOynatmaYonu;

        /*Oncesinde player gap iki uctan birine geldi mi bunu kontrol ediyor.*/

        if (rectangle.right+vektor<0 || rectangle2.left+vektor>Constants.SCREEN_WIDTH)
            setPlayerGapOynatmaYonu();
        rectangle.right +=vektor;
        rectangle2.left +=vektor;

        //Log.v(TAG,"vektör :"+vektor);

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
