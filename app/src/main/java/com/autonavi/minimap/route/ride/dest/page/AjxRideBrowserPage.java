package com.autonavi.minimap.route.ride.dest.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.rideresult.ajx.ModuleRide;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;
import com.autonavi.minimap.route.ajx.inter.UnLockGpsButtonInterface;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxRideBrowserPage extends Ajx3Page implements OnErrorReportClickInterface, UnLockGpsButtonInterface {
    private edt a;
    /* access modifiers changed from: private */
    public ModuleRide b;
    private int c = -1;

    static class JsLoadCallback implements Callback<AmapAjxView> {
        private WeakReference<AjxRideBrowserPage> mPageRef;

        public void error(Throwable th, boolean z) {
        }

        JsLoadCallback(AjxRideBrowserPage ajxRideBrowserPage) {
            this.mPageRef = new WeakReference<>(ajxRideBrowserPage);
        }

        public void callback(AmapAjxView amapAjxView) {
            AjxRideBrowserPage ajxRideBrowserPage = (AjxRideBrowserPage) this.mPageRef.get();
            if (ajxRideBrowserPage != null && ajxRideBrowserPage.isAlive() && amapAjxView != null) {
                ajxRideBrowserPage.b = (ModuleRide) amapAjxView.getJsModule(ModuleRide.MODULE_NAME);
                if (ajxRideBrowserPage.b != null) {
                    ajxRideBrowserPage.b.setUnLockGpsBtnListener(ajxRideBrowserPage);
                }
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("jsData");
            if (!TextUtils.isEmpty(string)) {
                try {
                    this.c = new JSONObject(string).optString(Key.SOURCE_TYPE, "source_common").equals("source_etrip") ? 102 : 100;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        a();
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        edp.a("performance-", "showRideBrowserPage");
        FrameLayout frameLayout = new FrameLayout(getContext());
        MapInteractiveRelativeLayout mapInteractiveRelativeLayout = new MapInteractiveRelativeLayout(getContext());
        if (euk.a()) {
            mapInteractiveRelativeLayout.setPadding(mapInteractiveRelativeLayout.getPaddingLeft(), mapInteractiveRelativeLayout.getPaddingTop() + euk.a(getContext()), mapInteractiveRelativeLayout.getPaddingRight(), mapInteractiveRelativeLayout.getPaddingBottom());
        }
        frameLayout.addView(mapInteractiveRelativeLayout, new LayoutParams(-1, -1));
        frameLayout.addView(amapAjxView, new LayoutParams(-1, -1));
        amapAjxView.onAjxContextCreated(new JsLoadCallback(this));
        return frameLayout;
    }

    private void a() {
        if (getSuspendManager() != null) {
            cdz d = getSuspendManager().d();
            if (d != null) {
                d.a(false);
                d.f();
            }
        }
    }

    public View getMapSuspendView() {
        this.a = new edt(this, this.c);
        this.a.c = this;
        return this.a.b.getSuspendView();
    }

    public void onJsBack(Object obj, String str) {
        if (this.a != null) {
            this.a.c = null;
            this.a = null;
        }
        if (this.b != null) {
            this.b.setUnLockGpsBtnListener(null);
        }
        finish();
    }

    public void onErrorReportClickBtn(String str) {
        if (this.b != null) {
            String errorReportData = this.b.getErrorReportData();
            if (errorReportData != null && !errorReportData.trim().equals("")) {
                PageBundle b2 = ebc.b(getContext(), str, errorReportData);
                b2.putInt("sourcepage", 30);
                b2.putInt("page_id", 24);
                b2.putInt("route_line_type", 1);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("name", "骑行路线图面");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.PAGE_ID_FEEDBACK_ENTRANCE, "B001", jSONObject);
                IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                if (iErrorReportStarter != null) {
                    iErrorReportStarter.startFeedback(b2);
                }
            }
        }
    }

    public void unLockGpsButtonState() {
        a();
    }
}
