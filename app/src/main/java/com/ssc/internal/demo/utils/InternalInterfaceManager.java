package com.ssc.internal.demo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingImpressionListener;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.CampaignMetadata;
import com.google.firebase.inappmessaging.model.InAppMessage;


/**
 * Created by tangjinlong on 16/4/15.
 */
public class InternalInterfaceManager {
    private final static String TAG = "InternalManager";
    public final static int REQ_CODE_SELECT_PIC = 0x0000A2;
    public final static int REQ_CODE_APP_NOTIFICATION = 0x0000A3;
    private static InternalInterfaceManager instance;

    private boolean bFIAMListenerRegistered = false;


    private InternalInterfaceManager() {

    }

    public static InternalInterfaceManager getInstance() {
        if (instance == null)
            synchronized (InternalInterfaceManager.class) {
                if (instance == null)
                    instance = new InternalInterfaceManager();
            }

        return instance;
    }


    private void registerFIAMListeners() {
        if (!bFIAMListenerRegistered) {
            //(1)
            FirebaseInAppMessaging.getInstance().addClickListener(new FirebaseInAppMessagingClickListener() {

                @Override
                public void messageClicked(@NonNull InAppMessage inAppMessage, @NonNull Action action) {
                    Log.i(TAG, "FIAM messageClicked");
                    // Determine which URL the user clicked
                    String url = action.getActionUrl();
                    Log.i(TAG, "FIAM messageClicked:Action==>url = " + url);
                    if (action.getButton() != null) {
                        Log.i(TAG, "FIAM messageClicked:Action==> button.text = "
                                + action.getButton().getText().getText());
                    }

                    // Get general information about the campaign
                    CampaignMetadata metadata = inAppMessage.getCampaignMetadata();

                    Log.i(TAG, "FIAM messageClicked:CampaignMetadata==> metadata:id = "
                            + metadata.getCampaignId() + ",name = " + metadata.getCampaignName());
                }
            });
            //(2)
            FirebaseInAppMessaging.getInstance().addImpressionListener(new FirebaseInAppMessagingImpressionListener() {
                @Override
                public void impressionDetected(@NonNull InAppMessage inAppMessage) {
                    Log.i(TAG, "FIAM impressionDetected");
                    CampaignMetadata metadata = inAppMessage.getCampaignMetadata();

                }
            });
            bFIAMListenerRegistered = true;
        }
    }

    public void inAppMsgTriggerEvent(String triggerEvent) {
        if (TextUtils.isEmpty(triggerEvent)) {
            Log.e(TAG, "Trigger event can NOT be NULL");
            return;
        }
        registerFIAMListeners();
        FirebaseInAppMessaging.getInstance().triggerEvent(triggerEvent);
    }
}