package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class ObstacleManager {
    //higher index = lover on screen =higher in y value

    private ArrayList<Obstacle> obstacles;
    private ArrayList<Obstacle> o;
    private ArrayList<Trap> tuzaklar;
    private ArrayList<Trap> t;
    private ArrayList<Rect> kirpilacak;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private float speed;
    private int tuzakSayisi;
    private int tuzakBuyuklugu;

    private Bitmap trapImg,obstacleImg;

    private Paint paint;

    private int obs=0;
    private int obstacleIndex=0;

    private int xStart;

    private int obsInd;

    private int score = 0;

    public void setScore(int score) {
        this.score = score;
    }

    private final int engelSayisi=3;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        tuzakBuyuklugu = Constants.SCREEN_WIDTH / 30;

        BitmapFactory bf = new BitmapFactory();
        trapImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.trap);
        obstacleImg =bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.duvar);


        obstacles = new ArrayList<>();
        tuzaklar = new ArrayList<>();

        o = new ArrayList<>();
        t = new ArrayList<>();

        kirpilacak = new ArrayList<>();


        paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);

        populateObstacles();
    }

    public boolean playerCollide(Rect player) {

        for (Obstacle ob : o) {
            if (ob.playerCollide(player))
                return true;
        }
        for (Trap t : t) {
            if (t.playerCollide(player))
                return true;
        }

        return false;
    }


    private void populateObstacles() {
    //    int suankiY = 3 * Constants.SCREEN_HEIGHT / 2;
/*
        while (suankiY > 0) {

            Log.v("POPULATE====","new Obstacle");

            int xBaslangıcı = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xBaslangıcı, suankiY + Constants.SCREEN_HEIGHT, playerGap,obstacleImg));
            suankiY -= obstacleHeight + obstacleGap;

        }

*/

        int startY=Constants.SCREEN_HEIGHT;

        for(int i=0;i<3;i++) {

            xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, startY, playerGap, obstacleImg));
            startY +=obstacleHeight + obstacleGap;

        }
    /*
        for(int i=0;i<3;i++){
            int xBaslangıcı = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xBaslangıcı, suankiY + Constants.SCREEN_HEIGHT, playerGap,obstacleImg));
            suankiY -= obstacleHeight + obstacleGap;
        }
    */
        // Log.v("POPULATE===="," sz:: " + sz);

        for (Obstacle ob : obstacles) {
            int high = ob.getRectangle().bottom + obstacleGap - (int) (1.5 * obstacleHeight);
            int low = ob.getRectangle().bottom + (int) (1.5 * obstacleHeight);


            tuzaklar.add(0, new Trap(high, low, 1,trapImg,tuzakBuyuklugu));


        }


    }
    public int getScore(){
        return score;
    }

    public void doTheMath(float speed){
        this.speed = speed;
        tuzakSayisi = (int) (speed) / 6;


/*
        if (obstacles.get((obstacleIndex)%3).getRectangle().bottom <= 0) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart,
                    obstacles.get(0).getRectangle().bottom + obstacleHeight + obstacleGap, playerGap,
                    obstacleImg));
            obstacles.remove(obstacles.size() - 1);
            obs--;
            int high = obstacles.get(0).getRectangle().bottom + obstacleGap - (int) (1.5 * obstacleHeight);
            int low = obstacles.get(0).getRectangle().bottom + (int) (1.5 * obstacleHeight);


            tuzaklar.add(0, new Trap(high, low, tuzakSayisi + 1,trapImg,tuzakBuyuklugu));
        }*/

        obsInd=(obstacleIndex)%3;


        if (obstacles.get(obsInd).getRectangle().bottom <= 0) {

            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.get(obsInd).yerDegistir(xStart,
                    obstacles.get((obstacleIndex+2)%3).getRectangle().bottom+obstacleGap+obstacleHeight);

            int high = obstacles.get(obsInd).getRectangle().bottom + obstacleGap - (int) (1.5 * obstacleHeight);
            int low = obstacles.get((obsInd)%3).getRectangle().bottom + (int) (1.5 * obstacleHeight);


            tuzaklar.add(0, new Trap(high, low, tuzakSayisi + 1,trapImg,tuzakBuyuklugu));

            obstacleIndex++;

            obs--;
        }

        if (obstacles.get( (obstacleIndex+obs)%3 ).getRectangle().top <= (Constants.SCREEN_HEIGHT / 4 )) {
            score++;
           obs++;
        }

/*
        if (obstacles.get(obstacles.size() - obs).getRectangle().top <= (Constants.SCREEN_HEIGHT / 4 )) {
            score++;
            obs++;
        }
*/
        if (tuzaklar.get(tuzaklar.size() - 1).getRectangle() <= 0) {
            tuzaklar.remove(tuzaklar.size() - 1);
        }

        o= new ArrayList(obstacles);
        t=new ArrayList(tuzaklar);

    }


    public void update() {



        for (Obstacle ob : o) {
                    ob.yukarıCıkar((speed));
                }

                for (Trap t : t) {
                    t.yukarıCıkar((speed));
                }


    }

    public void draw(Canvas canvas) {


        for (Obstacle ob : o) ob.draw(canvas);

        for (Trap t : t) t.draw(canvas);

        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);


    }


    public ArrayList<Rect> kirpilacakDortgenler(){

        for (Obstacle o:obstacles) {
                if(o.getRectangle().top < Constants.SCREEN_HEIGHT){
                    kirpilacak.add(o.getRectangle());
                    kirpilacak.add(o.getRectangle2());
                }
        }

        return kirpilacak;
    }



}
