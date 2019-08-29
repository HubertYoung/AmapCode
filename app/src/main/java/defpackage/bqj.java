package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.idqmax.page.SearchIdqMaxPage;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/* renamed from: bqj reason: default package */
/* compiled from: SearchIdqMaxJsSuspendHelper */
public final class bqj extends ccv implements ccs {
    protected Context a;
    protected ccy b;
    public View c;
    public View d;
    public View e;
    public View f;
    public View g;
    public View h;
    public View i;
    float j = 0.0f;
    SearchIdqMaxPage k;
    private HashMap<String, View> l = new HashMap<>();
    private HashMap<String, View> m = new HashMap<>();
    private final MarginLayoutParams n;

    public final void addControl(String str, JsFunctionCallback jsFunctionCallback) {
    }

    public final void updateControl(String str) {
    }

    public bqj(SearchIdqMaxPage searchIdqMaxPage) {
        super(searchIdqMaxPage.getContext());
        this.k = searchIdqMaxPage;
        this.a = searchIdqMaxPage.getContext();
        this.b = searchIdqMaxPage.getSuspendWidgetHelper();
        this.n = d();
    }

    public final void a() {
        this.e = this.b.a(false, new d());
        this.d = this.b.c(false);
        this.f = this.b.l();
        this.c = this.b.f();
        this.g = this.b.d();
        this.h = this.b.n();
        this.i = this.b.a();
        a(this.e);
        a(this.d);
        a(this.f);
        a(this.c);
        a(this.g);
        a(this.h);
        a(this.i);
        addWidget(this.i, this.b.c(), 1);
        addWidget(this.f, this.b.m(), 5);
        addWidget(this.g, this.b.e(), 3);
        addWidget(this.c, this.b.g(), 7);
        addWidget(this.h, this.b.q(), 2);
        addWidget(this.e, this.b.i(), 4);
        addWidget(this.d, this.b.k(), 4);
        this.l.put("locationControl", this.g);
        this.l.put("mainLayerControl", this.e);
        this.l.put("trafficControl", this.d);
        this.l.put("scaleControl", this.c);
        this.l.put("compassControl", this.i);
        this.l.put("zoomControl", this.f);
        this.l.put("floorControl", this.h);
        this.e.setVisibility(8);
        this.d.setVisibility(8);
        this.f.setVisibility(8);
        this.c.setVisibility(8);
        this.g.setVisibility(8);
        this.h.setVisibility(8);
        this.i.setVisibility(8);
    }

    public final void b() {
        this.g = this.b.d();
        this.b.a((a) new a() {
            public final void a() {
                bqj.this.k.onPageGpsBtnClicked();
            }
        });
        a(this.g);
        addWidget(this.g, this.b.e(), 3);
        this.l.put("locationControl", this.g);
        this.g.setVisibility(0);
    }

    private static void a(View view) {
        if (view != null && view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public final void setMarginBottom(int i2, int i3) {
        if (i2 == 0) {
            i2 = 8;
        }
        MarginLayoutParams d2 = d();
        if (d2 != null && d2.bottomMargin != i2) {
            d2.bottomMargin = i2 - agn.a(this.a, 48.0f);
            a((LayoutParams) d2);
        }
    }

    public final void setMarginTop(int i2, int i3) {
        if (i2 == 0) {
            i2 = 8;
        }
        MarginLayoutParams d2 = d();
        if (d2 != null && d2.topMargin != i2) {
            d2.topMargin = i2 - agn.a(this.a, 48.0f);
            a((LayoutParams) d2);
        }
    }

    public final void setViewAlpha(float f2, int i2) {
        View suspendView = getSuspendView();
        if (suspendView.getAlpha() != f2) {
            suspendView.setAlpha(f2);
        }
    }

    private MarginLayoutParams d() {
        return (MarginLayoutParams) getSuspendView().getLayoutParams();
    }

    private void a(LayoutParams layoutParams) {
        getSuspendView().setLayoutParams(layoutParams);
        getSuspendView().requestLayout();
    }

    public final void setVerticalMargin(int i2, int i3, int i4, int i5) {
        MarginLayoutParams d2 = d();
        if (d2 == null) {
            return;
        }
        if (d2.topMargin != i2 || d2.bottomMargin != i3) {
            this.j = (float) i2;
            d2.topMargin = i2 - agn.a(this.a, 48.0f);
            d2.bottomMargin = i3 - agn.a(this.a, 48.0f);
            a((LayoutParams) d2);
        }
    }

    public final void setCommonControl(String str, JsFunctionCallback jsFunctionCallback) {
        this.m.clear();
        List<a> b2 = ccw.b(str);
        if (b2 != null && !b2.isEmpty()) {
            for (int i2 = 0; i2 < b2.size(); i2++) {
                a aVar = b2.get(i2);
                if (aVar != null) {
                    View view = this.l.get(aVar.a);
                    if (view != null) {
                        view.setVisibility(aVar.g ? 8 : 0);
                        this.m.put(aVar.a, view);
                    }
                }
            }
        }
    }

    public final void showControl(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            View view = this.l.get(str);
            if (view != null) {
                view.setVisibility(0);
            }
        }
    }

    public final void hideControl(String str) {
        if (!TextUtils.isEmpty(str)) {
            View view = this.l.get(str);
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    public final void c() {
        for (Entry<String, View> value : this.m.entrySet()) {
            ((View) value.getValue()).setVisibility(0);
        }
    }
}
