package defpackage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/* renamed from: cws reason: default package */
/* compiled from: ForegroundAndBackgroundStatusCallbacks */
public final class cws extends cwu {
    public final void onActivityResumed(Activity activity) {
        if (activity != null) {
            cxk.a().b = activity;
            String simpleName = activity.getClass().getSimpleName();
            Handler handler = cxi.a().a;
            if (handler != null) {
                Message obtainMessage = handler.obtainMessage();
                obtainMessage.what = 512;
                obtainMessage.arg1 = 514;
                Bundle bundle = new Bundle();
                StringBuilder sb = new StringBuilder();
                sb.append(simpleName);
                sb.append("$");
                cxk.a();
                sb.append(cxk.c());
                bundle.putString("_view_name", sb.toString());
                obtainMessage.setData(bundle);
                handler.sendMessage(obtainMessage);
            }
        }
    }

    public final void onActivityPaused(Activity activity) {
        if (activity != null) {
            Handler handler = cxi.a().a;
            if (handler != null) {
                String simpleName = activity.getClass().getSimpleName();
                Message obtainMessage = handler.obtainMessage();
                obtainMessage.what = 512;
                obtainMessage.arg1 = 515;
                Bundle bundle = new Bundle();
                StringBuilder sb = new StringBuilder();
                sb.append(simpleName);
                sb.append("$");
                cxk.a();
                sb.append(cxk.c());
                bundle.putString("_view_name", sb.toString());
                obtainMessage.setData(bundle);
                handler.sendMessage(obtainMessage);
            }
        }
    }
}
