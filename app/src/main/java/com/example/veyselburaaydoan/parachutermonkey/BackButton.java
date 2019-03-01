package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BackButton implements GameObject {
    private int butonGenisligi = 100;
    private int butonYuksekligi = 100;
    private int gidilecekSenaryo;
    private Rect rectangle;
    /* renamed from: x */
    private int f13x;
    /* renamed from: y */
    private int f14y;

    public BackButton(int x, int y, int gidilecekSenaryo) {
        this.f13x = x;
        this.f14y = y;
        this.gidilecekSenaryo = gidilecekSenaryo;
        int i = this.butonGenisligi;
        int i2 = x - (i / 2);
        int i3 = this.butonYuksekligi;
        this.rectangle = new Rect(i2, y - (i3 / 2), (i / 2) + x, (i3 / 2) + y);
    }

    public int getGidilecekSenaryo() {
        return this.gidilecekSenaryo;
    }

    public void draw(Canvas canvas) {
        BitmapFactory bf = new BitmapFactory();
        canvas.drawBitmap(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.back_button), null, this.rectangle, new Paint());
    }

    public void update() {
    }

    public boolean doesCollide(Rect rect) {
        return Rect.intersects(this.rectangle, rect);
    }
}