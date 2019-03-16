package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class CreditsScene implements Scene {

    private BackButton backButton;
    private int sceneNumber;

    public CreditsScene(int sceneNumber) {
       this.sceneNumber=sceneNumber;
        backButton = new BackButton(100,100,1,100,100);
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.rgb(0, 0, 0));//arka plan
        backButton.draw(canvas);
        yaziYaz("Created by",canvas,Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2,Color.WHITE);
        yaziYaz("Veysel Buğra Aydoğan",canvas,Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2+100,Color.WHITE);
    }

    @Override
    public void onTerminate() {
        SceneManager.setActiveScene(sceneNumber);
    }


    @Override
    public void recieveTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if(backButton.doesCollide(new Rect(x,y,x+1,y+1))){
                SceneManager.setScene(sceneNumber,backButton.getGidilecekSenaryo());
            }

        }

    }

    @Override
    public void onCreate() {


    }

    @Override
    public void setSceneNumber(int number) {

    }

    @Override
    public void doTheMath() {

    }

    private void yaziYaz(String string, Canvas canvas,float x,float y,int color){
        Paint paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH/15);
        paint.setColor(color);
        float txtSize = paint.getTextSize();
        int strSize = string.length() / 2;
        x = (float)(x-(strSize*(txtSize/2)));

        canvas.drawText(string , x, y , paint);
    }
}
