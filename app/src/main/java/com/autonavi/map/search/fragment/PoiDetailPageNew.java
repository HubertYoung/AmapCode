package com.autonavi.map.search.fragment;

import android.content.Context;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTop;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(clickable = false, overlay = UvOverlay.GpsOverlay, visible = true)})
@PageAction("amap.search.action.poidetail")
public class PoiDetailPageNew extends SearchResultBasePage implements launchModeSingleTop {
    public void finishSelf() {
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 17592187092992L;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final bxk a(SearchResultBasePage searchResultBasePage) {
        return new bxl(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
    }
}
