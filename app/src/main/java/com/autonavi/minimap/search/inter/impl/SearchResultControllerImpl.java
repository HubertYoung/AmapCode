package com.autonavi.minimap.search.inter.impl;

import android.graphics.Rect;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.minimap.search.inter.ISearchResultController;

public class SearchResultControllerImpl implements ISearchResultController {
    public aea<InfoliteResult> getSearchCallBackEx(String str) {
        bvt bvt = new bvt();
        bvt.e = str;
        return bvt;
    }

    public aea<InfoliteResult> setSearchRect(Rect rect, String str) {
        bwx bwx = new bwx(str, -1, false);
        bvt bvt = new bvt();
        bvt.e = str;
        bvt.d = bwx;
        bvt.e = str;
        return bvt;
    }

    public void setSearchRect(aea aea, Rect rect) {
        if (aea != null && (aea instanceof bvt)) {
            ((bvt) aea).a(rect);
        }
    }

    public aea<InfoliteResult> setSearchResultListener(String str, int i, boolean z, boolean z2) {
        bvt bvt = new bvt();
        bwx bwx = new bwx(str, i, z);
        bwx.l = z2;
        bvt.e = str;
        bvt.d = bwx;
        return bvt;
    }
}
