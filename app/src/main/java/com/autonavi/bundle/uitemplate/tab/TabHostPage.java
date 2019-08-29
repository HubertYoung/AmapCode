package com.autonavi.bundle.uitemplate.tab;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.tab.TabBar.b;
import com.autonavi.bundle.uitemplate.tab.TabBar.c;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import defpackage.beq;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public abstract class TabHostPage<THP extends beq> extends AbstractBasePage implements ben, com.autonavi.bundle.uitemplate.tab.HostContainer.a, com.autonavi.bundle.uitemplate.tab.TabBar.a, b, c, IPageStateListener {
    public static String a = "PerformTabClickID";
    public List<bep> b;
    protected beo c;
    protected beo d;
    protected bep e;
    public HashMap<String, beo> f;
    private Handler g;
    private beo h;
    private bep i;
    private bep j;
    private HostContainer k;
    private Stack<a> l = new Stack<a>() {
        /* access modifiers changed from: private */
        /* renamed from: a */
        public synchronized a pop() {
            if (isEmpty()) {
                return null;
            }
            return (a) super.pop();
        }
    };

    static class a {
        bep a;
        PageBundle b;

        a(bep bep, PageBundle pageBundle) {
            this.a = bep;
            this.b = pageBundle;
        }
    }

    public abstract PageBundle a(bep bep);

    public abstract List<bep> a();

    /* access modifiers changed from: protected */
    public void a(bep bep, bep bep2) {
    }

    public boolean a(bep bep, MotionEvent motionEvent) {
        return false;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public abstract THP createPresenter();

    public boolean c(bep bep) {
        return false;
    }

    public void onAppear() {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.g = new Handler(Looper.getMainLooper());
        this.b = a();
        this.f = new HashMap<>(this.b.size());
        this.k = new HostContainer(this.g, context, this.b);
        this.k.setTag("hostContainer");
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new LayoutParams(-1, -1));
        relativeLayout.setBackground(null);
        relativeLayout.addView(this.k);
        setContentView((View) relativeLayout);
        this.k.setTransitionAnimationListener(this);
        this.k.getTabBar().setSelectItem(this.e);
        this.k.getTabBar().setOnTabClickListener(this);
        this.k.getTabBar().setOnTabTouchListener(this);
        AMapPageUtil.setPageStateListener(this, this);
    }

    /* access modifiers changed from: protected */
    public final bep a(String str) {
        for (bep next : this.b) {
            if (TextUtils.equals(next.d, str)) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final void a(bep bep, PageBundle pageBundle) {
        this.l.push(new a(bep, pageBundle));
        this.k.getTabBar().performTabClick(bep);
    }

    public final void h() {
        List<bep> list = this.b;
        if (list == null || list.size() <= 0) {
            throw new IllegalStateException("至少需要1个Tab");
        }
        bep bep = null;
        int i2 = 0;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).e) {
                i2++;
                bep = list.get(size);
            }
        }
        if (i2 > 1) {
            throw new IllegalStateException("只能设置1个默认Tab");
        }
        if (i2 <= 0) {
            bep = list.get(0);
        }
        this.j = bep;
        this.e = this.j;
        this.d = d(this.e);
        this.c = this.d;
        f(this.d);
        this.k.initDefaultTab(this.d.getContentView());
        this.k.getTabBar().setSelectItem(this.e);
    }

    public final void c() {
        StringBuilder sb = new StringBuilder("TabHost#onTransitionStart: mNextTab ");
        sb.append(this.i);
        sb.append(" mCurrentTab ");
        sb.append(this.e.d);
        beo beo = this.d;
        if (beo != null) {
            beo.t();
        }
        c(this.d);
        d(this.d);
        a(this.h);
        beo beo2 = this.h;
        if (beo2 != null) {
            beo2.s();
        }
    }

    public final void d() {
        this.d = this.h;
        this.e = this.i;
        a pop = this.l.pop();
        if (pop != null && bep.a(pop.a, this.e)) {
            a(this.d, pop.b);
        }
        b(this.d);
        g();
        StringBuilder sb = new StringBuilder("TabHost#onTransitionEnd: mCurrentTab ");
        sb.append(this.e.d);
        sb.append(" mCurrentTabPage ");
        sb.append(this.d);
        this.k.safeCheck(this.d.getContentView());
    }

    public void onCover() {
        if (!this.k.isTransationLocked()) {
            for (bep next : this.b) {
                if (!(next == this.e || next == this.j)) {
                    beo beo = this.f.get(next.d);
                    if (beo != null) {
                        e(beo);
                        this.k.destroyTabContent(beo.getContentView());
                        this.f.remove(next.d);
                    }
                }
            }
        }
    }

    @CallSuper
    public void b(bep bep) {
        StringBuilder sb = new StringBuilder("TabHost#onTabClick: ");
        sb.append(bep.d);
        sb.append(" mCurrentTab");
        sb.append(this.e.d);
        if (!bep.a(this.e, bep)) {
            boolean z = false;
            this.k.getTabBar().setTabClickable(false);
            beo beo = this.f.get(bep.d);
            if (beo == null) {
                beo = d(bep);
                this.c = beo;
                f(beo);
            }
            a(bep, this.e);
            this.c = beo;
            this.i = bep;
            this.h = beo;
            StringBuilder sb2 = new StringBuilder("TabHost#onTabClick#Show: ");
            sb2.append(this.i.d);
            sb2.append(" mCurrentTab");
            sb2.append(this.e.d);
            View contentView = this.d.getContentView();
            contentView.setTag(R.id.tab_host_tab_tag, this.e);
            View contentView2 = beo.getContentView();
            contentView2.setTag(R.id.tab_host_tab_tag, this.i);
            if (this.b.indexOf(bep) < this.b.indexOf(this.e)) {
                z = true;
            }
            this.k.showTabContent(contentView, contentView2, z);
        }
    }

    private void f(beo beo) {
        if (beo != null) {
            beo.performCreate(getContext());
        }
    }

    public static void a(beo beo) {
        if (beo != null) {
            beo.onStart();
        }
    }

    public static void b(beo beo) {
        if (beo != null) {
            beo.onResume();
        }
    }

    public static void c(beo beo) {
        if (beo != null) {
            beo.onPause();
        }
    }

    public static void d(beo beo) {
        if (beo != null) {
            beo.onStop();
        }
    }

    public static void e(beo beo) {
        if (beo != null) {
            beo.onDestroy();
        }
    }

    public static void a(beo beo, PageBundle pageBundle) {
        if (beo != null) {
            beo.onNewIntent(pageBundle);
        }
    }

    public static void a(beo beo, int i2, int i3, Intent intent) {
        if (beo != null) {
            beo.onActivityResult(i2, i3, intent);
        }
    }

    public static void a(beo beo, int i2, ResultType resultType, PageBundle pageBundle) {
        if (beo != null) {
            beo.onResult(i2, resultType, pageBundle);
        }
    }

    public static void a(beo beo, Configuration configuration) {
        if (beo != null) {
            beo.onConfigurationChanged(configuration);
        }
    }

    public final beo e() {
        return this.d;
    }

    public boolean a(PageBundle pageBundle) {
        if (pageBundle != null && pageBundle.containsKey(a)) {
            bep a2 = a(pageBundle.getString(a));
            if (!(a2 == null || this.e == a2)) {
                a(a2, pageBundle);
                return true;
            }
        }
        return false;
    }

    public final void f() {
        this.k.getTabBar().setVisibility(8);
    }

    public final void g() {
        this.k.getTabBar().setVisibility(0);
    }

    public boolean isShowMapWidgets() {
        if (this.d instanceof AbstractBasePage) {
            return this.d.isShowMapWidgets();
        }
        return super.isShowMapWidgets();
    }

    public boolean isShowPageHeader() {
        if (this.d instanceof AbstractBasePage) {
            return this.d.isShowPageHeader();
        }
        return super.isShowPageHeader();
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private defpackage.beo d(defpackage.bep r10) {
        /*
            r9 = this;
            r0 = 0
            java.lang.Class<? extends beo> r1 = r10.g     // Catch:{ Exception -> 0x003f }
            java.lang.Object r1 = r1.newInstance()     // Catch:{ Exception -> 0x003f }
            beo r1 = (defpackage.beo) r1     // Catch:{ Exception -> 0x003f }
            android.content.Context r3 = r9.getContext()     // Catch:{ Exception -> 0x003d }
            android.view.LayoutInflater r4 = r9.getLayoutInflater()     // Catch:{ Exception -> 0x003d }
            r5 = 0
            akg r6 = r9.getPageId()     // Catch:{ Exception -> 0x003d }
            bul r7 = r9.getMvpActivityContext()     // Catch:{ Exception -> 0x003d }
            r2 = r1
            r2.attach(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x003d }
            r1.a(r9)     // Catch:{ Exception -> 0x003d }
            com.autonavi.common.PageBundle r0 = r9.a(r10)     // Catch:{ Exception -> 0x003d }
            java.util.Stack<com.autonavi.bundle.uitemplate.tab.TabHostPage$a> r2 = r9.l     // Catch:{ Exception -> 0x003d }
            java.lang.Object r2 = r2.pop()     // Catch:{ Exception -> 0x003d }
            com.autonavi.bundle.uitemplate.tab.TabHostPage$a r2 = (com.autonavi.bundle.uitemplate.tab.TabHostPage.a) r2     // Catch:{ Exception -> 0x003d }
            if (r2 == 0) goto L_0x0039
            bep r3 = r2.a     // Catch:{ Exception -> 0x003d }
            boolean r3 = defpackage.bep.a(r10, r3)     // Catch:{ Exception -> 0x003d }
            if (r3 == 0) goto L_0x0039
            com.autonavi.common.PageBundle r0 = r2.b     // Catch:{ Exception -> 0x003d }
        L_0x0039:
            r1.setArguments(r0)     // Catch:{ Exception -> 0x003d }
            goto L_0x004d
        L_0x003d:
            r0 = move-exception
            goto L_0x0043
        L_0x003f:
            r1 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
        L_0x0043:
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x004d
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            r10.<init>(r0)
            throw r10
        L_0x004d:
            java.util.HashMap<java.lang.String, beo> r0 = r9.f
            java.lang.String r10 = r10.d
            r0.put(r10, r1)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.tab.TabHostPage.d(bep):beo");
    }
}
