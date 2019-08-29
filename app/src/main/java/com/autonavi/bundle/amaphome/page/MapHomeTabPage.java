package com.autonavi.bundle.amaphome.page;

import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.user.UserCenterWidgetPresenter;
import com.autonavi.bundle.uitemplate.tab.TabHostPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.bundle.feed.page.FeedAjx3Page;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.basemap.action.default_page")
public class MapHomeTabPage extends TabHostPage<arp> implements bgm, a, launchModeSingleTask, IAMapHomePage, emc {
    private String g;
    private String h;
    private String i;
    /* access modifiers changed from: private */
    public boolean j;
    private Handler k;

    /* access modifiers changed from: private */
    /* renamed from: i */
    public arp createPresenter() {
        return new arp(this);
    }

    public final List<bep> a() {
        ArrayList arrayList = new ArrayList(3);
        bep bep = new bep(R.drawable.amaphome_tab_line, getResources().getText(R.string.amaphome_tab_maphome), R.drawable.amaphome_tab_txt_color, MapHomePage.class);
        bep.e = true;
        this.g = bep.d;
        bep bep2 = new bep(R.drawable.amaphome_tab_line, getResources().getText(R.string.amaphome_tab_nearby), R.drawable.amaphome_tab_txt_color, FeedAjx3Page.class);
        this.h = bep2.d;
        bep bep3 = new bep(R.drawable.amaphome_tab_line, getResources().getText(R.string.amaphome_tab_mine), R.drawable.amaphome_tab_txt_color, MineAjx3Page.class);
        this.i = bep3.d;
        arrayList.add(bep);
        arrayList.add(bep2);
        arrayList.add(bep3);
        return arrayList;
    }

    public final boolean a(PageBundle pageBundle) {
        if (pageBundle == null || !pageBundle.containsKey(a)) {
            bep a = a(this.g);
            if (!(a == null || this.e == a)) {
                a(a, pageBundle);
                return true;
            }
        }
        return super.a(pageBundle);
    }

    public final boolean u() {
        return this.d instanceof MapHomePage;
    }

    public final PageBundle a(bep bep) {
        JSONObject jSONObject;
        GLGeoPoint gLGeoPoint;
        Rect rect;
        if (TextUtils.equals(this.g, bep.d)) {
            return null;
        }
        if (TextUtils.equals(this.h, bep.d)) {
            try {
                bty mapView = NearBySearchWidgetPresenter.getMapView();
                int zoomLevel = NearBySearchWidgetPresenter.getZoomLevel();
                if (mapView != null) {
                    rect = mapView.H();
                    gLGeoPoint = mapView.n();
                } else {
                    rect = new Rect();
                    gLGeoPoint = LocationInstrument.getInstance().getLatestPosition();
                }
                jSONObject = NearBySearchWidgetPresenter.generateNewFeedData(zoomLevel, rect, gLGeoPoint);
            } catch (Exception e) {
                e.printStackTrace();
                jSONObject = null;
            }
            return NearBySearchWidgetPresenter.getPageBundle(Ajx3Path.FEED_PATH, j(), jSONObject);
        } else if (!TextUtils.equals(this.i, bep.d)) {
            return null;
        } else {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", Ajx3Path.BASEMAP_MINE);
            new bnv();
            pageBundle.putString("jsData", bnv.b());
            return pageBundle;
        }
    }

    private static JSONObject j() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", LogVersionType.REDESIGN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public final void b(bep bep) {
        super.b(bep);
        HashMap hashMap = new HashMap();
        hashMap.put("from", new arx().a().getStringValue("userIndividualityType", ""));
        if (TextUtils.equals(this.h, bep.d)) {
            bty mapView = NearBySearchWidgetPresenter.getMapView();
            NearBySearchWidgetPresenter.updateLog(NearBySearchWidgetPresenter.generateNewFeedData(NearBySearchWidgetPresenter.getZoomLevel(), mapView.H(), mapView.n()));
            kd.a((String) "amap.P00001.0.B007", (Map<String, String>) hashMap);
            return;
        }
        if (TextUtils.equals(this.i, bep.d)) {
            UserCenterWidgetPresenter.updateLogWhenStartUserPage();
            kd.a((String) "amap.P00001.0.B001", (Map<String, String>) hashMap);
        }
    }

    public final boolean c(bep bep) {
        if (!TextUtils.equals(this.i, bep.d)) {
            return super.c(bep);
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.setFlags(16);
        startPage((String) "amap.basemap.action.config_page", pageBundle);
        return true;
    }

    public final boolean a(bep bep, MotionEvent motionEvent) {
        if (TextUtils.equals(this.i, bep.d)) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.j = false;
                    if (this.k == null) {
                        this.k = new Handler();
                    }
                    this.k.postDelayed(new Runnable() {
                        public final void run() {
                            MapHomeTabPage.this.j = true;
                            bid pageContext = AMapPageUtil.getPageContext();
                            if (pageContext != null) {
                                pageContext.startPage((String) "amap.basemap.action.config_page", (PageBundle) null);
                            }
                        }
                    }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    break;
                case 1:
                    if (this.k != null) {
                        this.k.removeCallbacksAndMessages(null);
                        this.k = null;
                    }
                    if (this.j) {
                        return true;
                    }
                    break;
            }
        }
        return super.a(bep, motionEvent);
    }

    public final void a(bep bep, bep bep2) {
        super.a(bep, bep2);
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            bfo.h();
            bfo.g();
        }
    }

    public bgo getPresenter() {
        if (this.c instanceof bgm) {
            return ((bgm) this.c).getPresenter();
        }
        return null;
    }

    public long getScenesID() {
        if (this.c instanceof bgm) {
            return ((bgm) this.c).getScenesID();
        }
        return 0;
    }

    public JSONObject getScenesData() {
        if (this.c instanceof bgm) {
            return ((bgm) this.c).getScenesData();
        }
        return null;
    }

    public boolean needKeepSessionAlive() {
        if (this.c instanceof bgm) {
            return ((bgm) this.c).needKeepSessionAlive();
        }
        return false;
    }

    public void finishSelf() {
        if (this.c instanceof bgm) {
            ((bgm) this.c).finishSelf();
        }
    }

    public boolean isInnerPage() {
        if (this.c instanceof bgm) {
            return ((bgm) this.c).isInnerPage();
        }
        return false;
    }

    public boolean isMapHomePage() {
        return this.d instanceof MapHomePage;
    }

    public void onAnimationStarted(boolean z) {
        super.onAnimationStarted(z);
        this.d.onAnimationStarted(z);
    }

    public void onAnimationFinished(boolean z) {
        super.onAnimationFinished(z);
        this.d.onAnimationFinished(z);
    }
}
