package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.sharebike.model.RideState;

/* renamed from: ehv reason: default package */
/* compiled from: AndroidNotifyPolicy */
public final class ehv extends ehw {
    private static ehv a;

    private ehv() {
    }

    public static synchronized ehv a() {
        ehv ehv;
        synchronized (ehv.class) {
            try {
                if (a == null) {
                    a = new ehv();
                }
                ehv = a;
            }
        }
        return ehv;
    }

    public final void a(RideState rideState, String str) {
        if (rideState != null) {
            if (rideState.status != 1 && rideState.status != 2) {
                eac.a().a(6);
            } else if (!a(str) && !b()) {
                String a2 = eay.a(R.string.notification_title_keep_record);
                String a3 = eay.a(R.string.notification_content_riding_record);
                eac a4 = eac.a();
                ead a5 = ead.a(6);
                a5.c = a2;
                a5.d = a3;
                a4.a(a5);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean b() {
        try {
            dfm dfm = (dfm) ank.a(dfm.class);
            StringBuilder sb = new StringBuilder("FootNaviStateTunnel.getFootNaviState()=");
            sb.append(egc.a());
            eao.d("wbsww", sb.toString());
            if (dfm != null) {
                if (dfm.b() || egc.a()) {
                    return true;
                }
                return false;
            }
        } catch (Throwable th) {
            AMapLog.e(ehw.class.getName(), "Happen error during get status of drive navigation");
            th.printStackTrace();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        aww aww = (aww) a.a.a(aww.class);
        String str2 = null;
        Object simpleName = aww != null ? aww.a().a(1).getSimpleName() : null;
        avi avi = (avi) a.a.a(avi.class);
        Object simpleName2 = avi != null ? avi.c().a(1).getSimpleName() : null;
        atb atb = (atb) a.a.a(atb.class);
        Object simpleName3 = atb != null ? atb.a().a(2).getSimpleName() : null;
        avo avo = (avo) a.a.a(avo.class);
        if (avo != null) {
            str2 = avo.a().a(1).getSimpleName();
        }
        if (str.equals(simpleName2) || str.equals(simpleName3) || str.equals(str2) || str.equals(simpleName) || str.equals(NodeAlertDialogPage.class.getSimpleName())) {
            return true;
        }
        return false;
    }
}
