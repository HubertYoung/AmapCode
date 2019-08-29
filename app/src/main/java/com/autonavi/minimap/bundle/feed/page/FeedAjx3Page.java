package com.autonavi.minimap.bundle.feed.page;

import android.graphics.Rect;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.uitemplate.mapwidget.inter.LogVersionType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchWidgetPresenter;
import com.autonavi.bundle.uitemplate.tab.TabAjx3Page;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedAjx3Page extends TabAjx3Page implements bgm, bgo {
    /* access modifiers changed from: private */
    public View b;

    public void finishSelf() {
    }

    public bgo getPresenter() {
        return this;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 281474976710656L;
    }

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public final void t() {
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        final RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setBackgroundColor(-1);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(10);
        this.mAjxView.setLayoutParams(layoutParams);
        relativeLayout.addView(this.mAjxView);
        this.b = getLayoutInflater().inflate(R.layout.feed_loading, null);
        this.b.setLayoutParams(new LayoutParams(-1, -1));
        relativeLayout.addView(this.b);
        this.b.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
            }
        });
        this.mAjxView.setLoadingCallback(new Callback<AmapAjxView>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(AmapAjxView amapAjxView) {
                if (relativeLayout != null && FeedAjx3Page.this.b != null) {
                    FeedAjx3Page.this.b.setVisibility(8);
                }
            }
        });
        return relativeLayout;
    }

    public final void s() {
        result(0, ResultType.OK, a());
    }

    private PageBundle a() {
        Rect rect;
        GLGeoPoint gLGeoPoint;
        JSONObject jSONObject = null;
        try {
            bty mapView = getMapView();
            int zoomLevel = NearBySearchWidgetPresenter.getZoomLevel();
            if (mapView != null) {
                rect = mapView.H();
                gLGeoPoint = mapView.n();
            } else {
                rect = new Rect();
                gLGeoPoint = LocationInstrument.getInstance().getLatestPosition();
            }
            JSONObject generateNewFeedData = NearBySearchWidgetPresenter.generateNewFeedData(zoomLevel, rect, gLGeoPoint);
            try {
                generateNewFeedData.put("from", "tab");
                jSONObject = generateNewFeedData;
            } catch (Exception e) {
                JSONObject jSONObject2 = generateNewFeedData;
                e = e;
                jSONObject = jSONObject2;
                e.printStackTrace();
                PageBundle pageBundle = NearBySearchWidgetPresenter.getPageBundle(Ajx3Path.FEED_PATH, b(), jSONObject);
                pageBundle.putString(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject.toString());
                return pageBundle;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            PageBundle pageBundle2 = NearBySearchWidgetPresenter.getPageBundle(Ajx3Path.FEED_PATH, b(), jSONObject);
            pageBundle2.putString(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject.toString());
            return pageBundle2;
        }
        PageBundle pageBundle22 = NearBySearchWidgetPresenter.getPageBundle(Ajx3Path.FEED_PATH, b(), jSONObject);
        pageBundle22.putString(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject.toString());
        return pageBundle22;
    }

    private static JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", LogVersionType.REDESIGN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
