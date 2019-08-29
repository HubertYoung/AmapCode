package com.ali.user.mobile.base.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.ui.widget.APGenericProgressDialog;
import com.ali.user.mobile.ui.widget.APNormalPopDialog;
import com.ali.user.mobile.ui.widget.APNoticePopDialog;
import com.ali.user.mobile.ui.widget.APNoticePopDialog.OnClickNegativeListener;
import com.ali.user.mobile.ui.widget.APNoticePopDialog.OnClickPositiveListener;
import com.ali.user.mobile.ui.widget.toast.SimpleToast;

public class DialogHelper {
    /* access modifiers changed from: private */
    public final Activity a;
    /* access modifiers changed from: private */
    public AlertDialog b;

    public DialogHelper(Activity activity) {
        this.a = activity;
    }

    public final void a(String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2, Boolean bool) {
        a();
        Activity activity = this.a;
        final String str5 = str;
        final String str6 = str2;
        final String str7 = str3;
        final String str8 = str4;
        final OnClickListener onClickListener3 = onClickListener;
        final OnClickListener onClickListener4 = onClickListener2;
        final Boolean bool2 = bool;
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                if (DialogHelper.this.a != null && !DialogHelper.this.a.isFinishing()) {
                    final APNoticePopDialog aPNoticePopDialog = new APNoticePopDialog(DialogHelper.this.a, str5, str6, str7, str8);
                    if (onClickListener3 != null) {
                        aPNoticePopDialog.a((OnClickPositiveListener) new OnClickPositiveListener() {
                            public void onClick() {
                                onClickListener3.onClick(aPNoticePopDialog, 1);
                            }
                        });
                    }
                    if (onClickListener4 != null) {
                        aPNoticePopDialog.a((OnClickNegativeListener) new OnClickNegativeListener() {
                            public final void a() {
                                onClickListener4.onClick(aPNoticePopDialog, 0);
                            }
                        });
                    }
                    try {
                        aPNoticePopDialog.show();
                        aPNoticePopDialog.setCanceledOnTouchOutside(bool2.booleanValue());
                        aPNoticePopDialog.setCancelable(false);
                    } catch (Exception e2) {
                        AliUserLog.d("DialogHelper", "DialogHelper.alert(): exception=".concat(String.valueOf(e2)));
                    }
                }
            }
        };
        activity.runOnUiThread(r0);
    }

    public final void a(String str, String str2, String str3, String str4, APNormalPopDialog.OnClickPositiveListener onClickPositiveListener, String str5, APNormalPopDialog.OnClickNegativeListener onClickNegativeListener, Boolean bool) {
        a();
        Activity activity = this.a;
        final String str6 = str;
        final String str7 = str2;
        final String str8 = str3;
        final String str9 = str4;
        final APNormalPopDialog.OnClickPositiveListener onClickPositiveListener2 = onClickPositiveListener;
        final String str10 = str5;
        final APNormalPopDialog.OnClickNegativeListener onClickNegativeListener2 = onClickNegativeListener;
        final Boolean bool2 = bool;
        AnonymousClass2 r0 = new Runnable() {
            public void run() {
                if (DialogHelper.this.a != null && !DialogHelper.this.a.isFinishing()) {
                    APNormalPopDialog aPNormalPopDialog = new APNormalPopDialog(DialogHelper.this.a, str6, str7, str8);
                    if (!TextUtils.isEmpty(str9)) {
                        aPNormalPopDialog.a().setText(str9);
                    }
                    if (onClickPositiveListener2 != null) {
                        aPNormalPopDialog.a(onClickPositiveListener2);
                    }
                    if (!TextUtils.isEmpty(str10)) {
                        aPNormalPopDialog.a().setText(str10);
                    }
                    if (onClickNegativeListener2 != null) {
                        aPNormalPopDialog.a(onClickNegativeListener2);
                    }
                    try {
                        aPNormalPopDialog.setCanceledOnTouchOutside(bool2.booleanValue());
                        aPNormalPopDialog.setCancelable(false);
                        aPNormalPopDialog.show();
                    } catch (Exception e2) {
                        AliUserLog.d("DialogHelper", "APNormalPopDialog.alert(): exception=".concat(String.valueOf(e2)));
                    }
                }
            }
        };
        activity.runOnUiThread(r0);
    }

    public final void a(final String str, final int i) {
        this.a.runOnUiThread(new Runnable() {
            public void run() {
                if (DialogHelper.this.a != null && !DialogHelper.this.a.isFinishing() && !TextUtils.isEmpty(str)) {
                    try {
                        SimpleToast.a(DialogHelper.this.a, 0, str, i).show();
                    } catch (Exception e) {
                        AliUserLog.d("DialogHelper", "DialogHelper.toast(): exception=".concat(String.valueOf(e)));
                    }
                }
            }
        });
    }

    public final void a(String str, boolean z, OnCancelListener onCancelListener, boolean z2) {
        a();
        Activity activity = this.a;
        final String str2 = str;
        final boolean z3 = z2;
        final boolean z4 = z;
        final OnCancelListener onCancelListener2 = onCancelListener;
        AnonymousClass4 r1 = new Runnable() {
            public void run() {
                if (DialogHelper.this.a != null && !DialogHelper.this.a.isFinishing()) {
                    DialogHelper.this.b = new APGenericProgressDialog(DialogHelper.this.a);
                    DialogHelper.this.b.setMessage(str2);
                    ((APGenericProgressDialog) DialogHelper.this.b).a(z3);
                    DialogHelper.this.b.setCancelable(z4);
                    DialogHelper.this.b.setOnCancelListener(onCancelListener2);
                    DialogHelper.this.b.setCanceledOnTouchOutside(false);
                    try {
                        DialogHelper.this.b.show();
                    } catch (Exception e2) {
                        AliUserLog.a((String) "DialogHelper", (Throwable) e2);
                        DialogHelper.this.b = null;
                    }
                }
            }
        };
        activity.runOnUiThread(r1);
    }

    public final void a() {
        this.a.runOnUiThread(new Runnable() {
            public void run() {
                if (DialogHelper.this.b != null && DialogHelper.this.b.isShowing() && DialogHelper.this.a != null && !DialogHelper.this.a.isFinishing()) {
                    try {
                        DialogHelper.this.b.dismiss();
                    } catch (Throwable th) {
                        DialogHelper.this.b = null;
                        throw th;
                    }
                    DialogHelper.this.b = null;
                }
            }
        });
    }
}
