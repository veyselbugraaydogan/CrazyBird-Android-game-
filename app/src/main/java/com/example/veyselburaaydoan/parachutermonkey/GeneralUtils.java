package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;


public class GeneralUtils {

    public static void calcultateElapsedTime(){
        if(((System.currentTimeMillis() - Constants.START_TIME) - Constants.ELAPSED_TIME)>2000){
            Constants.START_TIME = System.currentTimeMillis() - Constants.ELAPSED_TIME;
        }

        Constants.ELAPSED_TIME = System.currentTimeMillis() - Constants.START_TIME;
    }

    public static Bitmap adjustOpacity(Bitmap bitmap, int opacity)
    {
        Bitmap mutableBitmap = bitmap.isMutable()
                ? bitmap
                : bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        int colour = (opacity & 0xFF) << 24;
        canvas.drawColor(colour, PorterDuff.Mode.DST_IN);
        return mutableBitmap;
    }

    public static void drawCenterText(Canvas canvas, Paint paint, String text, int y, Rect r) {
        Log.v("General Utils", "drawCenterText");
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        canvas.drawText(text, x, y, paint);
    }

    public static long getAnimationOpacity(long toplamZaman,long gecenZaman,long baslangicZamani,int acilisZamani){

        int ACILIS_ZAMANI = acilisZamani;

        gecenZaman -=baslangicZamani;
        toplamZaman -=baslangicZamani;

        if (gecenZaman<=0) {
            Log.v("General Utils", "0 ," + gecenZaman);
            return 0;
        }
        else if (gecenZaman<ACILIS_ZAMANI) {
            Log.v("General Utils", "baslangıc ," + gecenZaman);
            return (255 * gecenZaman / ACILIS_ZAMANI);
        }
        else if ((toplamZaman - gecenZaman)<ACILIS_ZAMANI) {
            Log.v("General Utils", "bitis ," + gecenZaman);
            return (255 * (toplamZaman - gecenZaman) / ACILIS_ZAMANI);
        }
        else {
            Log.v("General Utils", "255 ," + gecenZaman);
            return 255;
        }

    }


    public static void drawMultilineText(Canvas canvas,String text,Paint paint,Rect rect,int lineSpace) {
        text +=" ";

        ArrayList<Integer> stringData = new ArrayList<>();

        int a=0;

        //analiz

        for (int i=0;i<text.length();i++){
            if( ' ' == text.charAt(i)){
                stringData.add(i-a);
                a=i+1;
                //Log.v("General Utils", "if içerisi ");
            }
        }
/*
        Log.v("General Utils", "drawMultilines : " + stringData.size());*/
        Log.v("General Utils", "drawMultilines : " + stringData.toString());


        //yapıyı oluşturma

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.height();
        int width = bounds.width();
/*
        Log.v("General Utils", "height: " + height);
        Log.v("General Utils", "width : " + width);
*/

        width /=text.length();

        float x=rect.left;
        float y=rect.top+height;
        int indexCount=0;
        int baslangicIndexi=0;
        for(int i=0;i<stringData.size();i++){
            x +=(stringData.get(i)+1)*width;
            indexCount +=stringData.get(i)+1;
            if(x > rect.right){
                x = rect.left;
                indexCount -=stringData.get(i)+1;
                i--;
                canvas.drawText(getStringBetweenIndex(text,baslangicIndexi,indexCount), rect.left, y, paint);
                baslangicIndexi = indexCount;
                y += height + lineSpace*height;
                Log.v("General Utils", "if içerisi ");
            }else if(i==stringData.size()-1){
                canvas.drawText(getStringBetweenIndex(text,baslangicIndexi,indexCount), rect.left, y, paint);
                Log.v("General Utils", "if içerisi ");
            }
        }
    }

    private static String getStringBetweenIndex(String text,int baslangicIndexi,int bitisIndexi){
        String s=new String();
        for (int i=baslangicIndexi;i<bitisIndexi;i++){
            s +=text.charAt(i);
        }
        return s;
    }

}
