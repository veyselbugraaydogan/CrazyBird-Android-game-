package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Dugme implements ButtonInterface {

    private Rect rectangleBelow;
    private Rect rectangle;

    private int butonGenisligi=Constants.SCREEN_WIDTH/2;
    private  int butonYuksekligi=100;

    /*
    private int butonGenisligi=Constants.SCREEN_WIDTH/2;
    private  int butonYuksekligi=100;
*/
    private String butonYazisi;

    private int x;
    private int y;


    public Dugme (int x,int y,String butonYazisi,int butonGenisligi,int butonYuksekligi) {
        this.x = x;
        this.y = y;
        this.butonYazisi=butonYazisi;
        this.butonGenisligi=butonGenisligi;
        this.butonYuksekligi=butonYuksekligi;

        rectangleBelow = new Rect((( x ) - (butonGenisligi/2))-10,
                (y - butonYuksekligi/2)-10,
                ( x + (butonGenisligi/2))+10,
                (y + butonYuksekligi/2)+10);

        rectangle = new Rect(( x ) - (butonGenisligi/2),
                y - butonYuksekligi/2,
                ( x + (butonGenisligi/2)),
                y + butonYuksekligi/2);
        Log.v("DUGME","x:"+rectangleBelow.left +"y:"+rectangleBelow.top);
    }

    public int getButonYuksekligi() {
        return butonYuksekligi;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(rectangleBelow,paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(rectangle,paint);

        yaziYaz(butonYazisi,canvas,x,y,Color.BLACK);
    }

    @Override
    public void update() {

    }

    public boolean doesCollide(Rect rect){
        return Rect.intersects(rectangle , rect) || Rect.intersects(rectangleBelow , rect );
    }

    private void yaziYaz(String string, Canvas canvas,float x,float y,int color){
        Paint paint = new Paint();
        paint.setTextSize(2*butonYuksekligi/3);
        paint.setColor(color);
        float txtSize = paint.getTextSize();
        int strSize = string.length() / 2;
        x = (float)(x-(strSize*(txtSize/1.618)));
        y =y + ((butonYuksekligi-txtSize)/2);
        canvas.drawText(string , x, y , paint);
    }

    public void setButonGenisligi(int butonGenisligi) {
        this.butonGenisligi = butonGenisligi;
    }

    public void setButonYuksekligi(int butonYuksekligi) {
        this.butonYuksekligi = butonYuksekligi;
    }

    public void setPoint(int x, int y){
        this.x = x;
        this.y = y;
        rectangleBelow = new Rect((( x ) - (butonGenisligi/2))-10,
                (y - butonYuksekligi/2)-10,
                ( x + (butonGenisligi/2))+10,
                (y + butonYuksekligi/2)+10);

        rectangle = new Rect(( x ) - (butonGenisligi/2),
                y - butonYuksekligi/2,
                ( x + (butonGenisligi/2)),
                y + butonYuksekligi/2);

    }

    @Override
    public void setButtonCoordinate(Point point) {
        this.x = point.x;
        this.y = point.y;
        rectangleBelow = new Rect((( x ) - (butonGenisligi/2))-10,
                (y - butonYuksekligi/2)-10,
                ( x + (butonGenisligi/2))+10,
                (y + butonYuksekligi/2)+10);

        rectangle = new Rect(( x ) - (butonGenisligi/2),
                y - butonYuksekligi/2,
                ( x + (butonGenisligi/2)),
                y + butonYuksekligi/2);
    }
}
