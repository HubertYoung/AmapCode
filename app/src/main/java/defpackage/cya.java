package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;

/* renamed from: cya reason: default package */
/* compiled from: FrequentLocationController */
public final class cya implements a {
    public cyn a;
    public cyg b;
    private cyf c;
    private MapSharePreference d = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private boolean e;

    public final void onAppear() {
    }

    public final void onConfigCallBack(int i) {
    }

    public final void onPause() {
    }

    public final void onCreate() {
        this.e = false;
        this.c = new cyf();
        lo.a().a((String) "main_map_frequent_location", (lp) this);
        try {
            this.c.a(lo.a().a((String) "main_map_frequent_location"));
        } catch (Exception unused) {
        }
    }

    public final void onResume() {
        b();
        if (!cye.c()) {
            this.a.a();
        } else if (!this.e) {
            this.a.b();
        }
    }

    public final void a(boolean z) {
        this.e = !z;
        if (cye.c()) {
            if (z) {
                this.a.b();
            } else {
                this.a.a();
            }
        }
    }

    public final void a() {
        if (this.a != null) {
            this.a.c();
        }
    }

    public final void onCover() {
        this.a.a(false);
    }

    public final boolean onResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (i == 1000) {
            this.b.updateData(1001, pageBundle);
        } else if (i == 1001) {
            this.b.updateData(1002, pageBundle);
        } else if (i != 2000) {
            return false;
        } else {
            this.b.selectPoiCallback(pageBundle);
        }
        return true;
    }

    public final void onConfigResultCallBack(int i, String str) {
        this.c.a(str);
        b();
    }

    public final void onDestroy() {
        lo.a().b("main_map_frequent_location", this);
        this.b.destroy();
        this.a.a(false);
    }

    private void b() {
        boolean z = true;
        if (this.d.getIntValue("sp_key_open_frequent_location_aocs", 0) != 1) {
            z = false;
        }
        cye.a = z;
    }
}
