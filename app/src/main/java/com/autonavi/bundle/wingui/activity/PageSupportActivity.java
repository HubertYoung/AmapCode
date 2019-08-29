package com.autonavi.bundle.wingui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import com.amap.pages.framework.Pages;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPage;
import java.util.ArrayList;
import java.util.Iterator;

public class PageSupportActivity extends FragmentActivity {
    protected bul a;

    /* access modifiers changed from: protected */
    public final void a(ViewGroup viewGroup) {
        this.a = new bul(this, viewGroup, bun.class);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onStart() {
        super.onStart();
        if (a()) {
            Pages pages = this.a.c;
            Pages.a();
            akj akj = pages.a;
            if (akj.i == 1) {
                akj.i = 2;
                akj.a(akj.a(), false);
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (a()) {
            Pages pages = this.a.c;
            Pages.a();
            akj akj = pages.a;
            akj.i = 4;
            akj.a(akj.a(), true);
        }
    }

    public void onPause() {
        super.onPause();
        if (a()) {
            Pages pages = this.a.c;
            Pages.a();
            akj akj = pages.a;
            akj.i = 2;
            akj.d(akj.a());
        }
    }

    public void onStop() {
        super.onStop();
        if (a()) {
            Pages pages = this.a.c;
            Pages.a();
            akj akj = pages.a;
            akj.i = 1;
            akj.c(akj.a());
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (a()) {
            Pages pages = this.a.c;
            Pages.a();
            akj akj = pages.a;
            ArrayList arrayList = new ArrayList();
            Iterator<a> it = akj.d.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            akj.e(arrayList);
            akj.d.clear();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (a()) {
            ArrayList<akc> c = this.a.b.c();
            if (c != null) {
                Iterator<akc> it = c.iterator();
                while (it.hasNext()) {
                    akc next = it.next();
                    if (next instanceof buk) {
                        bid h = ((buk) next).h();
                        if (h != null && (h instanceof IPage)) {
                            ((IPage) h).onConfigurationChanged(configuration);
                        }
                    }
                }
            }
        }
    }

    public final boolean a(int i, KeyEvent keyEvent) {
        if (!a()) {
            return false;
        }
        bid b = this.a.b();
        if (b == null || !(b instanceof AbstractBasePage) || !((AbstractBasePage) b).onKeyDown(i, keyEvent)) {
            return false;
        }
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (a()) {
            bid b = this.a.b();
            if (b != null && (b instanceof AbstractBasePage)) {
                ((AbstractBasePage) b).onWindowFocusChanged(z);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final int f() {
        if (!a()) {
            return 0;
        }
        akc b = this.a.b.b();
        if (b != null) {
            return b.g();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public final boolean g() {
        return this.a != null && this.a.a.a;
    }

    private boolean a() {
        return this.a != null;
    }
}
