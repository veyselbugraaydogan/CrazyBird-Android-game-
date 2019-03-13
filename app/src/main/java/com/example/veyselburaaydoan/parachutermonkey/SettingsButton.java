package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Button;

public class SettingsButton implements ButtonInterface {
    private Rect rectangle;

    private int butonGenisligi=100;
    private  int butonYuksekligi=100;

    private Bitmap duvarImg;

    private int x;
    private int y;


    public SettingsButton (int x,int y) {

        this.x = x;
        this.y = y;

        rectangle = new Rect(( x ) - (butonGenisligi/2),
                y - butonYuksekligi/2,
                ( x + (butonGenisligi/2)),
                y + butonYuksekligi/2);

        BitmapFactory bf = new BitmapFactory();
        duvarImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.settings_button);
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(duvarImg,null,rectangle,null);


    }

    @Override
    public void update() {

    }

    public boolean doesCollide(Rect rect){
        return Rect.intersects(rectangle , rect );
    }

    @Override
    public void setButtonCoordinate(Point point) {

    }
}
