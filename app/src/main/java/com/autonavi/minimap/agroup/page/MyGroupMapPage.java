package com.autonavi.minimap.agroup.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import com.amap.bundle.maplayer.api.VMapPageConfig;
import com.autonavi.bundle.agroup.ajx.ModuleAgroup;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

@VMapPageConfig(name = "Member")
public class MyGroupMapPage extends Ajx3Page implements launchModeSingleTask {
    private View a;
    /* access modifiers changed from: private */
    public View b;
    /* access modifiers changed from: private */
    public ModuleAgroup c;
    private int d;

    static class JsLoadCallback implements Callback<AmapAjxView> {
        private WeakReference<MyGroupMapPage> mPageWeakRef;

        public void error(Throwable th, boolean z) {
        }

        JsLoadCallback(MyGroupMapPage myGroupMapPage) {
            this.mPageWeakRef = new WeakReference<>(myGroupMapPage);
        }

        public void callback(AmapAjxView amapAjxView) {
            MyGroupMapPage myGroupMapPage = (MyGroupMapPage) this.mPageWeakRef.get();
            if (myGroupMapPage != null && myGroupMapPage.isAlive()) {
                myGroupMapPage.c = (ModuleAgroup) amapAjxView.getJsModule("agroup");
                if (myGroupMapPage.c != null) {
                    myGroupMapPage.c.setOpenAnimateStatueListener(new a(myGroupMapPage));
                }
            }
        }
    }

    static class a implements com.autonavi.bundle.agroup.ajx.ModuleAgroup.a {
        private WeakReference<MyGroupMapPage> a;

        a(MyGroupMapPage myGroupMapPage) {
            this.a = new WeakReference<>(myGroupMapPage);
        }

        public final void a(String str) {
            MyGroupMapPage myGroupMapPage = (MyGroupMapPage) this.a.get();
            if (myGroupMapPage != null && myGroupMapPage.isAlive() && myGroupMapPage.b != null) {
                try {
                    String optString = new JSONObject(str).optString("reason", "1");
                    if (TextUtils.equals(optString, "1")) {
                        myGroupMapPage.b.setVisibility(0);
                        return;
                    }
                    if (TextUtils.equals(optString, "2")) {
                        myGroupMapPage.b.setVisibility(8);
                    }
                } catch (JSONException unused) {
                }
            }
        }
    }

    public Ajx3PagePresenter createPresenter() {
        return new cjn(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        defpackage.ciu.a.a.a(getClass(), new ciz());
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.addView(new MapInteractiveRelativeLayout(getContext()), new LayoutParams(-1, -1));
        frameLayout.addView(amapAjxView, new LayoutParams(-1, -1));
        amapAjxView.onAjxContextCreated(new JsLoadCallback(this));
        return frameLayout;
    }

    public void resume() {
        super.resume();
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.e(true);
            mapView.d(true);
        }
    }

    public void pause() {
        super.pause();
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.B();
            mapView.z();
        }
    }

    public void start() {
        super.start();
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            this.d = mapView.k(true);
            mapView.a(mapView.p(false), mapView.ae(), 1);
        }
    }

    public void stop() {
        super.stop();
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            mapView.a(bin.a.p("101"), mapView.ae(), this.d);
        }
    }

    public void destroy() {
        super.destroy();
    }

    public View getMapSuspendView() {
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        ccv ccv = new ccv(AMapPageUtil.getAppContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        layoutParams.leftMargin = agn.a(getContext(), 48.0f);
        layoutParams.bottomMargin = agn.a(getContext(), 171.0f);
        this.b = suspendWidgetHelper.f();
        this.b.setVisibility(8);
        ccv.addWidget(this.b, layoutParams, 7);
        this.a = ccv.getSuspendView();
        return this.a;
    }
}
