package com.uc.webview.export.extension;

import android.content.Context;
import com.autonavi.ae.bl.map.IMapPageConstant;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.utility.Log;
import java.util.Map;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
final class g extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ Callable c;
    final /* synthetic */ Map d;

    g(Context context, String str, Callable callable, Map map) {
        this.a = context;
        this.b = str;
        this.c = callable;
        this.d = map;
    }

    public final void run() {
        while (!((Boolean) SDKFactory.invoke(10010, new Object[0])).booleanValue()) {
            try {
                Thread.sleep(200);
            } catch (Exception unused) {
            }
        }
        if (((Boolean) SDKFactory.invoke(10003, Long.valueOf(1))).booleanValue()) {
            Log.i("tag_test_log", "force system webview, don't download uc player");
            return;
        }
        if (((Boolean) SDKFactory.invoke(10003, Long.valueOf(IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE))).booleanValue()) {
            Log.i("tag_test_log", "use ucmobile apollo, don't download uc player");
        } else if (!SDKFactory.t) {
            Log.i("tag_test_log", "sUseUCPlayer is false, don't download uc player");
        } else {
            try {
                UCCore.a(this.a, this.b, this.c, this.d);
            } catch (Throwable unused2) {
            }
        }
    }
}
