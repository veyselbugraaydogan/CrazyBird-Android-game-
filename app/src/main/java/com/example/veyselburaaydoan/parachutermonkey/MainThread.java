package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    public static final int MAX_FPS = 30;
    private double avarageFPS;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    private GamePanel gamePanel;
    public static Canvas canvas;


    public  void setRunning(boolean running){
        this.running = running;
    }


    public MainThread(SurfaceHolder surfaceHolder,GamePanel gamePanel){
        super();
        this.surfaceHolder=surfaceHolder;
        this.gamePanel=gamePanel;


    }

    @Override
    public void run(){
        Log.v("MAIN THREAD", "STARTED");
        long startTime = 0;
        long timeMillis=1000/MAX_FPS;
        long waitTime;
        long frameCount=0;
        long totalTime=0;
        long targetTime=1000/MAX_FPS;

        while (running) {

            startTime = System.nanoTime();
            canvas = null;


            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) this.sleep(waitTime);
            } catch (Exception e) {

                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == MAX_FPS) {

                avarageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.v("AvarageFps = ", avarageFPS + "");

            }
        }
    }

}