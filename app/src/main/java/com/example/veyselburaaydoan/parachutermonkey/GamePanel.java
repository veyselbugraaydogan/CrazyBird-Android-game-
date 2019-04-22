package com.example.veyselburaaydoan.parachutermonkey;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread mainThread;
    private SecondaryThread secondaryThread;
    //private UpdaterThread updater;

    private SceneManager manager;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        mainThread =  new MainThread (getHolder(),this);
        secondaryThread =  new SecondaryThread (this);
        //updater =new UpdaterThread(this);
       // helper =  new SecondaryThread (this);

        manager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v("SURFACE CREATED", "SURFACE CREATED");
        mainThread = new MainThread (getHolder(),this);
        mainThread.setRunning(true);
        mainThread.start();
        secondaryThread =  new SecondaryThread (this);
        secondaryThread.setRunning(true);
        secondaryThread.start();
        SceneManager.getActiveScene().onCreate();
        //updater =  new UpdaterThread (this);
        //updater.setRunning(true);
        //updater.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v("SURFACE CHANGED", "SURFACE CHANGED");
        SceneManager.getActiveScene().onCreate();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v("SURFACE DESTROYED", "SURFACE DESTROYED");

        SceneManager.getActiveScene().onTerminate();

        boolean retry = true;

        while(retry){
            try {
                mainThread.setRunning(false);
                mainThread.join();
                secondaryThread.setRunning(false);
                secondaryThread.join();
                //updater.setRunning(false);
                //updater.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }


    }

    @Override
    public boolean onTouchEvent (MotionEvent event){

        manager.recieveTouch(event);

        return true;
        //return super.onTouchEvent(event);
    }


    public void update(){
        manager.update();
    }

    public void doTheMath(){
        manager.doTheMath();
    }

    public void draw(Canvas canvas){

        super.draw(canvas);
        manager.draw(canvas);

    }
}
