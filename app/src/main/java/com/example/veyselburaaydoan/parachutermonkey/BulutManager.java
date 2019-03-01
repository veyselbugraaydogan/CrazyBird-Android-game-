package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BulutManager {
    private Bulut bulut;
    private Bulut2 bulut2;
    private Bitmap img;

    private int sagaIt,solaIt,width;

    public BulutManager(){

        BitmapFactory bf = new BitmapFactory();
        img = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.bulut);

        bulut = new Bulut(img);
        bulut2 = new Bulut2(img);

        sagaIt = 5*Constants.SCREEN_WIDTH/3;
        solaIt = 2*Constants.SCREEN_WIDTH;
        width=Constants.SCREEN_WIDTH;
    }

    public void update(){


        bulut.sagaIt(1);
        bulut2.solaIt(1);

        if (bulut.getRectangle2().left >= width){

            bulut.sagaIt((float)-(sagaIt));

        }
        if (bulut2.getRectangle().right <= 0){

            bulut2.solaIt((float)-(solaIt));
        }
    }

    public void draw(Canvas canvas) {
        bulut.draw(canvas);
        bulut2.draw(canvas);

    }

}
