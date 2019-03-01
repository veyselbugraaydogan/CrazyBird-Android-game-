package com.example.veyselburaaydoan.parachutermonkey;

import android.util.Log;

public class SecondaryThread extends Thread {

    public static final int MAX_FPS = 5;
    private double avarageFPS;

    private boolean running;
    private GamePanel gamePanel;

    public SecondaryThread(GamePanel gamePanel) {
        //super();
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running){this.running = running;}

    @Override
    public void run() {

        Log.v("SECONDARY THREAD", "STARTED");

        long startTime = 0;
        long timeMillis=1000/MAX_FPS;
        long waitTime;
        long frameCount=0;
        long totalTime=0;
        long targetTime=1000/MAX_FPS;

        while (running) {

            startTime = System.nanoTime();

            gamePanel.doTheMath();

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
