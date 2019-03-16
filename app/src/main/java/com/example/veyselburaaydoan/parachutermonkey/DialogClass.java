package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class DialogClass implements GameObject {

    private final String TAG = "Dialog Class";

    private Paint textPaint;
    private int width;
    private Point solUstKose;
    private String text;
    private int lineHeight;
    private int spaceNormal;
    private int lineCount = 0;
    private float lineSpace = 1;
    private ArrayList<Integer> stringData;
    private Rect header, textRect, bottomRect, buttonRect,bitmapRect;

    private Paint primaryPaint, secondaryPaint;
    private Bitmap bitmap=null;

    private Dugme okButton;

    private int bitmapBuyuklugu , bitmapPadding ;

    public DialogClass(Paint textPaint, int width, Point solustKose, String text, int primaryColor, int secondaryColor, Bitmap bitmap) {

        this.textPaint = textPaint;
        this.width = width;
        this.solUstKose = solustKose;
        this.text = text;
        primaryPaint = new Paint();
        secondaryPaint = new Paint();
        primaryPaint.setColor(primaryColor);
        secondaryPaint.setColor(secondaryColor);
        this.bitmap = bitmap;

        initiate();
    }

    public void setBitmapBuyuklugu(int bitmapBuyuklugu) {
        this.bitmapBuyuklugu = bitmapBuyuklugu;
    }

    public void setBitmapPadding(int bitmapPadding) {
        this.bitmapPadding = bitmapPadding;
    }

/*
    public DialogClass(Paint textPaint, int width, Point solustKose, String text,int primaryColor,int secondaryColor){
        this(textPaint,width,solustKose,text,primaryColor,secondaryColor,null);
    }
*/


    private void initiate() {

        //Bu sırayla olmalı

        stringData = new ArrayList<>();

        setWidthnHeight();
        setLineCount();
        setRects();
        setButton();
        Log.v(TAG, "initiated");
    }

    private void setButton(){

        okButton = new Dugme(width/2+bottomRect.left
                ,((bottomRect.bottom - bottomRect.top)/2)+bottomRect.top
                , Constants.CURRENT_CONTEXT.getResources().getString(R.string.tamam)
                ,bottomRect.right - bottomRect.left - (2*width/3)
                , bottomRect.bottom - bottomRect.top - (bottomRect.bottom - bottomRect.top)/2);

    }


    private void setRects() {
        int x = solUstKose.x;
        int y = solUstKose.y;
        header = new Rect(x, y, x + width, y + lineHeight);
        y += lineHeight;

        if(bitmap != null){

            bitmapBuyuklugu = 3 * lineHeight;
            bitmapPadding = lineHeight;

            bitmapRect = new Rect(x,y,x + width,y + (bitmapPadding*2)+bitmapBuyuklugu);
            y +=(bitmapPadding*2)+bitmapBuyuklugu;
        }else {
            bitmapRect =null;
        }

        textRect = new Rect(x, y, x + width, y + getTextHeight());
        y += getTextHeight();

        bottomRect =new Rect(x, y, x + width, y + lineHeight*2);

        Log.v(TAG, "initiate rect");
    }

    private int getTextHeight() {
        return (int) (lineHeight + lineHeight * lineSpace) * lineCount;
    }


    @Override
    public void draw(Canvas canvas) {

        canvas.drawRect(header,secondaryPaint);
        canvas.drawRect(textRect,primaryPaint);
        if (bitmap != null) {

            canvas.drawRect(
                    bitmapRect
                    ,primaryPaint);
            canvas.drawBitmap(bitmap, null, new Rect(((width-bitmapBuyuklugu)/2)+bitmapRect.left
                    ,bitmapRect.top+bitmapPadding
                    ,((width-bitmapBuyuklugu)/2)+bitmapRect.left+bitmapBuyuklugu
                    ,bitmapRect.top+bitmapPadding+bitmapBuyuklugu), null);
        }
        GnrlUtils.drawMultilineText(canvas,text,textPaint,textRect,(int)lineSpace);

        canvas.drawRect(bottomRect,secondaryPaint);
        okButton.draw(canvas);

    }

    @Override
    public void update() {

    }

    private void setRectPoint(Rect r, int x, int y) {
        r.right += x - r.left;
        r.top += y - r.top;
        r.left = x;
        r.top = y;
    }

    public boolean doesCollide(Rect r){
        return okButton.doesCollide(r);
    }



    private void setWidthnHeight() {

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        lineHeight = bounds.height();
        int width = bounds.width();

        width /= text.length();

        spaceNormal = width;
        Log.v(TAG, "line height:"+ lineHeight + "character width: "+width );
    }

    private void setLineCount() {

        //Log.v(TAG, "calculate lines");
        text += " ";

        int a = 0;

        //analiz

        for (int i = 0; i < text.length(); i++) {
            if (' ' == text.charAt(i)) {
                stringData.add(i - a);
                a = i + 1;
            }
        }


        Log.v(TAG, "set line counts");

        float x = solUstKose.x;
        int indexCount = 0;
        int baslangicIndexi = 0;
        for (int i = 0; i < stringData.size(); i++) {
            x += (stringData.get(i) + 1) * spaceNormal;
            //indexCount += stringData.get(i) + 1;
            if (x > solUstKose.x +width+spaceNormal) {
                x = solUstKose.x;
                //indexCount -= stringData.get(i) + 1;
                i--;
                lineCount++;
            }
        }
        lineCount = lineCount + 1;
    }
}
