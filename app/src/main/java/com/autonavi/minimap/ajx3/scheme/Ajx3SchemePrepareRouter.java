package com.autonavi.minimap.ajx3.scheme;

import android.content.Context;
import android.net.Uri;
import com.autonavi.annotation.Router;

@Router({"ajx_prepare_scheme"})
public class Ajx3SchemePrepareRouter extends esk {
    static final String NEW_HOST_AJX = "ajx_prepare_scheme";

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
        return Ajx3SchemeHelper.tryOpenLocalAjx(context, Ajx3SchemeHelper.mergeParam2Data(uri, str), this);
    }
}
