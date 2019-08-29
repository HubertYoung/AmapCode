package com.autonavi.minimap.route.navi.ModuleWrapper;

import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;

public class ReverseGeocodeWrapper$1 implements Callback<ReverseGeocodeResponser> {
    final /* synthetic */ edc a;

    public void error(Throwable th, boolean z) {
    }

    public ReverseGeocodeWrapper$1(edc edc) {
        this.a = edc;
    }

    public void callback(final ReverseGeocodeResponser reverseGeocodeResponser) {
        if (reverseGeocodeResponser != null) {
            aho.a(new Runnable() {
                public final void run() {
                    if (ReverseGeocodeWrapper$1.this.a.a != null) {
                        ReverseGeocodeWrapper$1.this.a.a.a(reverseGeocodeResponser.getDesc());
                    }
                }
            });
        }
    }
}
