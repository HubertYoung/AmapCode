package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;

/* renamed from: dly reason: default package */
/* compiled from: FaultTolerantIntentInterceptor */
public final class dly implements dlh {
    final Activity a;
    Callback<Boolean> b;

    public dly(Activity activity) {
        this.a = activity;
    }

    public final boolean a(Intent intent) {
        if (this.b != null) {
            this.b.callback(Boolean.FALSE);
            return true;
        }
        String div = NetworkParam.getDiv();
        SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
        final String string = sharedPrefs.getString("updateUrl", "");
        String string2 = sharedPrefs.getString("updateAmapAppVersion", "");
        if (TextUtils.isEmpty(div) || TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_not_support_and_update));
            return true;
        } else if (div.compareTo(string2) >= 0) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_not_support_current));
            return true;
        } else {
            final bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                return false;
            }
            a aVar = new a(this.a);
            aVar.b(R.string.intent_not_support_and_update);
            aVar.a(R.string.update_remind);
            aVar.a(R.string.update_to_latest, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    try {
                        dly.this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(string)));
                    } catch (Exception unused) {
                    }
                }
            });
            aVar.b(R.string.update_not_now, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                }
            });
            aVar.b = new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            aVar.c = new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            aVar.a(false);
            AlertView a2 = aVar.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
            return true;
        }
    }
}
