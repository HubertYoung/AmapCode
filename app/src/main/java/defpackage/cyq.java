package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONObject;

/* renamed from: cyq reason: default package */
/* compiled from: SelectPoiFromMapSuspendViewManager */
public final class cyq extends ccx implements ccs {
    public IAjxContext f;
    boolean g = false;

    public final void addControl(String str, JsFunctionCallback jsFunctionCallback) {
    }

    public final void e() {
    }

    public final void hideControl(String str) {
    }

    public final void showControl(String str, boolean z) {
    }

    public final void updateControl(String str) {
    }

    public cyq(IMapPage iMapPage) {
        super(iMapPage);
    }

    public final void b() {
        View a = this.e.a(false);
        a.setContentDescription("指南针");
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(this.b, 8.0f);
        layoutParams.topMargin = agn.a(this.b, 8.0f);
        a(a, layoutParams);
    }

    public final void c() {
        View n = this.e.n();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(this.b, 4.0f);
        b(n, layoutParams);
        this.e.a((cdp) new cdp() {
            public final void onFloorChanged(int i, int i2) {
            }

            public final void onFloorWidgetVisibilityChanged(ami ami, boolean z, int i) {
                if (z) {
                    cyq.this.e.b(false).setVisibility(8);
                    return;
                }
                if (!cyq.this.g) {
                    cyq.this.e.b(false).setVisibility(0);
                }
            }
        });
    }

    public final void d() {
        View b = this.e.b(false);
        LayoutParams layoutParams = new LayoutParams(this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size), this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        layoutParams.rightMargin = agn.a(this.a.getContext(), 4.0f);
        layoutParams.topMargin = agn.a(this.a.getContext(), 4.0f);
        c(b, layoutParams);
    }

    public final void f() {
        ZoomView l = this.e.l();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.rightMargin = agn.a(this.b, 4.0f);
        layoutParams.topMargin = agn.a(this.b, 4.0f);
        layoutParams.bottomMargin = agn.a(this.b, 4.0f);
        d(l, layoutParams);
    }

    private MarginLayoutParams g() {
        return (MarginLayoutParams) a().getLayoutParams();
    }

    private void a(ViewGroup.LayoutParams layoutParams) {
        a().setLayoutParams(layoutParams);
    }

    public final void setMarginBottom(int i, int i2) {
        MarginLayoutParams g2 = g();
        if (g2 != null && g2.bottomMargin != i) {
            g2.bottomMargin = i;
            a(g2);
            AMapLog.d("SelectPoi", "setMarginBottom=".concat(String.valueOf(i)));
        }
    }

    public final void setMarginTop(int i, int i2) {
        MarginLayoutParams g2 = g();
        if (g2 != null && g2.topMargin != i) {
            g2.topMargin = i;
            if (euk.a()) {
                g2.topMargin += euk.a(a().getContext());
            }
            a(g2);
            AMapLog.d("SelectPoi", "setMarginTop=".concat(String.valueOf(i)));
        }
    }

    public final void setViewAlpha(float f2, int i) {
        View a = a();
        if (a.getAlpha() != f2) {
            a.setAlpha(f2);
            AMapLog.d("SelectPoi", "setViewAlpha=".concat(String.valueOf(f2)));
        }
    }

    public final void setVerticalMargin(int i, int i2, int i3, int i4) {
        MarginLayoutParams g2 = g();
        if (g2 == null) {
            return;
        }
        if (g2.topMargin != i || g2.bottomMargin != i2) {
            g2.topMargin = i;
            g2.bottomMargin = i2;
            a(g2);
            StringBuilder sb = new StringBuilder("setVerticalMargin marginTop/marginBottom=");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            AMapLog.d("SelectPoi", sb.toString());
        }
    }

    public final void setCommonControl(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                new JSONObject();
                boolean z = jSONObject.getJSONObject("mainLayerControl").getBoolean("hidden");
                this.g = z;
                if (z) {
                    this.e.h().setVisibility(8);
                } else {
                    this.e.h().setVisibility(0);
                }
            } catch (Exception unused) {
            }
            a().setVisibility(0);
        }
    }
}
