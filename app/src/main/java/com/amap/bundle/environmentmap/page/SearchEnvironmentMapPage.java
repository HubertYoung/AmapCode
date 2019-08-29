package com.amap.bundle.environmentmap.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.environmentmap.net.request.EnvironmentGridRequest;
import com.amap.bundle.environmentmap.net.request.EnvironmentGridRequest.EnvironmentGridParam;
import com.amap.bundle.environmentmap.overlay.EnvironmentGLRasterOverlay;
import com.amap.bundle.environmentmap.overlay.SearchEnvironmentOverlay;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.environmentmap.ajx.ModuleSearchEnvironment;
import com.autonavi.bundle.environmentmap.ajx.ModuleSearchEnvironment.a;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.gmap.gloverlay.GLRasterOverlay;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(clickable = false, moveToFocus = true, overlay = UvOverlay.MapPointOverlay, visible = true)})
@SuppressFBWarnings({"BIT_SIGNED_CHECK"})
@PageAction("environment_map_page")
public class SearchEnvironmentMapPage extends Ajx3Page implements a, uq {
    public SearchEnvironmentOverlay a;
    public JsFunctionCallback b;
    private boolean c;
    private ModuleSearchEnvironment d;
    private ccy e;
    private Map<Integer, String> f;
    private EnvironmentGLRasterOverlay g;
    private EnvironmentGridRequest h;
    private long i;
    private JsFunctionCallback j;
    private uy k;
    private ccv l;
    private uz m;
    private int n;
    private int o;

    public Ajx3PagePresenter createPresenter() {
        return new uv(this);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        uw.a = true;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.a(true);
        }
        this.d = (ModuleSearchEnvironment) this.mAjxView.getJsModule(ModuleSearchEnvironment.MODULE_NAME);
        if (this.d != null) {
            this.d.setmUiListener(this);
        }
        this.f = new ConcurrentHashMap();
        uz.a(getMapView(), this.f);
        final bty mapView = getMapView();
        if (mapView != null) {
            this.n = mapView.o(false);
            mapView.a(mapView.j(false), mapView.l(false), 16);
            this.a = new SearchEnvironmentOverlay(mapView);
            addOverlay(this.a);
            this.c = mapView.s();
            mapView.b(false);
            aho.a(new Runnable() {
                public final void run() {
                    mapView.a(500, 10.0f, 0, 0, LocationInstrument.getInstance().getLatestPosition().x, LocationInstrument.getInstance().getLatestPosition().y);
                }
            });
        }
    }

    private static String e() {
        int i2;
        JSONObject jSONObject = new JSONObject();
        try {
            int adCode = LocationInstrument.getInstance().getLatestPosition().getAdCode();
            lj a2 = li.a().a((int) ((long) adCode));
            if (a2 != null) {
                i2 = a2.j;
            } else {
                i2 = 0;
            }
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(uz.a("map_environment_air"));
            jSONArray.put(uz.a("map_environment_water"));
            jSONArray.put(uz.a("map_environment_soil"));
            jSONArray.put(uz.a("map_environment_pollution"));
            jSONObject.put("layers", jSONArray);
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, adCode);
            jSONObject.put("citycode", i2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final void a() {
        if (this.a != null) {
            this.a.clear();
        }
    }

    public final void b() {
        if (this.j != null) {
            this.j.callback("");
        }
    }

    public final void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("navheight");
            ut utVar = new ut(getActivity(), Integer.parseInt(optString), jSONObject.optString("text"), this.o);
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null) {
                cfc.a().a(mapManager, (a) new a(utVar) {
                    final /* synthetic */ us a;

                    public final void onFailure() {
                    }

                    public final void onPrepare() {
                    }

                    {
                        this.a = r1;
                    }

                    public final void onScreenShotFinish(String str) {
                        if (str == null) {
                            str = "";
                        }
                        this.a.a(str);
                    }
                });
            }
        } catch (JSONException unused) {
        } catch (NumberFormatException unused2) {
        }
    }

    public View getMapSuspendView() {
        if (this.k == null || this.l == null) {
            return null;
        }
        uy uyVar = this.k;
        uyVar.b = uyVar.e.f();
        LayoutParams g2 = uyVar.e.g();
        if (g2 != null) {
            g2.bottomMargin = agn.a(uyVar.f, 4.0f);
            uyVar.d.addWidget(uyVar.b, g2, 7);
        }
        uyVar.c = uyVar.e.a();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(uyVar.f, 6.0f);
        layoutParams.topMargin = agn.a(uyVar.f, 51.0f);
        if (euk.a()) {
            layoutParams.topMargin = agn.a(uyVar.f, 51.0f) + euk.a(uyVar.f);
        }
        uyVar.d.addWidget(uyVar.c, layoutParams, 1);
        uyVar.a = uyVar.e.d();
        uyVar.e.a(uyVar.d.getSuspendView(), uyVar.a, uyVar.e.e(), 3);
        return this.l.getSuspendView();
    }

    public void destroy() {
        super.destroy();
        if (this.h != null) {
            EnvironmentGridRequest environmentGridRequest = this.h;
            environmentGridRequest.d = true;
            if (environmentGridRequest.c != null) {
                yq.a();
                yq.a((AosRequest) environmentGridRequest.c);
            }
        }
        uw.a = false;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.a(false);
        }
        bty mapView = getMapView();
        Map<Integer, String> map = this.f;
        awo awo2 = (awo) a.a.a(awo.class);
        if (!(awo2 == null || mapView == null)) {
            for (Integer intValue : map.keySet()) {
                awo2.a(intValue.intValue());
            }
        }
        a();
        removeOverlay(this.a);
        bty mapView2 = getMapView();
        if (mapView2 != null) {
            mapView2.b(this.c);
            mapView2.a(mapView2.j(false), mapView2.l(false), this.n);
            if (this.g != null && this.i != 0) {
                ((GLRasterOverlay) this.g.getGLOverlay()).removeRasterItem(this.i);
                mapView2.e().F().c(this.g);
            }
        }
    }

    public void resume() {
        super.resume();
        if (this.e != null) {
            this.e.a((a) new uu(this));
        }
    }

    public void pause() {
        super.pause();
        if (this.e != null) {
            this.e.a((a) null);
        }
    }

    public final void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (this.o != 0) {
                    a();
                    b();
                }
                this.o = new JSONObject(str).optInt("layerid");
                uz.a(getMapView(), this.o, this.f);
                boolean z = this.o == 600000;
                if (this.f.containsKey(Integer.valueOf(600000)) && z && this.i == 0) {
                    if (this.h == null) {
                        this.h = new EnvironmentGridRequest(this);
                    }
                    EnvironmentGridRequest environmentGridRequest = this.h;
                    environmentGridRequest.c = aax.b(new EnvironmentGridParam(0));
                    yq.a();
                    yq.a((AosRequest) environmentGridRequest.c, (AosResponseCallback<T>) environmentGridRequest.b);
                }
                if (this.g != null) {
                    this.g.setVisible(z);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final void a(JsFunctionCallback jsFunctionCallback) {
        this.b = jsFunctionCallback;
    }

    public final void b(JsFunctionCallback jsFunctionCallback) {
        this.j = jsFunctionCallback;
    }

    public final void c() {
        a();
    }

    public final void c(String str) {
        try {
            String optString = new JSONObject(str).optString("height");
            if (this.k != null) {
                uy uyVar = this.k;
                int parseFloat = (int) Float.parseFloat(optString);
                int i2 = 4;
                if (uyVar.a != null) {
                    LayoutParams layoutParams = (LayoutParams) uyVar.a.getLayoutParams();
                    int a2 = agn.a(uyVar.f, (float) ((parseFloat / 2) + (parseFloat > 0 ? 0 : 4)));
                    if (a2 != layoutParams.bottomMargin) {
                        layoutParams.bottomMargin = a2;
                        uyVar.a.requestLayout();
                    }
                }
                if (uyVar.b != null) {
                    LayoutParams layoutParams2 = (LayoutParams) uyVar.b.getLayoutParams();
                    Context context = uyVar.f;
                    int i3 = parseFloat / 2;
                    if (parseFloat > 0) {
                        i2 = 0;
                    }
                    int a3 = agn.a(context, (float) (i3 + i2));
                    if (a3 != layoutParams2.bottomMargin) {
                        layoutParams2.bottomMargin = a3;
                        uyVar.b.requestLayout();
                    }
                }
                this.k.a(true);
            }
        } catch (JSONException unused) {
        } catch (NumberFormatException unused2) {
        }
    }

    public final void d() {
        this.k.a(false);
    }

    public final void d(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.a.addHightLightOverlay(jSONObject.optString("label"), jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void a(byte[] bArr) {
        if (this.h == null || !this.h.d) {
            bty mapView = getMapView();
            if (bArr != null && mapView != null && this.i == 0 && this.o == 600000) {
                this.g = new EnvironmentGLRasterOverlay(mapView.ad(), getContext(), mapView.c());
                mapView.e().F().b((BaseMapOverlay) this.g);
                this.i = ((GLRasterOverlay) this.g.getGLOverlay()).addRasterItem(bArr);
            }
        }
    }

    public void onCreate(Context context) {
        this.m = new uz();
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
            setArguments(arguments);
        }
        arguments.putString("url", "path://amap_bundle_environment/src/app.js");
        arguments.putString("jsData", e());
        super.onCreate(context);
        this.e = getSuspendWidgetHelper();
        this.l = new ccv(getContext());
        if (this.e != null && getContext() != null) {
            this.k = new uy(getContext(), this.l, this.e);
        }
    }
}
