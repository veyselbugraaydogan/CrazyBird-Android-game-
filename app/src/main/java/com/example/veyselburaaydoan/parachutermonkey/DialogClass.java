package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class DialogClass implements GameObject{

    private final String TAG="Dialog Class";

    private Paint textPaint;
    private int width;
    private Point solUstKose;
    private String text;
    private int height;
    private int spaceNormal;
    private int lineCount=0;
    private int lineSpace=1;
    private ArrayList<Integer> stringData;
    private Rect header,textRect,bottom,button;

    private int primaryColor,secondaryColor;

    public DialogClass(Paint textPaint, int width, Point solustKose, String text,int primaryColor,int secondaryColor){

        this.textPaint =textPaint;
        this.width = width;
        this.solUstKose=solustKose;
        this.text=text;
        this.primaryColor=primaryColor;
        this.secondaryColor=secondaryColor;

        initiate();

    }


    public void initiate(){
        stringData=new ArrayList<>();
        calculateTheLines();
        setWidthnHeight();
        setLineCount();
    }



    @Override
    public void draw(Canvas canvas) {

        float x=textRect.left;
        float y=textRect.top+height;
        int indexCount=0;
        int baslangicIndexi=0;
        for(int i=0;i<stringData.size();i++){
            x +=(stringData.get(i)+1)*width;
            indexCount +=stringData.get(i)+1;
            if(x > textRect.right){
                x = textRect.left;
                indexCount -=stringData.get(i)+1;
                i--;
                canvas.drawText(GnrlUtils.getStringBetweenIndex(text,baslangicIndexi,indexCount), textRect.left, y, textPaint);
                baslangicIndexi = indexCount;
                y += height + lineSpace*height;
                //Log.v("General Utils", "if içerisi ");
            }
        }
        canvas.drawText(GnrlUtils.getStringBetweenIndex(text,baslangicIndexi,indexCount), textRect.left, y, textPaint);

    }

    @Override
    public void update() {

    }

    private void setRectPoint(Rect r,int x,int y){
        r.right += x-r.left;
        r.top   += y-r.top;
        r.left = x;
        r.top = y;
    }

    private void calculateTheLines(){
        text +=" ";

        int a=0;

        //analiz

        for (int i=0;i<text.length();i++){
            if( ' ' == text.charAt(i)){
                stringData.add(i-a);
                a=i+1;
            }
        }
    }

    private void setWidthnHeight(){

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();

        width /=text.length();

        spaceNormal=width;
    }

    private void setLineCount(){

        float x=textRect.left;
        float y=textRect.top;
        int indexCount=0;
        int baslangicIndexi=0;
        for(int i=0;i<stringData.size();i++){
            x +=(stringData.get(i)+1)*width;
            indexCount +=stringData.get(i)+1;
            if(x > textRect.right){
                x = textRect.left;
                indexCount -=stringData.get(i)+1;
                i--;
                lineCount++;
                baslangicIndexi = indexCount;
                y += height + lineSpace*height;
            }
        }
        lineCount = lineCount+1;
    }
}
