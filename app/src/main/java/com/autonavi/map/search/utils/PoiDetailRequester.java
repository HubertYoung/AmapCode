package com.autonavi.map.search.utils;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.inter.ISearchManager;
import com.autonavi.minimap.search.utils.SearchUtils;
import java.util.List;

public final class PoiDetailRequester {
    Context a;
    private AosRequest b;

    class PoiDetailCallback implements Callback<List<POI>> {
        private Callback<POI> mCallback;
        private POI mPoi;

        private PoiDetailCallback(POI poi, Callback<POI> callback) {
            this.mCallback = callback;
            this.mPoi = poi;
        }

        public void callback(List<POI> list) {
            PoiDetailRequester poiDetailRequester = PoiDetailRequester.this;
            POI poi = this.mPoi;
            Callback<POI> callback = this.mCallback;
            SearchUtils.mOfflineSearchNearestPoi = false;
            if (list == null || list.size() <= 0) {
                callback.error(null, true);
                return;
            }
            POI poi2 = list.get(0);
            if (poi2 != null && !TextUtils.isEmpty(poi2.getName())) {
                SearchPoi searchPoi = (SearchPoi) poi2.as(SearchPoi.class);
                if (TextUtils.isEmpty(searchPoi.getId())) {
                    searchPoi.setPoint(poi.getPoint());
                }
                if (searchPoi.getTemplateData() == null && searchPoi.getTemplateDataMap() == null) {
                    bxz.a(poiDetailRequester.a, searchPoi);
                }
                callback.callback(searchPoi);
            }
        }

        public void error(Throwable th, boolean z) {
            PoiDetailRequester poiDetailRequester = PoiDetailRequester.this;
            POI poi = this.mPoi;
            Callback<POI> callback = this.mCallback;
            if (poi != null) {
                SearchUtils.mOfflineSearchNearestPoi = true;
                adz adz = (adz) a.a.a(adz.class);
                ank.a(ISearchManager.class);
                if (adz != null) {
                    ady b = adz.b();
                    if (b != null) {
                        b.a(poi.getPoint(), poi.getId(), (OnSearchResultListener) new OnSearchResultListener(b, callback) {
                            final /* synthetic */ ady a;
                            final /* synthetic */ Callback b;

                            {
                                this.a = r2;
                                this.b = r3;
                            }

                            public final void onGetSearchResult(int i, final GPoiResult gPoiResult) {
                                aho.a(new Runnable() {
                                    public final void run() {
                                        if (gPoiResult == null || gPoiResult.getPoiList() == null || gPoiResult.getPoiList().size() <= 0) {
                                            AnonymousClass1.this.b.error(null, false);
                                            return;
                                        }
                                        POI a2 = AnonymousClass1.this.a.a(gPoiResult.getPoiList().get(0));
                                        if (a2 != null && !TextUtils.isEmpty(a2.getName())) {
                                            bxz.a(PoiDetailRequester.this.a, a2);
                                            AnonymousClass1.this.b.callback(a2);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    }

    public PoiDetailRequester(Context context) {
        this.a = context;
    }

    public final void a() {
        if (this.b != null) {
            this.b.cancel();
            this.b = null;
        }
    }
}
