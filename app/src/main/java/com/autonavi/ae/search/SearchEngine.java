package com.autonavi.ae.search;

import com.autonavi.ae.bl.search.BLSearchCondition;
import com.autonavi.ae.bl.search.BLSearchCondition.RoadId;
import com.autonavi.ae.bl.search.BLSearchEngine;
import com.autonavi.ae.bl.search.BLSearchObserver;
import com.autonavi.ae.bl.search.BLSearchRequest;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GADAREAEXTRAINFO;
import com.autonavi.ae.search.model.GObjectID;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.jni.ae.bl.Parcel;

public class SearchEngine extends NativeSearchEngine {
    private final String TAG = "BLSearchEngine";
    private BLSearchEngine mBLSearchEngine = new BLSearchEngine();

    public class GSearchForm {
        public static final int GSEARCH_FORM_FINAL = 2;
        public static final int GSEARCH_FORM_MAX = 3;
        public static final int GSEARCH_FORM_NONE = 0;
        public static final int GSEARCH_FORM_PRE = 1;

        public GSearchForm() {
        }
    }

    public int init() {
        return 0;
    }

    public boolean isInit() {
        return true;
    }

    public int searchAdareaInfo(String str) {
        return 0;
    }

    public int setMccPath(String str) {
        return 0;
    }

    public int destroy() {
        this.mBLSearchEngine.destroy();
        return 0;
    }

    public int cancelQuery() {
        this.mBLSearchEngine.abortAll();
        return 0;
    }

    public int startSearch(int i, String str, int i2, float f, float f2, int i3, int i4, GObjectID[] gObjectIDArr) {
        return startSearch(i, str, i2, f, f2, i3, i4, gObjectIDArr, null);
    }

    public int preSearch(int i, String str, int i2, float f, float f2, int i3, OnSearchResultListener onSearchResultListener) {
        return startSearch(i, 1, str, i2, f, f2, i3, onSearchResultListener);
    }

    public int startSearch(int i, String str, int i2, float f, float f2, int i3, OnSearchResultListener onSearchResultListener) {
        return startSearch(i, 2, str, i2, f, f2, i3, onSearchResultListener);
    }

    public int startSearch(int i, int i2, String str, int i3, float f, float f2, int i4, OnSearchResultListener onSearchResultListener) {
        BLSearchCondition bLSearchCondition = new BLSearchCondition();
        bLSearchCondition.searchType = i;
        bLSearchCondition.searchForm = i2;
        bLSearchCondition.keyword = str;
        bLSearchCondition.locatedAdminCode = i3;
        bLSearchCondition.lon = (int) (f * 1000000.0f);
        bLSearchCondition.lat = (int) (f2 * 1000000.0f);
        bLSearchCondition.resultMaxCount = i4;
        startSearch(bLSearchCondition, onSearchResultListener);
        return 0;
    }

    private int startSearch(BLSearchCondition bLSearchCondition, final OnSearchResultListener onSearchResultListener) {
        this.mBLSearchEngine.search(BLSearchRequest.create(bLSearchCondition, new BLSearchObserver() {
            public void onSearchStatusChanged(BLSearchRequest bLSearchRequest, Parcel parcel) {
                onSearchResultListener.onGetSearchResult(0, new GPoiResult(parcel));
            }
        }));
        return 0;
    }

    public int startSearch(int i, String str, int i2, float f, float f2, int i3, int i4, GObjectID[] gObjectIDArr, OnSearchResultListener onSearchResultListener) {
        BLSearchCondition bLSearchCondition = new BLSearchCondition();
        bLSearchCondition.searchType = i;
        bLSearchCondition.keyword = str;
        bLSearchCondition.locatedAdminCode = i2;
        bLSearchCondition.lon = (int) (f * 1000000.0f);
        bLSearchCondition.lat = (int) (f2 * 1000000.0f);
        bLSearchCondition.resultMaxCount = i3;
        if (i4 > 0) {
            bLSearchCondition.guideRoads = new RoadId[i4];
            for (int i5 = 0; i5 < i4; i5++) {
                RoadId roadId = new RoadId();
                roadId.urId = gObjectIDArr[i5].getU16AdareaID();
                roadId.tileId = (int) gObjectIDArr[i5].getUnMeshID();
                roadId.roadId = (int) gObjectIDArr[i5].getUnObjectID();
                bLSearchCondition.guideRoads[i5] = roadId;
            }
        }
        startSearch(bLSearchCondition, onSearchResultListener);
        return 0;
    }

    public int searchNearestPoi(float f, float f2, OnSearchResultListener onSearchResultListener) {
        return startSearch(4, 2, (String) null, 0, f, f2, 10, onSearchResultListener);
    }

    public int getPoiDetail(String str, float f, float f2, OnSearchResultListener onSearchResultListener) {
        return startSearch(5, 2, str, 0, f, f2, 10, onSearchResultListener);
    }

    public static boolean isExistByAdCode(int i) {
        return BLSearchEngine.dbExists(i);
    }

    public static synchronized String getDataVersion(int i) {
        String dataVersion;
        synchronized (SearchEngine.class) {
            dataVersion = BLSearchEngine.getDataVersion(i);
        }
        return dataVersion;
    }

    public static synchronized String getEngineVersion() {
        String sdkVersion;
        synchronized (SearchEngine.class) {
            try {
                sdkVersion = BLSearchEngine.getSdkVersion();
            }
        }
        return sdkVersion;
    }

    public GADAREAEXTRAINFO GetAdareaInfo(int i) {
        return new GADAREAEXTRAINFO(BLSearchEngine.getAreaExtraInfo(i));
    }
}
