package com.example.veyselburaaydoan.parachutermonkey;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.CURRENT_CONTEXT = this;

        Constants.tuzakYazisi = getResources().getString(R.string.tuzaklara_dokunma);
        Constants.sagaGit = getResources().getString(R.string.saga_gitmek_icin);
        Constants.solagit = getResources().getString(R.string.sola_gitmek_icin);

        int pixel = 120;
        final float scale = getResources().getDisplayMetrics().density;
        Constants.DIP = (int) (pixel* scale + 0.5f);
        Constants.MAIN_ACTIVITY=this;
        Constants.BUYUK_YAZI=Constants.SCREEN_WIDTH/10;
        Constants.KUCUK_YAZI=Constants.SCREEN_WIDTH/20;
        setContentView(new GamePanel(this));


    }
}
