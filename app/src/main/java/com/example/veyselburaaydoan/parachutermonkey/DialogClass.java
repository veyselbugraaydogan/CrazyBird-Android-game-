package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class DialogClass implements GameObject{

    private Rect header,textRect,bottom,button;

    public DialogClass(float textSize, int width, Point solustKose,String text){

    }


    @Override
    public void draw(Canvas canvas) {

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
}
