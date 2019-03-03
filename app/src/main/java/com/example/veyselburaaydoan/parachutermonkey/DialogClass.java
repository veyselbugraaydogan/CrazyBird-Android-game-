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

    private final int bitmapBuyuklugu = 20, bitmapPadding = 30;

    public DialogClass(Paint textPaint, int width, Point solustKose, String text, int primaryColor, int secondaryColor, Bitmap bitmap) {

        this.textPaint = textPaint;
        this.width = width;
        this.solUstKose = solustKose;
        this.text = text;
        primaryPaint= new Paint(primaryColor);
        secondaryPaint= new Paint(secondaryColor);
        this.bitmap = bitmap;

        initiate();
    }
/*
    public DialogClass(Paint textPaint, int width, Point solustKose, String text,int primaryColor,int secondaryColor){
        this(textPaint,width,solustKose,text,primaryColor,secondaryColor,null);
    }
*/


    private void initiate() {
        stringData = new ArrayList<>();
        calculateTheLines();
        setWidthnHeight();
        setLineCount();
        initiateRectangles();
        Log.v(TAG, "initiated");
    }

    private void initiateRectangles() {
        int x = solUstKose.x;
        int y = solUstKose.y;
        header = new Rect(x, y, x + width, y + lineHeight);
        y += lineHeight;
        if (bitmap == null) {
            textRect = new Rect(x, y, x + width, y + getTextHeight());
            bitmapRect = null;
            y += getTextHeight();
        } else {
            textRect = new Rect(x, y, x + width, y + getTextHeight()+(bitmapPadding*2)+bitmapBuyuklugu);
            int bL=(width-bitmapBuyuklugu)/2;
            bitmapRect =new Rect(x+bL, y+bitmapPadding, x + bL +bitmapBuyuklugu,
                    y + bitmapPadding+ bitmapBuyuklugu);
            y += getTextHeight()+(bitmapPadding*2)+bitmapBuyuklugu;
        }

        bottomRect =new Rect(x, y, x + width, y + lineHeight*3);

        Log.v(TAG, "initiate rect");
    }

    private int getTextHeight() {
        return (int) (lineHeight + lineHeight * lineSpace) * lineCount;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(header,secondaryPaint);
        canvas.drawRect(textRect,primaryPaint);
        if (bitmap != null)
            canvas.drawBitmap(bitmap,null,bitmapRect,null);
        GnrlUtils.drawMultilineText(canvas,text,textPaint,textRect,(int)lineSpace);

        canvas.drawRect(bottomRect,secondaryPaint);

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

    private void calculateTheLines() {
        Log.v(TAG, "calculate lines");
        text += " ";

        int a = 0;

        //analiz

        for (int i = 0; i < text.length(); i++) {
            if (' ' == text.charAt(i)) {
                stringData.add(i - a);
                a = i + 1;
            }
        }
    }

    private void setWidthnHeight() {

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        lineHeight = bounds.height();
        int width = bounds.width();

        width /= text.length();

        spaceNormal = width;
    }

    private void setLineCount() {
        Log.v(TAG, "set line counts");

        float x = solUstKose.x;
        int indexCount = 0;
        int baslangicIndexi = 0;
        for (int i = 0; i < stringData.size(); i++) {
            x += (stringData.get(i) + 1) * width;
            indexCount += stringData.get(i) + 1;
            if (x > x +width) {
                x = solUstKose.x;
                indexCount -= stringData.get(i) + 1;
                i--;
                lineCount++;
                baslangicIndexi = indexCount;
            }
        }
        lineCount = lineCount + 1;
    }
}
