package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void onTerminate();
    public void recieveTouch(MotionEvent event);
    public void onCreate();
    public void setSceneNumber(int number);
    public void doTheMath();
}
