package com.autonavi.minimap.route.sharebike.presenter;

import android.text.TextUtils;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.sdk.location.LocationInstrument;

public class ShareBikePresenter$18 implements Callback<ReverseGeocodeResponser> {
    final /* synthetic */ ehd a;

    public ShareBikePresenter$18(ehd ehd) {
        this.a = ehd;
    }

    public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
        this.a.y = reverseGeocodeResponser.getDesc();
        if (TextUtils.isEmpty(this.a.y)) {
            this.a.y = LocationInstrument.getInstance().getLatestPosition().getCity();
        }
        ehd.U(this.a);
    }

    public void error(Throwable th, boolean z) {
        this.a.y = LocationInstrument.getInstance().getLatestPosition().getCity();
        ehd.U(this.a);
    }
}
