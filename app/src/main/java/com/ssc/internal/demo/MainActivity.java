package com.ssc.internal.demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.ssc.internal.demo.utils.DataCleanManager;
import com.ssc.internal.demo.utils.InternalInterfaceManager;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by tangjinlong on 17/5/18.
 */
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private CountDownTimer mCountDownTimer;
    private LinearLayout mAdsViewGroup;
    private TextView mAdsLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        createTimer(5000);
        mCountDownTimer.start();

        Button button = new Button(this);
        button.setAllCaps(false);
        button.setText("Clear Data");
        button.setOnClickListener(view -> {
            try {
                DataCleanManager.cleanApplicationData(getApplicationContext());
                System.exit(0);
            }catch (Exception e){

            }
        });

        addContentView(button,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.TOP|Gravity.RIGHT));


    }

 
    public void onClickAdsBtn(View view) {
        switch (view.getId()) {
            case R.id.showCustomInappMsg:
               InternalInterfaceManager.getInstance().inAppMsgTriggerEvent("showCustomInappMsg");
                break;
            case R.id.showCustomInappMsgModal:
                InternalInterfaceManager.getInstance().inAppMsgTriggerEvent("showCustomInappMsgModal");

                break;
            case R.id.showCustomInappMsgImage:
                InternalInterfaceManager.getInstance().inAppMsgTriggerEvent("showCustomInappMsgImage");

                break;
            case R.id.showCustomInappMsgTopBanner:
                InternalInterfaceManager.getInstance().inAppMsgTriggerEvent("showCustomInappMsgTopBanner");

                break;

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemNavigationBar();
        }
    }

    private void hideSystemNavigationBar() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View view = this.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
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
    
    private void createTimer(final long milliseconds) {
        // Create the game timer, which counts down to the end of the level
        // and shows the "retry" button.
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        mAdsLoading = ((TextView) findViewById(R.id.ads_loading));
        mAdsViewGroup = (LinearLayout) findViewById(R.id.ads_group);

        mCountDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                mAdsLoading.setText("Ads loading: " + ((millisUnitFinished / 1000) + 1));
            }

            @Override
            public void onFinish() {
                mAdsLoading.setVisibility(View.GONE);
                mAdsViewGroup.setVisibility(View.VISIBLE);
            }
        };
    }
    

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Nativity onpause");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
   }

}
