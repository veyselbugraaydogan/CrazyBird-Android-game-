package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SesButonu implements ButtonInterface{
    private int butonGenisligi = 100;
    private int butonYuksekligi = 100;
    private Rect rectangle;
    private boolean soundOn = false;
    /* renamed from: x */
    private int x;
    /* renamed from: y */
    private int y;

    public SesButonu(int x, int y) {
        this.x = x;
        this.y = y;
        int i = this.butonGenisligi;
        int i2 = x - (i / 2);
        int i3 = this.butonYuksekligi;
        rectangle = new Rect(i2, y - (i3 / 2), (i / 2) + x, (i3 / 2) + y);
    }

    public void soundOnOf() {
        if (this.soundOn) {
            this.soundOn = false;
        } else {
            this.soundOn = true;
        }
        draw(new Canvas());
    }
    @Override
    public void draw(Canvas canvas) {
        Bitmap duvarImg;
        BitmapFactory bf = new BitmapFactory();
        if (this.soundOn) {
            duvarImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ses_kapa);
        } else {
            duvarImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ses_ac);
        }
        canvas.drawBitmap(duvarImg, null, this.rectangle, new Paint());
    }
    @Override
    public void update() {
    }

    public boolean doesCollide(Rect rect) {
        return Rect.intersects(this.rectangle, rect);
    }

    @Override
    public void setButtonCoordinate(Point point) {
        this.x = point.x;
        this.y = point.y;
        int i = this.butonGenisligi;
        int i2 = x - (i / 2);
        int i3 = this.butonYuksekligi;
        rectangle = new Rect(i2, y - (i3 / 2), (i / 2) + x, (i3 / 2) + y);
    }
}