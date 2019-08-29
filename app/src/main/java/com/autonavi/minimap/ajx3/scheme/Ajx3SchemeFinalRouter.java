package com.autonavi.minimap.ajx3.scheme;

import android.content.Context;
import android.net.Uri;
import com.autonavi.annotation.Router;
import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;

@Router({"ajx_final_scheme"})
public class Ajx3SchemeFinalRouter extends esk {
    static final String NEW_HOST_AJX = "ajx_final_scheme";
    private Callback<Boolean> mResultCallback;

    public void setResultCallback(Callback<Boolean> callback) {
        this.mResultCallback = callback;
    }

    public boolean start(ese ese) {
        Context context;
        Uri uri = ese.a;
        if ("ajxdebug".equals(uri.getHost())) {
            return false;
        }
        String str = null;
        if (this.mWingContext != null) {
            context = this.mWingContext.b;
        } else {
            context = null;
        }
        if (context == null || !Ajx3SchemeHelper.checkValid(uri)) {
            return false;
        }
        if (ese.c() != null) {
            str = ese.c().getString("ajxData");
        }
        Uri mergeParam2Data = Ajx3SchemeHelper.mergeParam2Data(uri, str);
        if (Ajx3SchemeHelper.tryOpenLocalAjx(context, mergeParam2Data, this)) {
            if (this.mResultCallback != null) {
                this.mResultCallback.callback(Boolean.TRUE);
            }
            return true;
        }
        Ajx3RouterManager.getInstance().updateLogStatId();
        Ajx3ActionLogUtil.actionLogAjxWeb(18, 0, "Show Remote Scheme Loading", ese.a.toString(), true, Ajx3RouterManager.getInstance().getLogStatId(), Ajx3ActionLogUtil.generateSchemeAjxCheckRequestAndroidExt());
        return Ajx3SchemeHelper.updateAjxByScheme(mergeParam2Data, this, this.mResultCallback);
    }
}
