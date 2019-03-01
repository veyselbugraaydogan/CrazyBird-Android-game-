package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BaslatButonu {
    private int butonGenisligi = 430;
    private int butonYuksekligi = 300;
    private Rect rectangle = new Rect((Constants.SCREEN_WIDTH / 2) - (this.butonGenisligi / 2), ((Constants.SCREEN_HEIGHT * 2) / 3) - (this.butonYuksekligi / 2), (Constants.SCREEN_WIDTH / 2) + (this.butonGenisligi / 2), ((Constants.SCREEN_HEIGHT * 2) / 3) + (this.butonYuksekligi / 2));

    public void draw(Canvas canvas) {
        BitmapFactory bf = new BitmapFactory();
        canvas.drawBitmap(BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.play_button), null, this.rectangle, new Paint());
    }

    public boolean doesCollide(Rect rect) {
        return Rect.intersects(this.rectangle, rect);
    }
}