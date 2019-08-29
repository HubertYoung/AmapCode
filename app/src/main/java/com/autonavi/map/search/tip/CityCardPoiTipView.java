package com.autonavi.map.search.tip;

import android.content.Context;
import android.widget.TextView;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.a;
import com.autonavi.minimap.R;

class CityCardPoiTipView extends SearchPoiTipBaseView {
    private TextView mTip_name;

    public CityCardPoiTipView(Context context, a aVar) {
        super(context);
        setContentView(R.layout.poi_layout_citycard_tip);
        initViews();
    }

    private void initViews() {
        this.mTip_name = (TextView) findViewById(R.id.tip_name);
    }

    public void update(InfoliteResult infoliteResult, SearchPoi searchPoi) {
        this.mTip_name.setText(searchPoi.getName());
    }
}
