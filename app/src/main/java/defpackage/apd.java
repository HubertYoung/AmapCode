package defpackage;

import android.app.Activity;
import android.os.Looper;
import android.text.TextUtils;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;

/* renamed from: apd reason: default package */
/* compiled from: ProgressDlgUtil */
public final class apd {
    private static ProgressDlg a;

    public static void a() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            c();
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    apd.c();
                }
            });
        }
    }

    public static void b() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            d();
        } else {
            aho.a(new Runnable() {
                public final void run() {
                    apd.d();
                }
            });
        }
    }

    static void c() {
        Activity a2 = AMapPageUtil.getMVPActivityContext().a();
        if (a2 != null) {
            String string = a2.getResources().getString(R.string.loading);
            if (a == null) {
                ProgressDlg progressDlg = new ProgressDlg(a2, null);
                a = progressDlg;
                progressDlg.setCanceledOnTouchOutside(false);
            }
            if (!TextUtils.isEmpty(string)) {
                a.setMessage(string);
            }
            if (a != null && !a2.isFinishing()) {
                a.show();
            }
        }
    }

    static void d() {
        if (a != null) {
            a.dismiss();
            a = null;
        }
    }
}
