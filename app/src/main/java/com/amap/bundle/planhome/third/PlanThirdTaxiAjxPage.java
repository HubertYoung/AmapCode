package com.amap.bundle.planhome.third;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressFBWarnings({"BIT_SIGNED_CHECK"})
public class PlanThirdTaxiAjxPage extends Ajx3Page {
    private View a;
    private acp b;
    private boolean c = true;

    @Nullable
    public String getAjx3Url() {
        return "path://amap_bundle_taxi/src/taxi_order/pages/TaxiIndex.page.js";
    }

    public boolean handleSetContentView() {
        return true;
    }

    public boolean isFullScreenMode() {
        return false;
    }

    public void resume() {
        super.resume();
        acp acp = this.b;
        bty mapView = acp.b.getMapView();
        if (mapView != null) {
            mapView.a(0, mapView.l(false), 13);
            mapView.d(false);
            mapView.a(true);
            mapView.g(0.0f);
            mapView.e(true);
        }
        cde suspendManager = acp.b.getSuspendManager();
        if (suspendManager != null) {
            suspendManager.d().f();
        }
        if (!(mapView == null || acp.c == -1 || mapView.w() == acp.c)) {
            mapView.f((float) acp.c);
        }
        axd axd = acl.a().a;
        if (!(axd == null || axd.getRouteInputUI() == null)) {
            if (axd.getRouteInputUI().o()) {
                PageBundle arguments = getArguments();
                if (arguments != null) {
                    int a2 = eqg.a(arguments.getString("bundleKeyVoiceCmd"));
                    if (a2 != -1) {
                        a(a2);
                    } else if (!this.c) {
                        a(arguments.getInt("bundle_key_token", -1));
                    }
                }
            }
            if (this.a != null && this.a.getParent() == null) {
                axd.getRouteInputUI().a(this.a);
            }
        }
        this.c = false;
    }

    public void pause() {
        super.pause();
        acp acp = this.b;
        if (acp.b.getMapManager() != null && acp.b.getMapManager().getMapView() != null) {
            cdd.n().e();
            bty mapView = acp.b.getMapView();
            if (mapView != null) {
                acp.c = mapView.w();
            }
            if (acp.b.getMapView() != null) {
                acp.b.getMapView().a(false);
                acp.b.getMapView().z();
                acp.b.getMapView().f(acp.a.b);
            }
        }
    }

    public void stop() {
        super.stop();
        if (this.a != null && this.a.getParent() != null) {
            axd axd = acl.a().a;
            if (axd != null && axd.getRouteInputUI() != null) {
                axd.getRouteInputUI().b(this.a);
            }
        }
    }

    public void destroy() {
        super.destroy();
        if (!(this.a == null || this.a.getParent() == null)) {
            axd axd = acl.a().a;
            if (!(axd == null || axd.getRouteInputUI() == null)) {
                axd.getRouteInputUI().b(this.a);
            }
        }
        this.a = null;
    }

    public void onContentViewCreated(View view) {
        this.a = view;
    }

    private static void a(int i) {
        if (i != -1) {
            boolean z = false;
            String str = (String) Ajx.getInstance().getMemoryStorage("taxi_business_state").getItem("state");
            if (!TextUtils.isEmpty(str)) {
                try {
                    z = "1".equals(new JSONObject(str).getJSONObject(FunctionSupportConfiger.TAXI_TAG).getString("polling"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (z) {
                d.a.a(i, 10126, (String) null);
            } else {
                d.a.a(i, 10000, (String) null);
            }
        }
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            Object object = arguments.getObject("jsData");
            JSONObject jSONObject = null;
            if (object != null && (object instanceof JSONObject)) {
                jSONObject = (JSONObject) object;
            }
            if (arguments.getInt("key_source", 0) == 102) {
                if (jSONObject == null) {
                    jSONObject = new JSONObject();
                }
                try {
                    jSONObject.put("fromSource", "etrip");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arguments.putObject("jsData", jSONObject);
            }
        }
        super.onCreate(context);
        setContentView(R.layout.third_layout_content_view);
    }

    public void pageCreated() {
        if (this.a != null) {
            this.a.setLayoutParams(new LayoutParams(-1, -1));
            axd axd = acl.a().a;
            if (!(axd == null || axd.getRouteInputUI() == null)) {
                axd.getRouteInputUI().a(this.a);
            }
        }
        this.b = new acp(this);
        this.b.a();
        if (getArguments() != null) {
            a(getArguments().getInt("bundle_key_token", -1));
        }
    }
}
