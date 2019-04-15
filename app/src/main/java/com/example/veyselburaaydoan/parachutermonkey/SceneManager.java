package com.example.veyselburaaydoan.parachutermonkey;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class SceneManager {

    private static ArrayList<Scene> scenes=new ArrayList<>();
    private static int ACTİVE_SCENE;

    public  SceneManager(){
        ACTİVE_SCENE = 3;
        scenes.add(new GameplayScene(0));
        scenes.add(new SettingsScene(1));
        scenes.add(new CreditsScene(2));
        scenes.add(new InitializationScreenScene(3));
        scenes.add(new IntroductionScene(4));
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(ACTİVE_SCENE).recieveTouch(event);
    }

    public void update(){
        scenes.get(ACTİVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTİVE_SCENE).draw(canvas);
    }

    public void doTheMath() {
        scenes.get(ACTİVE_SCENE).doTheMath();
    }

    public static void setScene(int terminatedScene,int createdScene){
        scenes.get(terminatedScene).onTerminate();
        scenes.get(createdScene).onCreate();
        ACTİVE_SCENE = createdScene;
    }

    public static GameplayScene getGamePlayScene(){
        return (GameplayScene) scenes.get(0);
    }

    public static void setActiveScene(int no){
        ACTİVE_SCENE = no;
    }
}
