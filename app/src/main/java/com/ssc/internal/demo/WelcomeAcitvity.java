package com.ssc.internal.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by tangjinlong on 16/5/19.
 */
public class WelcomeAcitvity extends Activity /*implements AndroidUUIDListener */{
    private static String TAG = "WelcomeAcitvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"activity on create call------");
//        boolean bTestAds = AdsTools.isTestAds(this);
//        if (bTestAds){
//            Intent intent = new Intent(this,AdsUnitIDActivity.class);
//            startActivity(intent);
//        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
//        }

        finish();
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            hideSystemNavigationBar();
    }

    private void hideSystemNavigationBar() {
    if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
        View view = this.getWindow().getDecorView();
        view.setSystemUiVisibility(View.GONE);
    } else if (Build.VERSION.SDK_INT >= 19) {
        View decorView = getWindow().getDecorView();
        int uiOptions =   View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
}
