package com.ali.user.mobile.base.helper;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import com.ali.user.mobile.ui.widget.APNormalPopDialog.OnClickNegativeListener;
import com.ali.user.mobile.ui.widget.APNormalPopDialog.OnClickPositiveListener;

public class ActivityUIHelper {
    static final String a = "ActivityUIHelper";
    private final DialogHelper b;

    public ActivityUIHelper(Activity activity) {
        this.b = new DialogHelper(activity);
    }

    public final void a() {
        this.b.a();
    }

    public final void a(String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2) {
        this.b.a(str, str2, str3, onClickListener, str4, onClickListener2, Boolean.FALSE);
    }

    public final void a(String str, String str2, String str3, String str4, OnClickPositiveListener onClickPositiveListener, String str5, OnClickNegativeListener onClickNegativeListener) {
        this.b.a(str, str2, str3, str4, onClickPositiveListener, str5, onClickNegativeListener, Boolean.FALSE);
    }

    public final void a(String str, int i) {
        this.b.a(str, i);
    }

    public final void a(String str) {
        this.b.a(str, false, null, true);
    }

    public final void b() {
        this.b.a();
    }
}
