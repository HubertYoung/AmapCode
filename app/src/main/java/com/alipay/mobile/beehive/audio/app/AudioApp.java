package com.alipay.mobile.beehive.audio.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.beehive.audio.activity.GeneralAudioPlayActivity;
import com.alipay.mobile.beehive.audio.utils.BundleLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.MicroApplication;

public class AudioApp extends ActivityApplication {
    public static final String ACTION_TYPE_AUDIO_DETAIL = "toAudioDetail";
    public static final String KEY_ACTION_TYPE = "actionType";
    private BundleLogger mLogger = BundleLogger.getLogger(AudioApp.class);
    private Bundle params;

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.params = bundle;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        dispatch();
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        this.params = bundle;
        dispatch();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    private void dispatch() {
        if (this.params == null) {
            this.mLogger.w("Params invalid.");
            return;
        }
        String actionType = this.params.getString("actionType");
        if (TextUtils.equals(ACTION_TYPE_AUDIO_DETAIL, actionType)) {
            Intent intent = new Intent(LauncherApplicationAgent.getInstance().getApplicationContext(), GeneralAudioPlayActivity.class);
            intent.putExtras(this.params);
            getMicroApplicationContext().startActivity((MicroApplication) this, intent);
            return;
        }
        this.mLogger.w("Unsupported action type which = " + actionType);
    }
}
