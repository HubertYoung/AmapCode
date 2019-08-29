package com.alipay.mobile.framework.app.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.mobile.framework.R;
import com.alipay.mobile.quinox.utils.TraceLogger;

public class DialogHelper {
    protected static final String TAG = "DialogHelper";
    /* access modifiers changed from: private */
    public Activity a;
    /* access modifiers changed from: private */
    public AlertDialog b;

    public class APGenericProgressDialog extends AlertDialog {
        private ProgressBar a;
        private TextView b;
        private CharSequence c;
        private boolean d;
        private boolean e;

        public APGenericProgressDialog(Context context) {
            super(context);
        }

        public APGenericProgressDialog(Context context, int theme) {
            super(context, theme);
        }

        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.generic_progress_dialog);
            this.a = (ProgressBar) findViewById(16908301);
            this.b = (TextView) findViewById(R.id.message);
            a();
            setIndeterminate(this.d);
        }

        private void a() {
            int i = 8;
            this.b.setText(this.c);
            if (this.c == null || "".equals(this.c)) {
                this.b.setVisibility(8);
            }
            ProgressBar progressBar = this.a;
            if (this.e) {
                i = 0;
            }
            progressBar.setVisibility(i);
        }

        public void setMessage(CharSequence message) {
            this.c = message;
        }

        public void setProgressVisiable(boolean progressVisiable) {
            this.e = progressVisiable;
        }

        public void setIndeterminate(boolean indeterminate) {
            if (this.a != null) {
                this.a.setIndeterminate(indeterminate);
            } else {
                this.d = indeterminate;
            }
        }
    }

    public DialogHelper(Activity activity) {
        this.a = activity;
    }

    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener) {
        alert(title, msg, positive, positiveListener, negative, negativeListener, Boolean.valueOf(false));
    }

    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener, Boolean isCanceledOnTouchOutside) {
        alert(title, msg, positive, positiveListener, negative, negativeListener, Boolean.valueOf(false), Boolean.valueOf(false));
    }

    public void alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener, Boolean isCanceledOnTouchOutside, Boolean cancelable) {
        dismissProgressDialog();
        final String str = title;
        final String str2 = msg;
        final String str3 = positive;
        final OnClickListener onClickListener = positiveListener;
        final String str4 = negative;
        final OnClickListener onClickListener2 = negativeListener;
        final Boolean bool = isCanceledOnTouchOutside;
        final Boolean bool2 = cancelable;
        this.a.runOnUiThread(new Runnable() {
            public void run() {
                if (DialogHelper.this.a != null && !DialogHelper.this.a.isFinishing()) {
                    Builder builder = new Builder(DialogHelper.this.a);
                    if (str != null) {
                        builder.setTitle(str);
                    }
                    if (str2 != null) {
                        builder.setMessage(str2);
                    }
                    if (str3 != null) {
                        builder.setPositiveButton(str3, onClickListener);
                    }
                    if (str4 != null) {
                        builder.setNegativeButton(str4, onClickListener2);
                    }
                    DialogHelper.this.b = builder.create();
                    try {
                        DialogHelper.this.b.show();
                        DialogHelper.this.b.setCanceledOnTouchOutside(bool.booleanValue());
                        DialogHelper.this.b.setCancelable(bool2.booleanValue());
                    } catch (Exception e) {
                        TraceLogger.w((String) DialogHelper.TAG, "DialogHelper.alert(): exception=" + e);
                        DialogHelper.this.b = null;
                    }
                }
            }
        });
    }

    public void toast(final String msg, final int period) {
        this.a.runOnUiThread(new Runnable() {
            public void run() {
                if (DialogHelper.this.a != null) {
                    Context context = DialogHelper.this.a;
                    Toast toast = new Toast(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.transient_notification, null);
                    ((TextView) view.findViewById(16908299)).setText(msg);
                    toast.setView(view);
                    toast.setDuration(period);
                    toast.setGravity(17, 0, 0);
                    try {
                        toast.show();
                    } catch (Exception e) {
                        TraceLogger.w((String) DialogHelper.TAG, "DialogHelper.toast(): exception=" + e);
                    }
                }
            }
        });
    }

    public void showProgressDialog(boolean showProgressBar, String msg) {
        showProgressDialog(msg, true, null, showProgressBar);
    }

    public void showProgressDialog(String msg) {
        showProgressDialog(msg, true, null, true);
    }

    public void showProgressDialog(String msg, boolean cancelable, OnCancelListener cancelListener, boolean showProgressBar) {
        dismissProgressDialog();
        final String str = msg;
        final boolean z = showProgressBar;
        final boolean z2 = cancelable;
        final OnCancelListener onCancelListener = cancelListener;
        this.a.runOnUiThread(new Runnable() {
            public void run() {
                if (DialogHelper.this.a != null && !DialogHelper.this.a.isFinishing()) {
                    DialogHelper.this.b = new APGenericProgressDialog(DialogHelper.this.a);
                    DialogHelper.this.b.setMessage(str);
                    ((APGenericProgressDialog) DialogHelper.this.b).setProgressVisiable(z);
                    DialogHelper.this.b.setCancelable(z2);
                    DialogHelper.this.b.setOnCancelListener(onCancelListener);
                    DialogHelper.this.b.setCanceledOnTouchOutside(false);
                    try {
                        DialogHelper.this.b.show();
                    } catch (Throwable e) {
                        TraceLogger.e((String) DialogHelper.TAG, "DialogHelper.showProgressDialog()" + e);
                        DialogHelper.this.b = null;
                    }
                }
            }
        });
    }

    public void dismissProgressDialog() {
        this.a.runOnUiThread(new Runnable() {
            public void run() {
                if (DialogHelper.this.b != null && DialogHelper.this.b.isShowing() && !DialogHelper.this.a.isFinishing()) {
                    try {
                        DialogHelper.this.b.dismiss();
                    } catch (Throwable e) {
                        TraceLogger.w((String) DialogHelper.TAG, "DialogHelper.dismissProgressDialog(): exception=" + e);
                    } finally {
                        DialogHelper.this.b = null;
                    }
                }
            }
        });
    }
}
