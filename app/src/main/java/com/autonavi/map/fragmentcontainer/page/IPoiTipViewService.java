package com.autonavi.map.fragmentcontainer.page;

import android.view.ViewGroup;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter.IGPSTipView;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;

public interface IPoiTipViewService extends bie {
    IGPSTipView createGpsTipView(bid bid, cdx cdx);

    AbstractGpsTipView createGpsTipViewForPoiDetaiilDelegate(bid bid, cdx cdx);

    AbstractPoiDetailView createPoiDetailViewForCQ();

    a createPoiTipEvent(boolean z);

    ely createPoiTipView(ViewGroup viewGroup, bid bid, POI poi);
}
