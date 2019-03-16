package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class BaslatButonu implements ButtonInterface{
    private int butonGenisligi ;
    private int butonYuksekligi ;
    private Rect rectangle = new Rect((Constants.SCREEN_WIDTH / 2) - (this.butonGenisligi / 2), ((Constants.SCREEN_HEIGHT * 2) / 3) - (this.butonYuksekligi / 2), (Constants.SCREEN_WIDTH / 2) + (this.butonGenisligi / 2), ((Constants.SCREEN_HEIGHT * 2) / 3) + (this.butonYuksekligi / 2));

    public BaslatButonu(Point point,int butonYuksekligi,int butonGenisligi){
        this.butonGenisligi=butonGenisligi;
        this.butonYuksekligi=butonYuksekligi;
        setButtonCoordinate(point);
    }

    public void draw(Canvas canvas) {
        BitmapFactory bf = new BitmapFactory();
        canvas.drawBitmap(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.play_button), null, this.rectangle, new Paint());
    }

    @Override
    public void update() {

    }

    public boolean doesCollide(Rect rect) {
        return Rect.intersects(this.rectangle, rect);
    }

    @Override
    public void setButtonCoordinate(Point point) {
        rectangle = new Rect(point.x-butonGenisligi/2,point.y - butonYuksekligi/2,
                point.x+butonGenisligi/2,point.y + butonYuksekligi/2);
    }
}