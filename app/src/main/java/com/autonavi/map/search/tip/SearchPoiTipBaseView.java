package com.autonavi.map.search.tip;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.util.TipLogHelper;
import com.autonavi.map.search.tip.SearchPoiTipView.a;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import java.util.ArrayList;

abstract class SearchPoiTipBaseView extends FrameLayout {
    protected cbt mActionLog = new cbt();
    protected cbu mBehavior = new cbu(this);

    /* access modifiers changed from: protected */
    public abstract void update(InfoliteResult infoliteResult, SearchPoi searchPoi);

    public SearchPoiTipBaseView(Context context) {
        super(context);
    }

    public void setIsPoiChildMark(boolean z) {
        this.mActionLog.d = z;
    }

    /* access modifiers changed from: protected */
    public void setContentView(@LayoutRes int i) {
        LayoutInflater.from(getContext()).inflate(i, this);
    }

    public void setTipItemEvent(a aVar) {
        this.mBehavior.d = aVar;
    }

    public final void updateUI(InfoliteResult infoliteResult, SearchPoi searchPoi, int i) {
        this.mBehavior.a(infoliteResult, searchPoi, i);
        cbt cbt = this.mActionLog;
        cbt.a = infoliteResult;
        cbt.b = searchPoi;
        if (i >= 0) {
            if (TipLogHelper.a(searchPoi)) {
                i = 0;
            }
            cbt.e = i;
        }
        SearchPoi searchPoi2 = cbt.b;
        ArrayList arrayList = null;
        if (searchPoi2 != null) {
            ChildrenPoiData poiChildrenInfo = searchPoi2.getPoiChildrenInfo();
            if (poiChildrenInfo != null) {
                arrayList = new ArrayList();
                if (poiChildrenInfo.childType == 2 && poiChildrenInfo.poiList != null) {
                    arrayList.addAll(poiChildrenInfo.poiList);
                } else if (poiChildrenInfo.childType == 1 && poiChildrenInfo.stationList != null) {
                    arrayList.addAll(poiChildrenInfo.stationList);
                }
            }
        }
        cbt.c = arrayList;
        if (cbt.c == null) {
            cbt.c = new ArrayList();
        }
        update(infoliteResult, searchPoi);
    }

    public void setChildPoiClickListener(cbr cbr) {
        this.mBehavior.e = cbr;
    }

    public void setTipsHeightChangedListener(bxj bxj, a aVar) {
        cbu cbu = this.mBehavior;
        cbu.f = aVar;
        cbu.g = bxj;
    }
}
