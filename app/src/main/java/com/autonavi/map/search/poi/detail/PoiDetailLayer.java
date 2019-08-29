package com.autonavi.map.search.poi.detail;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.searchresult.ajx.AjxModuleTipDetailPage;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.searchresult.ajx.ModulePoi.a;
import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.modules.ModuleJsBridge;
import com.autonavi.minimap.ajx3.modules.internalmodules.AjxModuleBridge;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.search.PoiDetailSlidingView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.json.JSONException;
import org.json.JSONObject;

public final class PoiDetailLayer {
    public PoiDetailSlidingView a;
    public AmapAjxView b;
    public ModulePoi c;
    public ViewGroup d;
    public ViewGroup e;
    public Context f;
    public bid g;
    public String h;
    public AjxModuleTipDetailPage i;
    public caq j;
    public Callback<AmapAjxView> k = new Callback<AmapAjxView>() {
        public void error(Throwable th, boolean z) {
        }

        public void callback(AmapAjxView amapAjxView) {
            if (amapAjxView == PoiDetailLayer.this.b) {
                PoiDetailLayer.this.i = (AjxModuleTipDetailPage) amapAjxView.getJsModule("tipDetailPage");
                if (PoiDetailLayer.this.i != null) {
                    PoiDetailLayer.this.i.setListener(PoiDetailLayer.this.j.h);
                }
                PoiDetailLayer.this.c = (ModulePoi) amapAjxView.getJsModule("poi");
                if (PoiDetailLayer.this.c != null) {
                    PoiDetailLayer.this.c.setOnRegisterInfoListener(PoiDetailLayer.this.j.f);
                    PoiDetailLayer.this.c.setOnStateChangeListener(PoiDetailLayer.this.j.g);
                    PoiDetailLayer.this.c.setOnPoiCallbackInitListener(PoiDetailLayer.this.m);
                    int a2 = ccd.a((View) PoiDetailLayer.this.e);
                    PoiDetailLayer poiDetailLayer = PoiDetailLayer.this;
                    poiDetailLayer.h = PoiDetailLayer.this.a(PoiDetailLayer.this.h, (ccd.a((View) PoiDetailLayer.this.d) - agn.a(PoiDetailLayer.this.f, 39.0f)) + a2);
                    PoiDetailLayer poiDetailLayer2 = PoiDetailLayer.this;
                    if (poiDetailLayer2.b != null) {
                        ModuleJsBridge moduleJsBridge = (ModuleJsBridge) poiDetailLayer2.b.getJsModule("js");
                        if (moduleJsBridge != null) {
                            JsAdapter jsMethod = moduleJsBridge.getJsMethod();
                            if (!(jsMethod == null || poiDetailLayer2.j.e == null)) {
                                jsMethod.setBundle(poiDetailLayer2.j.e);
                                poiDetailLayer2.j.e = null;
                            }
                        }
                    }
                }
            }
        }
    };
    public Callback<AmapAjxView> l = new Callback<AmapAjxView>() {
        public void error(Throwable th, boolean z) {
        }

        public void callback(AmapAjxView amapAjxView) {
            AjxModuleBridge ajxModuleBridge = (AjxModuleBridge) PoiDetailLayer.this.b.getJsModule(AjxModuleBridge.MODULE_NAME);
            if (ajxModuleBridge != null) {
                ajxModuleBridge.callJsFunction("poiInfo", PoiDetailLayer.this.h);
            }
            StringBuilder sb = new StringBuilder("poiInfo ");
            sb.append(PoiDetailLayer.this.h);
            AMapLog.d("PoiDetailLayer", sb.toString());
        }
    };
    a m = new a() {
        public final void a() {
            if (!TextUtils.equals(PoiDetailLayer.this.j.b, "invalid")) {
                PoiDetailLayer.this.c.setDetailViewState(PoiDetailLayer.this.j.b, PoiDetailLayer.this.j.c);
            }
        }
    };
    private int n;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SwitchViewType {
    }

    public final String a(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (i2 > 0) {
            this.n = i2;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("detail");
            optJSONObject.put("tipsHeight", (double) DimensionUtils.pixelToStandardUnit(this.f, (float) this.n));
            optJSONObject.put("forbiddenDragDown", this.j.a);
            jSONObject.put("detail", optJSONObject);
            str = jSONObject.toString();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return str;
    }

    public final void a(boolean z) {
        if (this.a != null) {
            this.a.setTouchable(z);
        }
    }

    public final boolean a() {
        return this.a != null && this.a.getVisibility() == 0;
    }

    public final void a(String str, boolean z) {
        if (this.c == null) {
            this.j.b = str;
            this.j.c = z;
            return;
        }
        this.c.setDetailViewState(str, z);
    }

    public final caq b() {
        return this.j != null ? this.j : new caq();
    }
}
