package com.example.veyselburaaydoan.parachutermonkey;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class SesButonu implements ButtonInterface{

    private final String TAG = "Ses butonu";

    private int butonGenisligi = 100;
    private int butonYuksekligi = 100;
    private Rect rectangle;
    private boolean isSoundOn;
    /* renamed from: x */
    private int x;
    /* renamed from: y */
    private int y;

    private SharedPreferences sharedPref;

    private GameplayScene gameplayScene;

    public SesButonu(int x, int y) {
        this.x = x;
        this.y = y;
        int i = this.butonGenisligi;
        int i2 = x - (i / 2);
        int i3 = this.butonYuksekligi;
        rectangle = new Rect(i2, y - (i3 / 2), (i / 2) + x, (i3 / 2) + y);


        sharedPref = Constants.CURRENT_CONTEXT.getSharedPreferences(Constants.isSoundOnSharedPref,Context.MODE_PRIVATE);

        isSoundOn = sharedPref.getBoolean(Constants.isSoundOnSharedPref, true);



    }

    public void soundOnOf() {
        if (isSoundOn) {

            isSoundOn = false;
            sharedPref = Constants.CURRENT_CONTEXT.getSharedPreferences(Constants.isSoundOnSharedPref,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(Constants.isSoundOnSharedPref, isSoundOn);
            editor.commit();

        } else {
            isSoundOn = true;
            sharedPref = Constants.CURRENT_CONTEXT.getSharedPreferences(Constants.isSoundOnSharedPref,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(Constants.isSoundOnSharedPref, isSoundOn);
            editor.commit();
        }
        Log.w(TAG,"is sound on : "+isSoundOn + sharedPref.getBoolean(Constants.isSoundOnSharedPref, true));
        SceneManager.getGamePlayScene().getSoundManager().setSound(isSoundOn);
        draw(new Canvas());
    }
    @Override
    public void draw(Canvas canvas) {
        Bitmap duvarImg;
        BitmapFactory bf = new BitmapFactory();
        if (isSoundOn) {
            duvarImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ses_ac);
        } else {
            duvarImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ses_kapa);
        }
        canvas.drawBitmap(duvarImg, null, rectangle, new Paint());
    }
    @Override
    public void update() {
    }

    public boolean doesCollide(Rect rect) {
        return Rect.intersects(rectangle, rect);
    }

    @Override
    public void setButtonCoordinate(Point point) {
        this.x = point.x;
        this.y = point.y;
        int i = butonGenisligi;
        int i2 = x - (i / 2);
        int i3 = butonYuksekligi;
        rectangle = new Rect(i2, y - (i3 / 2), (i / 2) + x, (i3 / 2) + y);
    }
}