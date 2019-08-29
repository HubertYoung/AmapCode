package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;

/* renamed from: bct reason: default package */
/* compiled from: SearchProgressDlgUtil */
public final class bct {
    public static ProgressDlg a;

    @Deprecated
    public static void a(String str) {
        String str2;
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (str == null || "".equals(str)) {
            str2 = resources.getString(R.string.searching);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(resources.getString(R.string.searching));
            sb.append("\"");
            sb.append(str);
            sb.append("\"");
            str2 = sb.toString();
        }
        a(str2, null);
    }

    public static void a(String str, final bbm bbm) {
        if (a != null) {
            a.dismiss();
        }
        if (a == null && AMapAppGlobal.getTopActivity() != null) {
            a = new ProgressDlg(AMapAppGlobal.getTopActivity(), str, "");
        }
        a.setCanceledOnTouchOutside(false);
        a.setMessage(str);
        a.setCancelable(true);
        a.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                adz adz = (adz) a.a.a(adz.class);
                if (adz != null) {
                    ady b = adz.b();
                    if (b != null) {
                        b.a();
                    }
                }
                if (bbm != null) {
                    bbm.a();
                }
            }
        });
        a.show();
        a.show();
    }

    @Deprecated
    public static void a() {
        adz adz = (adz) a.a.a(adz.class);
        if (adz != null) {
            ady b = adz.b();
            if (b != null) {
                b.a();
            }
        }
        if (a != null) {
            a.dismiss();
        }
    }
}
