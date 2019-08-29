package com.autonavi.minimap.life.common.widget.view.headersearchview;

import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback;
import java.util.List;

public class HeaderSearchViewPresenter$3 implements Callback<List<TipItem>> {
    final /* synthetic */ dop a;

    public void error(Throwable th, boolean z) {
    }

    public HeaderSearchViewPresenter$3(dop dop) {
        this.a = dop;
    }

    public void callback(List<TipItem> list) {
        this.a.a.callbackRunOnUiThread(list);
    }
}
