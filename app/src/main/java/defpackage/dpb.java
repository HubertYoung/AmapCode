package defpackage;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback;
import com.autonavi.minimap.search.utils.SearchUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dpb reason: default package */
/* compiled from: HeaderSearchOfflineSearcher */
public final class dpb {
    public final boolean a(String str, double d, double d2, final Callback<List<TipItem>> callback) {
        adz adz = (adz) a.a.a(adz.class);
        if (adz == null) {
            return false;
        }
        ady a = adz.a(String.valueOf(str), String.valueOf(d), String.valueOf(d2));
        if (a == null || !a.d() || !a.a(str)) {
            return false;
        }
        a.a((OnSearchResultListener) new OnSearchResultListener() {
            public final void onGetSearchResult(int i, GPoiResult gPoiResult) {
                ArrayList arrayList = new ArrayList(10);
                if (i != 0) {
                    ToastHelper.showToast("ErrorCode : ".concat(String.valueOf(i)));
                } else {
                    int i2 = 0;
                    List<GPoiBase> poiList = gPoiResult.getPoiList();
                    if (poiList != null && poiList.size() > 0) {
                        for (GPoiBase convertGPoiBase2TipItem : poiList) {
                            TipItem convertGPoiBase2TipItem2 = SearchUtils.convertGPoiBase2TipItem(convertGPoiBase2TipItem);
                            if (convertGPoiBase2TipItem2 != null) {
                                arrayList.add(convertGPoiBase2TipItem2);
                                i2++;
                                if (i2 > 10) {
                                    break;
                                }
                            }
                        }
                    }
                }
                callback.callback(arrayList);
            }
        });
        return true;
    }
}
