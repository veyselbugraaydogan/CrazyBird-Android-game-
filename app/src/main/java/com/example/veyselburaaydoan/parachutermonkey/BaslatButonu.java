package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class BaslatButonu implements ButtonInterface{
    private int butonGenisligi ;
    private int butonYuksekligi ;
    private Bitmap bitmap;
    private Rect rectangle = new Rect((Constants.SCREEN_WIDTH / 2) - (this.butonGenisligi / 2), ((Constants.SCREEN_HEIGHT * 2) / 3) - (this.butonYuksekligi / 2), (Constants.SCREEN_WIDTH / 2) + (this.butonGenisligi / 2), ((Constants.SCREEN_HEIGHT * 2) / 3) + (this.butonYuksekligi / 2));

    public BaslatButonu(Point point, int butonYuksekligi, int butonGenisligi, Bitmap bitmap){
        this.butonGenisligi=butonGenisligi;
        this.butonYuksekligi=butonYuksekligi;
        this.bitmap = bitmap;
        setButtonCoordinate(point);
    }

    public void draw(Canvas canvas) {
        BitmapFactory bf = new BitmapFactory();
        canvas.drawBitmap(bitmap, null, this.rectangle, new Paint());
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