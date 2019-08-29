package defpackage;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.amap.bundle.logs.AMapLog;
import com.amap.pages.framework.Pages;
import com.autonavi.common.PageBundle;
import com.autonavi.inter.IPageManifest;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTop;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTopAllowDuplicate;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: bul reason: default package */
/* compiled from: MvpActivityContext */
public final class bul implements bui {
    public final bum a = new bum();
    public final akd b;
    public Pages c;
    public Activity d;
    public ViewGroup e;
    private Class<? extends akc> f;

    private static int b(int i) {
        if (i == 2) {
            return 8;
        }
        if (i == 4) {
            return 4;
        }
        return i == 16 ? 2 : -1;
    }

    public bul(Activity activity, ViewGroup viewGroup, Class<? extends akc> cls) {
        this.d = activity;
        this.e = viewGroup;
        this.c = new Pages(activity, activity.getLayoutInflater(), viewGroup);
        Pages pages = this.c;
        Pages.a();
        this.b = pages.b;
        Pages pages2 = this.c;
        Pages.a();
        pages2.a.h = this;
        this.f = cls;
    }

    public final void a(Class<?> cls, PageBundle pageBundle) {
        a(cls, pageBundle, (aki) null);
    }

    public final void a(String str, PageBundle pageBundle) {
        a(str, pageBundle, (aki) null);
    }

    /* access modifiers changed from: protected */
    public final void a(String str, PageBundle pageBundle, aki aki) {
        a(a(str), pageBundle, aki);
    }

    public final void a(Class<?> cls, PageBundle pageBundle, aki aki) {
        HashMap hashMap;
        String str;
        ake ake = null;
        if (pageBundle != null) {
            hashMap = new HashMap();
            hashMap.put("CUSCTOM_BUNDLE", pageBundle);
            hashMap.put("PAGE_COUNT", Integer.valueOf(pageBundle.getPageCnt()));
        } else {
            hashMap = null;
        }
        int i = -1;
        if (pageBundle != null) {
            i = b(pageBundle.getFlags());
            str = pageBundle.getUniqueID();
        } else {
            str = null;
        }
        if (i < 0) {
            i = a(cls);
        }
        int i2 = i;
        akh akh = new akh(hashMap, cls, str);
        akc b2 = this.b.b();
        Object h = (b2 == null || !(b2 instanceof buk)) ? null : ((buk) b2).h();
        akb a2 = bus.a(h != null ? h.getClass() : null, (Class) cls);
        aka e2 = a2 != null ? e() : null;
        if (a2 != null) {
            ake = new ake(e2, a2);
        }
        this.b.a(this.f, i2, akh, aki, ake);
    }

    private aka e() {
        final bid b2 = b();
        return new aka() {
            public final void a() {
                if (b2 != null) {
                    b2.onAnimationStarted(false);
                }
                bid b2 = bul.this.b();
                if (b2 != null) {
                    b2.onAnimationStarted(true);
                }
            }

            public final void b() {
                if (b2 != null) {
                    b2.onAnimationFinished(false);
                }
                bid b2 = bul.this.b();
                if (b2 != null) {
                    b2.onAnimationFinished(true);
                }
            }
        };
    }

    public final void a(String str, PageBundle pageBundle, int i) {
        a(a(str), pageBundle, i);
    }

    public final void a(Class<?> cls, PageBundle pageBundle, int i) {
        akc b2 = this.b.b();
        if (b2 instanceof buk) {
            akg i2 = ((buk) b2).i();
            a(cls, pageBundle, i2 != null ? new aki(i, i2) : null);
        }
    }

    public final Activity a() {
        return this.d;
    }

    private static Class a(String str) {
        Class<?> page = ((IPageManifest) bqn.a(IPageManifest.class)).getPage(str);
        if ("amap.basemap.action.default_page".equals(str)) {
            return ((buh) ank.a(buh.class)).a(str);
        }
        if (page == null) {
            StringBuilder sb = new StringBuilder("不存在Action为：");
            sb.append(str);
            sb.append("的Page");
            AMapLog.e("MvpActivityContext", sb.toString());
        }
        return page;
    }

    private static int a(Class<?> cls) {
        int i = 1;
        if (cls == null) {
            return 1;
        }
        boolean isAssignableFrom = launchModeSingleTask.class.isAssignableFrom(cls);
        boolean isAssignableFrom2 = launchModeSingleTop.class.isAssignableFrom(cls);
        boolean isAssignableFrom3 = launchModeSingleInstance.class.isAssignableFrom(cls);
        launchModeSingleTopAllowDuplicate.class.isAssignableFrom(cls);
        if (Transparent.class.isAssignableFrom(cls)) {
            i = 16;
        } else if (isAssignableFrom) {
            i = 4;
        } else if (isAssignableFrom2) {
            i = 8;
        } else if (isAssignableFrom3) {
            i = 2;
        }
        return i;
    }

    public final void a(a aVar) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("builder", aVar);
        a((String) "amap.page.action.alert_dialog_page", pageBundle, -1);
    }

    @Nullable
    public final bid b() {
        bid bid = null;
        try {
            akc b2 = this.b.b();
            if (b2 != null && (b2 instanceof buk)) {
                bid = ((buk) b2).h();
            }
            return bid;
        } catch (Exception unused) {
            return null;
        }
    }

    @Nullable
    public final bid a(int i) {
        bid bid = null;
        try {
            ArrayList<akc> c2 = this.b.c();
            akc akc = (c2 == null || i >= c2.size() || i < 0) ? null : c2.get((c2.size() - 1) - i);
            if (akc != null && (akc instanceof buk)) {
                bid = ((buk) akc).h();
            }
            return bid;
        } catch (Exception unused) {
            return null;
        }
    }

    public final Class<?> c() {
        try {
            return this.b.a();
        } catch (Exception unused) {
            return null;
        }
    }

    public final ArrayList<akc> d() {
        try {
            return this.b.c();
        } catch (Exception unused) {
            return null;
        }
    }
}
