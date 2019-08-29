package com.alipay.mobile.nebulacore.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5Log;

public class H5Alert implements OnCancelListener, OnClickListener {
    public static final int INDEX_CANCEL = 3;
    public static final int INDEX_LEFT = 0;
    public static final int INDEX_MIDDLE = 1;
    public static final int INDEX_RIGHT = 2;
    public static final String TAG = "H5Alert";
    private Activity a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private boolean g = true;
    private H5AlertListener h;
    private AlertDialog i;

    public interface H5AlertListener {
        void onCancel(H5Alert h5Alert);

        void onClick(H5Alert h5Alert, int i);
    }

    public H5Alert(Activity activity) {
        this.a = activity;
    }

    public H5Alert title(String title) {
        this.b = title;
        return this;
    }

    public H5Alert message(String message) {
        this.c = message;
        return this;
    }

    public H5Alert cancelable(boolean cancelable) {
        this.g = cancelable;
        return this;
    }

    public H5Alert buttons(String[] labels) {
        if (labels == null || labels.length == 0) {
            H5Log.w(TAG, "no buttons set.");
        } else {
            switch (labels.length) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    this.f = labels[2];
                    break;
            }
            this.e = labels[1];
            this.d = labels[0];
        }
        return this;
    }

    public H5Alert listener(H5AlertListener listener) {
        this.h = listener;
        return this;
    }

    public H5Alert show() {
        if (this.a == null || this.a.isFinishing()) {
            H5Log.d(TAG, "activity is finishing");
            this.i = null;
        } else if (!TextUtils.isEmpty(this.b) || !TextUtils.isEmpty(this.c)) {
            Builder builder = new Builder(this.a);
            if (!TextUtils.isEmpty(this.b)) {
                builder.setTitle(this.b);
            }
            if (!TextUtils.isEmpty(this.c)) {
                builder.setMessage(this.c);
            }
            if (!TextUtils.isEmpty(this.d)) {
                if (VERSION.SDK_INT <= 14) {
                    builder.setPositiveButton(this.d, this);
                } else {
                    builder.setNegativeButton(this.d, this);
                }
            }
            if (!TextUtils.isEmpty(this.e)) {
                builder.setNeutralButton(this.e, this);
            }
            if (!TextUtils.isEmpty(this.f)) {
                if (VERSION.SDK_INT <= 14) {
                    builder.setNegativeButton(this.f, this);
                } else {
                    builder.setPositiveButton(this.f, this);
                }
            }
            builder.setCancelable(this.g);
            builder.setOnCancelListener(this);
            this.i = builder.show();
        } else {
            H5Log.w(TAG, "empty title and message");
        }
        return this;
    }

    public void dismiss() {
        if (this.i != null && this.i.isShowing() && this.a != null && !this.a.isFinishing()) {
            try {
                this.i.dismiss();
            } catch (Throwable th) {
                H5Log.e((String) TAG, (String) "dismiss exception");
            }
            this.i = null;
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        int index = 2;
        if (which == -3) {
            index = 1;
        } else if ((which == -1 && VERSION.SDK_INT <= 14) || (which == -2 && VERSION.SDK_INT > 14)) {
            index = 0;
        }
        dismiss();
        if (this.h != null) {
            this.h.onClick(this, index);
        }
    }

    public void onCancel(DialogInterface dialog) {
        if (this.h != null) {
            this.h.onCancel(this);
        }
    }
}
