package com.autonavi.bundle.scenicarea.page;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.scenicarea.ajx.ModuleSearchScenic;
import com.autonavi.bundle.scenicarea.ajx.ModuleSearchScenic.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("search_scenicarea_walk_route")
public class SearchScenicMapRoutePage extends Ajx3Page implements a {
    private ModuleSearchScenic a;
    private bbb b;

    public final void b() {
    }

    public final String c(String str) {
        return "";
    }

    @Nullable
    public String getAjx3Url() {
        return "path://amap_bundle_scenic_area/src/pages/BizScenicWalkPlanPage.page.js";
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.a = (ModuleSearchScenic) this.mAjxView.getJsModule(ModuleSearchScenic.MODULE_NAME);
        if (this.a != null) {
            this.a.setUiListener(this);
        }
        if (this.b == null) {
            this.b = new bbb(this);
        }
        bbb bbb = this.b;
        bbb.b();
        bbb.a();
        if (bbb.a != null) {
            bbb.f = bbb.a.ai();
            bbb.g = brv.a();
            bbb.a.q(false);
            bbb.a.s(false);
        }
    }

    public void onJsBack(Object obj, String str) {
        super.onJsBack(obj, str);
    }

    public Ajx3PagePresenter createPresenter() {
        return super.createPresenter();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
    }

    public void destroy() {
        super.destroy();
        bbb bbb = this.b;
        bbb.a(true);
        if (bbb.a != null) {
            bbb.a.q(bbb.f);
            bbb.a.s(bbb.g);
        }
    }

    public final void a() {
        bty mapView = getMapView();
        if (mapView != null) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                mapView.a((GLGeoPoint) latestPosition);
            }
        }
    }

    public View getMapSuspendView() {
        if (this.b == null) {
            this.b = new bbb(this);
        }
        bbb bbb = this.b;
        bbb.e = bbb.d.f();
        LayoutParams g = bbb.d.g();
        if (g != null) {
            g.bottomMargin = agn.a(bbb.b, 4.0f);
            bbb.c.addWidget(bbb.e, g, 7);
        }
        return this.b.c.getSuspendView();
    }

    public final void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("bottomOffset");
            int parseFloat = (int) Float.parseFloat(optString);
            int parseFloat2 = (int) Float.parseFloat(jSONObject.optString("leftOffset"));
            if (this.b != null) {
                bbb bbb = this.b;
                if (bbb.e != null) {
                    LayoutParams layoutParams = (LayoutParams) bbb.e.getLayoutParams();
                    int a2 = agn.a(bbb.b, (float) (parseFloat / 2));
                    int a3 = agn.a(bbb.b, (float) (parseFloat2 / 2));
                    if (a2 != layoutParams.bottomMargin) {
                        layoutParams.bottomMargin = a2;
                    }
                    if (a3 != layoutParams.leftMargin) {
                        layoutParams.leftMargin = a3;
                    }
                    bbb.e.requestLayout();
                }
            }
        } catch (JSONException unused) {
        } catch (NumberFormatException unused2) {
        }
    }

    public final void b(String str) {
        try {
            boolean optBoolean = new JSONObject(str).optBoolean("showWidget", true);
            if (this.b != null) {
                bbb bbb = this.b;
                if (bbb.e != null) {
                    bbb.e.setVisibility(optBoolean ? 0 : 4);
                }
            }
        } catch (JSONException unused) {
        }
    }

    public void onPageAppear() {
        super.onPageAppear();
        if (this.b != null) {
            bbb bbb = this.b;
            bbb.a();
            if (bbb.a != null) {
                bbb.a.d(true);
                bbb.a.e(true);
                bbb.a.q(false);
                bbb.a.s(false);
            }
        }
    }

    public void onPageCover() {
        super.onPageCover();
        if (this.b != null) {
            bbb bbb = this.b;
            bbb.a(true);
            if (bbb.a != null) {
                bbb.a.q(bbb.f);
                bbb.a.s(bbb.g);
                bbb.a.B();
                bbb.a.z();
            }
        }
    }
}
