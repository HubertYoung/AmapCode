package com.autonavi.minimap.route.bus.navidetail.model;

import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;

public class ReverseGeoCodeParser$1 implements Callback<ReverseGeocodeResponser> {
    final /* synthetic */ a a;
    final /* synthetic */ dww b;

    public ReverseGeoCodeParser$1(dww dww, a aVar) {
        this.b = dww;
        this.a = aVar;
    }

    public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
        if (reverseGeocodeResponser == null || reverseGeocodeResponser.errorCode != 1) {
            if (this.a != null) {
                this.a.a(3, "");
            }
        } else if (this.a != null) {
            this.a.a(1, reverseGeocodeResponser.getDesc());
        }
    }

    public void error(Throwable th, boolean z) {
        if (this.a != null) {
            this.a.a(3, "");
        }
    }
}
