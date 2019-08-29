package com.autonavi.bundle.uitemplate.mapwidget.widget.homecorp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.bedstone.model.FrequentLocationDBInfo;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bedstone.model.FrequentLocationInfo;
import com.autonavi.minimap.bundle.frequentlocation.entity.FrequentLocationData;
import com.autonavi.minimap.bundle.frequentlocation.util.FrequentLocationInfoEx;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class HomeCorpWidgetPresenter extends BaseMapWidgetPresenter<HomeCorpMapWidget> implements cyg {
    public static final String CLOUD_SYNC_DATA_KEY = "value";
    public static final int REQUEST_CODE_ADD_POI = 2000;
    public static final int REQUEST_CODE_EDIT_COMPANY = 1001;
    public static final int REQUEST_CODE_EDIT_HOME = 1000;
    FrequentLocationData mCompanyLocationData;
    private Context mContext;
    FrequentLocationData mEditData;
    /* access modifiers changed from: private */
    public cyn mFrequentLocationView;
    /* access modifiers changed from: private */
    public List<FrequentLocationData> mFrequentLocations = new ArrayList();
    cyj mFrequentLocationsUtil = new cyj();
    FrequentLocationData mHomeLocationData;
    /* access modifiers changed from: private */
    public LoadDataEndRunnable mLoadDataEndUIRunnable;
    private LoadDataRunnable mLoadDataRunnable;
    /* access modifiers changed from: private */
    public volatile boolean mLoadDataRunning = false;
    /* access modifiers changed from: private */
    public bid mPageContext;
    FrequentLocationData mSearchData;
    /* access modifiers changed from: private */
    public volatile int token_load_data = 0;

    class LoadDataEndRunnable implements Runnable {
        /* access modifiers changed from: private */
        public boolean needAnim;

        private LoadDataEndRunnable() {
            this.needAnim = true;
        }

        public void run() {
            if (HomeCorpWidgetPresenter.this.mPageContext != null && HomeCorpWidgetPresenter.this.mPageContext.isAlive()) {
                HomeCorpWidgetPresenter.this.mLoadDataRunning = false;
                HomeCorpWidgetPresenter.this.mFrequentLocationView.a(HomeCorpWidgetPresenter.this.mFrequentLocations, HomeCorpWidgetPresenter.this.mHomeLocationData, HomeCorpWidgetPresenter.this.mCompanyLocationData, HomeCorpWidgetPresenter.this.mSearchData, HomeCorpWidgetPresenter.this.mEditData, this.needAnim);
                this.needAnim = true;
            }
        }
    }

    class LoadDataRunnable implements Runnable {
        private volatile int mLoadDataToken;

        private LoadDataRunnable() {
            this.mLoadDataToken = HomeCorpWidgetPresenter.this.token_load_data;
        }

        public void run() {
            FrequentLocationData frequentLocationData;
            FrequentLocationData frequentLocationData2;
            if (HomeCorpWidgetPresenter.this.mPageContext != null && HomeCorpWidgetPresenter.this.mPageContext.isAlive() && !HomeCorpWidgetPresenter.this.mLoadDataRunning) {
                HomeCorpWidgetPresenter.this.mLoadDataRunning = true;
                HomeCorpWidgetPresenter.this.mFrequentLocations.clear();
                String str = "";
                coy coy = (coy) ank.a(coy.class);
                if (coy != null) {
                    str = coy.a();
                }
                com com2 = (com) ank.a(com.class);
                if (com2 != null) {
                    cop b = com2.b(str);
                    FavoritePOI c = b.c();
                    FavoritePOI d = b.d();
                    ArrayList arrayList = new ArrayList();
                    if (c != null) {
                        String name = c.getName();
                        if (!TextUtils.isEmpty(name)) {
                            arrayList.add(name);
                        }
                    }
                    if (c != null) {
                        frequentLocationData = FrequentLocationData.a(c, null);
                    } else {
                        frequentLocationData = FrequentLocationData.a();
                    }
                    frequentLocationData.a = 1001;
                    HomeCorpWidgetPresenter.this.mHomeLocationData = frequentLocationData;
                    if (d != null) {
                        String name2 = d.getName();
                        if (!TextUtils.isEmpty(name2)) {
                            arrayList.add(name2);
                        }
                    }
                    if (d != null) {
                        frequentLocationData2 = FrequentLocationData.a(d, null);
                    } else {
                        frequentLocationData2 = FrequentLocationData.a();
                    }
                    frequentLocationData2.a = 1002;
                    HomeCorpWidgetPresenter.this.mCompanyLocationData = frequentLocationData2;
                    List<FrequentLocationInfoEx> access$600 = HomeCorpWidgetPresenter.this.getCloudSyncData();
                    if (access$600 != null) {
                        for (FrequentLocationInfoEx frequentLocationInfoEx : access$600) {
                            POI a = FrequentLocationData.a((FrequentLocationInfo) frequentLocationInfoEx);
                            if (a != null) {
                                FrequentLocationData a2 = FrequentLocationData.a(a, frequentLocationInfoEx.frequentRemark);
                                a2.b = true;
                                HomeCorpWidgetPresenter.this.mFrequentLocations.add(a2);
                                if (!TextUtils.isEmpty(a2.c())) {
                                    arrayList.add(a2.c());
                                }
                            }
                        }
                    }
                    List<FrequentLocationDBInfo> b2 = cyc.e().b(cyi.a(arrayList));
                    if (!b2.isEmpty()) {
                        HomeCorpWidgetPresenter.this.mFrequentLocations.addAll(HomeCorpWidgetPresenter.this.covertData(b2));
                    }
                    if (!HomeCorpWidgetPresenter.this.justHomeCompanyData()) {
                        FrequentLocationData a3 = FrequentLocationData.a();
                        a3.a = 10003;
                        HomeCorpWidgetPresenter.this.mSearchData = a3;
                        FrequentLocationData a4 = FrequentLocationData.a();
                        a4.a = 10004;
                        HomeCorpWidgetPresenter.this.mEditData = a4;
                    }
                    if (this.mLoadDataToken == HomeCorpWidgetPresenter.this.token_load_data) {
                        aho.a(HomeCorpWidgetPresenter.this.mLoadDataEndUIRunnable);
                    } else {
                        HomeCorpWidgetPresenter.this.mLoadDataRunning = false;
                    }
                }
            }
        }

        public void syncLoadDataToken() {
            this.mLoadDataToken = HomeCorpWidgetPresenter.this.token_load_data;
        }
    }

    public final void init(@NonNull bid bid) {
        this.mPageContext = bid;
        this.mContext = this.mPageContext.getContext();
        this.mLoadDataRunnable = new LoadDataRunnable();
        this.mLoadDataEndUIRunnable = new LoadDataEndRunnable();
    }

    public final void bindView(cyn cyn) {
        this.mFrequentLocationView = cyn;
    }

    public final void loadData() {
        cancelLoadData();
        this.token_load_data++;
        this.mLoadDataRunnable.syncLoadDataToken();
        ahm.a(this.mLoadDataRunnable);
    }

    public final void cancelLoadData() {
        this.token_load_data++;
        ahm.b(this.mLoadDataRunnable);
        aho.b(this.mLoadDataEndUIRunnable);
        this.mLoadDataRunning = false;
    }

    public final void onAddPoi() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("search_for", 2);
        pageBundle.putBoolean("isHideMyPosition", true);
        pageBundle.putString("hint", this.mContext.getString(R.string.act_search_poi_bar));
        this.mPageContext.startPageForResult((String) "search.fragment.SearchCallbackFragment", pageBundle, 2000);
    }

    public final void onEditModeChanged(boolean z) {
        refreshHeader();
        refreshFooter();
    }

    public final void refreshHeader() {
        this.mFrequentLocationView.a(this.mHomeLocationData, this.mCompanyLocationData);
    }

    private void reloadHeader() {
        FrequentLocationData frequentLocationData;
        FrequentLocationData frequentLocationData2;
        String str = "";
        coy coy = (coy) ank.a(coy.class);
        if (coy != null) {
            str = coy.a();
        }
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(str);
            FavoritePOI c = b.c();
            FavoritePOI d = b.d();
            if (c != null) {
                frequentLocationData = FrequentLocationData.a(c, null);
            } else {
                frequentLocationData = FrequentLocationData.a();
            }
            frequentLocationData.a = 1001;
            this.mHomeLocationData = frequentLocationData;
            if (d != null) {
                frequentLocationData2 = FrequentLocationData.a(d, null);
            } else {
                frequentLocationData2 = FrequentLocationData.a();
            }
            frequentLocationData2.a = 1002;
            this.mCompanyLocationData = frequentLocationData2;
            refreshHeader();
        }
    }

    public final void refreshFooter() {
        this.mFrequentLocationView.b(this.mSearchData, this.mEditData);
    }

    public final void updateData(int i, PageBundle pageBundle) {
        POI searchResultPoi = getSearchResultPoi(pageBundle);
        String str = "";
        coy coy = (coy) ank.a(coy.class);
        if (coy != null) {
            str = coy.a();
        }
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(str);
            switch (i) {
                case 1001:
                    if (searchResultPoi != null) {
                        b.f(searchResultPoi);
                        break;
                    }
                    break;
                case 1002:
                    if (searchResultPoi != null) {
                        b.e(searchResultPoi);
                        break;
                    }
                    break;
                default:
                    return;
            }
            this.mLoadDataEndUIRunnable.needAnim = false;
            reloadHeader();
        }
    }

    public final void editLocation(int i) {
        PageBundle pageBundle;
        int i2;
        switch (i) {
            case 1001:
                pageBundle = new PageBundle();
                i2 = 1000;
                pageBundle.putString("address", this.mContext.getString(R.string.home));
                pageBundle.putString("search_hint", this.mContext.getString(R.string.act_fromto_home_input_hint));
                FrequentLocationData homeData = getHomeData();
                if (homeData != null) {
                    pageBundle.putString(TrafficUtil.KEYWORD, homeData.c());
                    break;
                }
                break;
            case 1002:
                pageBundle = new PageBundle();
                i2 = 1001;
                pageBundle.putString("address", this.mContext.getString(R.string.company));
                pageBundle.putString("search_hint", this.mContext.getString(R.string.act_fromto_company_input_hint));
                FrequentLocationData companyData = getCompanyData();
                if (companyData != null) {
                    pageBundle.putString(TrafficUtil.KEYWORD, companyData.c());
                    break;
                }
                break;
            default:
                return;
        }
        this.mPageContext.startPageForResult((String) "amap.basemap.action.save_search_page", pageBundle, i2);
    }

    public final void selectPoiCallback(PageBundle pageBundle) {
        onSelectPoiCallback(getSearchResultPoi(pageBundle));
    }

    public final void planRoute(int i) {
        POI poi;
        FrequentLocationData safeData = getSafeData(i);
        if (safeData != null) {
            switch (safeData.a) {
                case 1000:
                    poi = safeData.b();
                    break;
                case 1001:
                    poi = safeData.b();
                    break;
                case 1002:
                    poi = safeData.b();
                    break;
                default:
                    return;
            }
            planRoute(poi);
        }
    }

    public final void planRoute(POI poi) {
        if (checkEndPoi(poi)) {
            startRoutePage(poi);
        }
    }

    public final void search() {
        this.mPageContext.startPage((String) "amap.search.action.searchfragment", new PageBundle());
    }

    /* access modifiers changed from: private */
    public List<FrequentLocationData> covertData(@NonNull List<FrequentLocationDBInfo> list) {
        ArrayList arrayList = new ArrayList();
        for (FrequentLocationDBInfo next : list) {
            if (next != null) {
                arrayList.add(FrequentLocationData.a(next));
            }
        }
        return arrayList;
    }

    public final void destroy() {
        cancelLoadData();
        this.mPageContext = null;
    }

    private boolean checkEndPoi(POI poi) {
        if (poi == null) {
            return false;
        }
        GeoPoint point = poi.getPoint();
        if (point == null || (point.getLatitude() == 0.0d && point.getLongitude() == 0.0d)) {
            return false;
        }
        return true;
    }

    @Nullable
    private FrequentLocationData getSafeData(int i) {
        if (i < 0 || i >= this.mFrequentLocations.size()) {
            return null;
        }
        return this.mFrequentLocations.get(i);
    }

    @Nullable
    private FrequentLocationData getHomeData() {
        return this.mHomeLocationData;
    }

    @Nullable
    private POI getSearchResultPoi(PageBundle pageBundle) {
        if (pageBundle == null) {
            return null;
        }
        Object obj = pageBundle.get("result_poi");
        if (obj == null || !(obj instanceof POI)) {
            return null;
        }
        return (POI) obj;
    }

    @Nullable
    private FrequentLocationData getCompanyData() {
        return this.mCompanyLocationData;
    }

    /* access modifiers changed from: private */
    public boolean justHomeCompanyData() {
        return this.mFrequentLocations.size() == 0;
    }

    /* access modifiers changed from: private */
    public List<FrequentLocationInfoEx> getCloudSyncData() {
        String frequentAddress = bim.aa().n().getFrequentAddress();
        JSONObject jSONObject = null;
        if (TextUtils.isEmpty(frequentAddress)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            jSONObject = new JSONObject(frequentAddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jSONObject == null) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("value");
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                FrequentLocationInfoEx b = cyj.b(optJSONObject.toString());
                if (b != null) {
                    arrayList.add(b);
                }
            }
        }
        return arrayList;
    }

    private void onSelectPoiCallback(POI poi) {
        if (poi != null) {
            this.mFrequentLocationView.a(FrequentLocationData.a(poi, null));
            refreshFooter();
        }
    }

    private void startRoutePage(POI poi) {
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_poi_end", poi);
            pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
            bax.a(pageBundle);
        }
    }
}
