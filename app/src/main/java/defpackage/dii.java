package defpackage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;

/* renamed from: dii reason: default package */
/* compiled from: SearchCallbackProgressDlgUtil */
public final class dii {
    private static ProgressDlg a;

    public static void a(String str, final adx adx, Context context) {
        String str2;
        if (a != null) {
            a.dismiss();
        }
        Resources resources = context.getResources();
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
        if (a == null) {
            a = new ProgressDlg(AMapAppGlobal.getTopActivity(), str2, "");
        }
        a.setCanceledOnTouchOutside(false);
        a.setMessage(str2);
        a.setCancelable(true);
        a.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (adx != null) {
                    adx.a();
                }
            }
        });
        a.show();
        a.show();
    }

    public static void a() {
        if (a != null) {
            a.dismiss();
        }
    }
}
