package com.amap.bundle.planhome.page;

import android.content.Context;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;

public abstract class AbstractPlanHomePage extends AbstractBasePage<IPresenter> {
    public float a = 0.0f;
    public float b = 16.0f;
    public float c = 0.0f;
    public GeoPoint d = LocationInstrument.getInstance().getLatestPosition();

    public IPresenter createPresenter() {
        return new add(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.plan_page);
    }
}
