package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.R;

/* renamed from: cax reason: default package */
/* compiled from: ExtendWebViewPresenter */
public abstract class cax extends cav {
    /* access modifiers changed from: private */
    public final String b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    /* access modifiers changed from: private */
    public final Uri g;

    public boolean f() {
        return true;
    }

    /* access modifiers changed from: protected */
    public abstract boolean i();

    public cax(Uri uri, String str) {
        this.b = uri.getQueryParameter("contentType");
        this.c = uri.getQueryParameter("zoom_settings");
        this.d = uri.getQueryParameter("urlType");
        this.e = uri.getQueryParameter("hide_title");
        this.f = str;
        this.g = uri;
    }

    public final boolean g() {
        return !TextUtils.equals(this.e, "1");
    }

    public final boolean h() {
        return "autonavi".equals(this.b) && !"1".equals(this.d) && !this.f.contains(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.REAL_SCENE_ACTIVITY));
    }

    public final boolean e() {
        if (TextUtils.equals(this.c, "1")) {
            return true;
        }
        if (TextUtils.equals(this.c, "0")) {
            return false;
        }
        return i();
    }

    public final boolean d() {
        return !"autonavi".equals(this.b);
    }

    public final b c() {
        if ("autonavi".equals(this.b)) {
            return null;
        }
        return new b() {
            public final boolean a() {
                return false;
            }

            public final long c() {
                return 1000;
            }

            public final String b() {
                if (!"autonavi".equals(cax.this.b)) {
                    return cax.this.g.getQueryParameter("websiteName");
                }
                return cax.this.a.getContext().getString(R.string.third_Name);
            }
        };
    }
}
