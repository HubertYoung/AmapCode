package com.autonavi.minimap.route.foot.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.autonavi.bundle.footresult.ajx.ModuleFoot;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ajx.inter.OnAjxFootPreviewInterface;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;
import com.autonavi.minimap.route.ajx.inter.UnLockGpsButtonInterface;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxFootBrowserPage extends Ajx3Page implements OnAjxFootPreviewInterface, OnErrorReportClickInterface, UnLockGpsButtonInterface {
    private edt a;
    /* access modifiers changed from: private */
    public ModuleFoot b;
    private int c = -1;

    static class JsLoadCallback implements Callback<AmapAjxView> {
        private WeakReference<AjxFootBrowserPage> mPageRef;

        public void error(Throwable th, boolean z) {
        }

        JsLoadCallback(AjxFootBrowserPage ajxFootBrowserPage) {
            this.mPageRef = new WeakReference<>(ajxFootBrowserPage);
        }

        public void callback(AmapAjxView amapAjxView) {
            AjxFootBrowserPage ajxFootBrowserPage = (AjxFootBrowserPage) this.mPageRef.get();
            if (ajxFootBrowserPage != null && ajxFootBrowserPage.isAlive() && amapAjxView != null) {
                ajxFootBrowserPage.b = (ModuleFoot) amapAjxView.getJsModule(ModuleFoot.MODULE_NAME);
                if (ajxFootBrowserPage.b != null) {
                    ajxFootBrowserPage.b.setUnLockGpsBtnListener(ajxFootBrowserPage);
                    ajxFootBrowserPage.b.setOnAjxPreviewListener(ajxFootBrowserPage);
                }
                eav.a("performance-", "AjxFootBrowserPage JsLoadCallback");
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        eav.a("performance-", "AjxFootBrowserPage  onCreate");
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
        eav.a("performance-", "showFootBrowserPage");
        eav.a("performance-", "AjxFootBrowserPage onCreateView");
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

    public void resume() {
        super.resume();
        eav.a("performance-", "AjxFootBrowserPage  resume");
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
                dys.a((String) "P00031", (String) "B002", (JSONObject) null);
                PageBundle a2 = ebc.a(getContext(), str, errorReportData);
                a2.putInt("sourcepage", 16);
                a2.putInt("page_id", 7);
                a2.putInt("route_line_type", 1);
                IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                if (iErrorReportStarter != null) {
                    iErrorReportStarter.startFeedback(a2);
                }
            }
        }
    }

    public void unLockGpsButtonState() {
        a();
    }

    public void onIndoorFloorChange(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                int optInt = new JSONObject(str).optInt("floor");
                if (getSuspendManager() != null && getSuspendManager().c().c()) {
                    getSuspendManager().c().b(optInt);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
