package defpackage;

import android.view.ViewGroup;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter.IGPSTipView;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPoiTipViewService;
import com.autonavi.map.search.tip.GpsTipView;
import com.autonavi.map.search.tip.GpsTipViewForPoiDetailDelegate;
import com.autonavi.map.search.tip.SearchPoiTipWrapper;
import com.autonavi.map.search.view.PoiDetailViewForCQ;
import com.autonavi.map.search.view.PoiTipView;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;

/* renamed from: cbj reason: default package */
/* compiled from: PoiTipViewServiceImpl */
public class cbj implements IPoiTipViewService {
    public ely createPoiTipView(ViewGroup viewGroup, bid bid, POI poi) {
        if (poi == null || !poi.getPoiExtra().containsKey(IOverlayManager.POI_EXTRA_FROM_FAV)) {
            return new SearchPoiTipWrapper();
        }
        return new PoiTipView(viewGroup, bid);
    }

    public AbstractPoiDetailView createPoiDetailViewForCQ() {
        if (DoNotUseTool.getActivity() == null) {
            return null;
        }
        PoiDetailViewForCQ poiDetailViewForCQ = new PoiDetailViewForCQ(DoNotUseTool.getActivity());
        poiDetailViewForCQ.setIsPoiChildMark(false);
        return poiDetailViewForCQ;
    }

    public a createPoiTipEvent(boolean z) {
        return new ccj(z);
    }

    public IGPSTipView createGpsTipView(bid bid, cdx cdx) {
        return new GpsTipView(bid, cdx);
    }

    public AbstractGpsTipView createGpsTipViewForPoiDetaiilDelegate(bid bid, cdx cdx) {
        return new GpsTipViewForPoiDetailDelegate(bid, cdx);
    }
}
