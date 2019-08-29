package com.autonavi.map.search.tip;

import android.text.TextUtils;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;

class GpsTipView$GpsLocateRequestThread$3 implements Callback<ReverseGeocodeResponser> {
    final /* synthetic */ a a;

    GpsTipView$GpsLocateRequestThread$3(a aVar) {
        this.a = aVar;
    }

    public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
        this.a.c = null;
        if (!TextUtils.isEmpty(reverseGeocodeResponser.getDesc())) {
            this.a.e = reverseGeocodeResponser.getDesc();
            if (GpsTipView.this.poi != null) {
                GpsTipView.this.poi.setAddr(this.a.e);
            }
        }
        this.a.d.clear();
        this.a.d.addAll(reverseGeocodeResponser.getPoiList());
        String str = "";
        if (!this.a.d.isEmpty()) {
            str = ((POI) this.a.d.get(0)).getName();
        }
        GpsTipView.this.setViceTitle(str);
    }

    public void error(Throwable th, boolean z) {
        this.a.c = null;
    }
}
