package com.autonavi.map.search.tip;

import android.view.View;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.a;

public final class SearchPoiTipWrapper implements ely<InfoliteResult> {
    private POI a;
    private SearchPoiTipBaseView b;

    public enum Type {
        Normal,
        City_Card
    }

    public final void adjustMargin() {
    }

    public final void refreshByScreenState(boolean z) {
    }

    public final void setFromSource(String str) {
    }

    public final void setSingle(boolean z) {
    }

    public final /* synthetic */ void initData(Object obj, POI poi, int i) {
        InfoliteResult infoliteResult = (InfoliteResult) obj;
        if (poi != null) {
            this.a = poi;
            this.b.updateUI(infoliteResult, (SearchPoi) poi.as(SearchPoi.class), i);
        }
    }

    public SearchPoiTipWrapper() {
        this.b = new SearchPoiTipView(DoNotUseTool.getContext());
    }

    public SearchPoiTipWrapper(Type type, a aVar) {
        if (type == Type.City_Card) {
            this.b = new CityCardPoiTipView(DoNotUseTool.getContext(), aVar);
        } else {
            this.b = new SearchPoiTipView(DoNotUseTool.getContext());
        }
    }

    public final void a() {
        this.b.setIsPoiChildMark(true);
    }

    public final POI getPoi() {
        return this.a;
    }

    public final void setTipItemEvent(a aVar) {
        this.b.setTipItemEvent(aVar);
    }

    public final void a(cbr cbr) {
        this.b.setChildPoiClickListener(cbr);
    }

    public final void a(bxj bxj, SearchPoiTipView.a aVar) {
        this.b.setTipsHeightChangedListener(bxj, aVar);
    }

    public final View getView() {
        return this.b;
    }
}
