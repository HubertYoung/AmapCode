package defpackage;

import android.content.Intent;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import java.util.ArrayList;
import java.util.List;

/* renamed from: aoi reason: default package */
/* compiled from: AmapWeiboApi */
public final class aoi {
    private List<aot> a;

    /* renamed from: aoi$a */
    /* compiled from: AmapWeiboApi */
    public static class a {
        /* access modifiers changed from: private */
        public static aoi a = new aoi(0);
    }

    /* synthetic */ aoi(byte b) {
        this();
    }

    private aoi() {
        this.a = new ArrayList();
        WbSdk.install(AMapAppGlobal.getApplication(), new AuthInfo(AMapAppGlobal.getApplication(), "884965267", ConfigerHelper.REDIRECT_URL, "email,direct_messages_read,direct_messages_write"));
    }

    public final void a(int i, int i2, Intent intent) {
        if (this.a != null) {
            for (aot next : this.a) {
                if (next != null) {
                    next.a(i, i2, intent);
                }
            }
            this.a.clear();
        }
    }

    public final void a(aot aot) {
        if (this.a != null) {
            this.a.add(aot);
        }
    }

    public final void a() {
        this.a.clear();
    }
}
