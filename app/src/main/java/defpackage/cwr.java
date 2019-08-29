package defpackage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.InputDeviceCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.Window.Callback;

/* renamed from: cwr reason: default package */
/* compiled from: DelegateViewCallbacks */
public final class cwr extends cwu {
    public final void onActivityResumed(Activity activity) {
        if (activity != null) {
            cwz a = cwz.a();
            if (activity != null) {
                activity.runOnUiThread(new Runnable(activity) {
                    final /* synthetic */ Activity a;

                    {
                        this.a = r2;
                    }

                    public final void run() {
                        View decorView = this.a.getWindow().getDecorView();
                        if (decorView instanceof ViewGroup) {
                            cwz.b(cwz.this, (ViewGroup) decorView, this.a);
                        }
                    }
                });
            }
            Window window = activity.getWindow();
            if (window != null) {
                Callback callback = window.getCallback();
                if (!(callback instanceof cwy)) {
                    window.setCallback(new cwy(callback) {
                        public final void a() {
                            Handler handler = cxi.a().a;
                            if (handler != null) {
                                Message obtainMessage = handler.obtainMessage();
                                obtainMessage.what = 512;
                                obtainMessage.arg1 = InputDeviceCompat.SOURCE_DPAD;
                                Bundle bundle = new Bundle();
                                cxk.a();
                                bundle.putString("_view_name", cxk.c());
                                obtainMessage.setData(bundle);
                                handler.sendMessage(obtainMessage);
                            }
                        }
                    });
                }
            }
        }
    }
}
