package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.autonavi.minimap.ajx3.R;

public class ToastUtils {
    private static ToastImpl toastImpl;

    static class ToastImpl {
        private static Handler handler = new Handler(Looper.getMainLooper());
        /* access modifiers changed from: private */
        public final LayoutInflater inflater;
        /* access modifiers changed from: private */
        public final Toast toast;

        ToastImpl(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.toast = new Toast(context);
            this.toast.setView(this.inflater.inflate(R.layout.toast_content_default, null));
        }

        /* access modifiers changed from: private */
        public void showToast(final CharSequence charSequence, final int i) {
            handler.post(new Runnable() {
                public void run() {
                    ToastImpl.this.toast.setDuration(i);
                    View inflate = ToastImpl.this.inflater.inflate(R.layout.toast_content_default, null);
                    ToastImpl.this.toast.setView(inflate);
                    ((TextView) inflate.findViewById(R.id.text_toast)).setText(charSequence);
                    ToastImpl.this.toast.show();
                }
            });
        }
    }

    public static void init(Context context) {
        toastImpl = new ToastImpl(context);
    }

    public static void showToast(String str, int i) {
        if (toastImpl == null) {
            throw new RuntimeException("ToastUtils init failed");
        }
        toastImpl.showToast(str, i);
    }

    public static Context getContext() {
        if (toastImpl != null) {
            return toastImpl.inflater.getContext();
        }
        return null;
    }
}
