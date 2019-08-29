package com.autonavi.miniapp.plugin.lbs;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.alipay.mobile.antui.basic.AUEditText;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.service.GeocodeService;
import com.alipay.mobile.framework.service.OnPoiSearchListener;
import com.alipay.mobile.map.model.LatLonPoint;
import com.alipay.mobile.map.model.SearchPoiRequest;
import com.alipay.mobile.map.model.geocode.PoiItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.miniapp.plugin.adapter.AmapGeocodeService;
import com.autonavi.miniapp.plugin.search.AUSocialSearchBar;
import com.autonavi.miniapp.plugin.search.PoiListAdapter;
import com.autonavi.minimap.R;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5PoiSearchActivity extends BaseActivity implements OnScrollListener, OnItemClickListener {
    private static final LatLonPoint INVALID_LATLONPOINT = null;
    private static final int MAX_RESULT = 100;
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "H5PoiSearchActivity";
    /* access modifiers changed from: private */
    public PoiListAdapter adapter;
    private AmapGeocodeService amapGeocodeService = new AmapGeocodeService();
    /* access modifiers changed from: private */
    public String appKey;
    /* access modifiers changed from: private */
    public boolean canRefresh;
    private String city;
    AUTextView emptyView;
    /* access modifiers changed from: private */
    public View footer;
    private GeoPoint geoPoint;
    private boolean hasCustomLatlng;
    /* access modifiers changed from: private */
    public boolean hasMore;
    /* access modifiers changed from: private */
    public boolean isOverseas;
    /* access modifiers changed from: private */
    public List<PoiItem> items = new ArrayList();
    /* access modifiers changed from: private */
    public double latitude;
    AUListView listView;
    /* access modifiers changed from: private */
    public double longitude;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public OnPoiSearchListener mLoadListener = new OnPoiSearchListener() {
        public void onPoiSearched(List<PoiItem> list, int i, int i2) {
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder("onPoiSearched: pageNum=");
            sb.append(i);
            sb.append(" pageCount=");
            sb.append(i2);
            traceLogger.debug(H5PoiSearchActivity.TAG, sb.toString());
            H5PoiSearchActivity.this.canRefresh = true;
            H5PoiSearchActivity.this.finishRefresh();
            if (list != null) {
                H5PoiSearchActivity.this.hasMore = i < i2 - 1 && (i + 1) * 20 < 100;
                if (H5PoiSearchActivity.this.hasMore) {
                    H5PoiSearchActivity.this.nextPage = i + 1;
                }
                H5PoiSearchActivity.this.updateList(list, false);
                return;
            }
            H5PoiSearchActivity.this.updateList(Collections.emptyList(), false);
        }
    };
    private OnPoiSearchListener mLoadMoreListener = new OnPoiSearchListener() {
        public void onPoiSearched(List<PoiItem> list, int i, int i2) {
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder("onPoiSearched: pageNum=");
            sb.append(i);
            sb.append(" pageCount=");
            sb.append(i2);
            traceLogger.debug(H5PoiSearchActivity.TAG, sb.toString());
            H5PoiSearchActivity.this.canRefresh = true;
            H5PoiSearchActivity.this.finishRefresh();
            if (H5PoiSearchActivity.this.items != null) {
                H5PoiSearchActivity.this.hasMore = i < i2 - 1 && (i + 1) * 20 < 100;
                if (H5PoiSearchActivity.this.hasMore) {
                    H5PoiSearchActivity.this.nextPage = i + 1;
                }
                H5PoiSearchActivity.this.updateList(H5PoiSearchActivity.this.items, true);
            }
        }
    };
    private HandlerThread mRpcThread;
    private Handler mainUIHandler;
    private String mode;
    /* access modifiers changed from: private */
    public int nextPage;
    /* access modifiers changed from: private */
    public String query;
    /* access modifiers changed from: private */
    public Runnable rpcTask = new Runnable() {
        public void run() {
            H5PoiSearchActivity.this.getKeyWordToSearch();
        }
    };
    AUSocialSearchBar searchBar;
    private String searchHint;

    /* access modifiers changed from: private */
    public H5PoiSearchActivity getActivity() {
        return this;
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        initHandler();
        if (extras != null) {
            this.longitude = extras.getDouble("longitude");
            this.latitude = extras.getDouble("latitude");
            this.isOverseas = extras.getBoolean("isOverseas");
            this.appKey = extras.getString("appKey");
            this.city = extras.getString("city");
            this.hasCustomLatlng = extras.getBoolean("hasCustomLatlng");
            this.searchHint = extras.getString("searchHint");
            this.mode = extras.getString(Constants.KEY_MODE, "default");
            this.geoPoint = (GeoPoint) extras.getSerializable("geoPoint");
        }
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("onCreate: longitude=");
        sb.append(this.longitude);
        sb.append(" latitude=");
        sb.append(this.latitude);
        traceLogger.debug(TAG, sb.toString());
        setContentView(R.layout.fragment_poi_search);
        this.searchBar = (AUSocialSearchBar) findViewById(R.id.search_bar);
        this.listView = (AUListView) findViewById(R.id.search_result_list);
        this.emptyView = (AUTextView) findViewById(R.id.search_no_result);
        this.amapGeocodeService.create();
    }

    private void initHandler() {
        this.mRpcThread = new HandlerThread("poi_rpc");
        this.mRpcThread.start();
        this.mHandler = new Handler(this.mRpcThread.getLooper());
        this.mainUIHandler = new Handler(Looper.myLooper());
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        initSearchBar();
        initListView();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mRpcThread.quit();
        this.amapGeocodeService.destroy();
    }

    private void initSearchBar() {
        this.searchBar.getSearchButton().setVisibility(0);
        if (!TextUtils.isEmpty(this.searchHint)) {
            this.searchBar.getSearchInputEdit().setHint(this.searchHint);
        } else {
            this.searchBar.getSearchInputEdit().setHint(getString(R.string.poiselect_search_hint_2));
        }
        final AUEditText searchInputEdit = this.searchBar.getSearchInputEdit();
        searchInputEdit.setImeOptions(3);
        searchInputEdit.setFocusable(true);
        searchInputEdit.setFocusableInTouchMode(true);
        searchInputEdit.setImeOptions(3);
        searchInputEdit.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 3) {
                    return false;
                }
                String trim = H5PoiSearchActivity.this.searchBar.getSearchInputEdit().getText().toString().trim();
                if (!TextUtils.isEmpty(trim) && !trim.equals(H5PoiSearchActivity.this.query)) {
                    H5PoiSearchActivity.this.query = trim;
                    H5PoiSearchActivity.this.doSearch(H5PoiSearchActivity.this.query);
                }
                H5PoiSearchActivity.this.hideKeyboard(H5PoiSearchActivity.this.getActivity(), searchInputEdit);
                return true;
            }
        });
        searchInputEdit.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                H5PoiSearchActivity.this.mHandler.removeCallbacks(H5PoiSearchActivity.this.rpcTask);
                H5PoiSearchActivity.this.mHandler.postDelayed(H5PoiSearchActivity.this.rpcTask, 300);
            }
        });
        showKeyboard(getActivity(), searchInputEdit);
        this.searchBar.getBackButton().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    H5PoiSearchActivity.this.hideKeyboard(H5PoiSearchActivity.this.getActivity(), searchInputEdit);
                    H5PoiSearchActivity.this.getActivity().onBackPressed();
                } catch (Exception unused) {
                }
            }
        });
        this.searchBar.getSearchButton().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                H5PoiSearchActivity.this.getKeyWordToSearch();
                H5PoiSearchActivity.this.hideKeyboard(H5PoiSearchActivity.this.getActivity(), searchInputEdit);
            }
        });
    }

    /* access modifiers changed from: private */
    public void getKeyWordToSearch() {
        String trim = this.searchBar.getSearchInputEdit().getText().toString().trim();
        if (!TextUtils.isEmpty(trim) && !trim.equals(this.query)) {
            this.query = trim;
            doSearch(trim);
        }
    }

    private void initListView() {
        this.footer = LayoutInflater.from(getActivity()).inflate(R.layout.footer_load_more, this.listView, false);
        this.listView.setOnItemClickListener(this);
        this.listView.setFooterDividersEnabled(false);
        this.listView.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.footer_divider, this.listView, false), null, false);
        this.listView.addFooterView(this.footer, null, false);
        this.footer.setVisibility(8);
        this.adapter = new PoiListAdapter(getActivity(), this.items, false);
        this.listView.setAdapter(this.adapter);
        if (this.items != null && !this.items.isEmpty()) {
            this.listView.setSelection(0);
        }
        this.canRefresh = true;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [android.widget.AdapterView<?>, android.widget.AdapterView] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onItemClick(android.widget.AdapterView<?> r1, android.view.View r2, int r3, long r4) {
        /*
            r0 = this;
            if (r3 < 0) goto L_0x0052
            java.util.List<com.alipay.mobile.map.model.geocode.PoiItem> r2 = r0.items
            int r2 = r2.size()
            if (r3 < r2) goto L_0x000b
            goto L_0x0052
        L_0x000b:
            android.widget.Adapter r1 = r1.getAdapter()
            java.lang.Object r1 = r1.getItem(r3)
            com.alipay.mobile.map.model.geocode.PoiItem r1 = (com.alipay.mobile.map.model.geocode.PoiItem) r1
            com.autonavi.miniapp.plugin.lbs.H5PoiSearchActivity r2 = r0.getActivity()
            com.autonavi.miniapp.plugin.search.AUSocialSearchBar r3 = r0.searchBar
            com.alipay.mobile.antui.basic.AUEditText r3 = r3.getSearchInputEdit()
            r0.hideKeyboard(r2, r3)
            java.lang.String r2 = "default"
            java.lang.String r3 = r0.mode
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x004a
            android.content.Intent r2 = new android.content.Intent
            r2.<init>()
            java.lang.String r3 = "EVENT_POI_LOAD_SEARCH"
            r2.putExtra(r3, r1)
            java.lang.String r1 = "MINI_APP_ACTION"
            r2.setAction(r1)
            android.support.v4.content.LocalBroadcastManager r1 = android.support.v4.content.LocalBroadcastManager.getInstance(r0)
            r1.sendBroadcast(r2)
            com.autonavi.miniapp.plugin.lbs.H5PoiSearchActivity r1 = r0.getActivity()
            r1.onBackPressed()
            return
        L_0x004a:
            com.autonavi.miniapp.plugin.lbs.H5PoiSearchActivity r1 = r0.getActivity()
            r1.finish()
            return
        L_0x0052:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.lbs.H5PoiSearchActivity.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == 0 && absListView.getLastVisiblePosition() == absListView.getCount() - 1 && this.canRefresh && this.hasMore) {
            this.canRefresh = false;
            this.footer.setVisibility(0);
            loadMoreData();
        }
    }

    /* access modifiers changed from: protected */
    public void finishRefresh() {
        this.mainUIHandler.post(new Runnable() {
            public void run() {
                if (H5PoiSearchActivity.this.getActivity() != null && !H5PoiSearchActivity.this.getActivity().isFinishing()) {
                    LoggerFactory.getTraceLogger().debug(H5PoiSearchActivity.TAG, "finishRefresh()");
                    H5PoiSearchActivity.this.canRefresh = true;
                    H5PoiSearchActivity.this.footer.setVisibility(8);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void loadMoreData() {
        LoggerFactory.getTraceLogger().debug(TAG, "loadMoreData()");
        GeocodeService geocodeService = getGeocodeService();
        if (geocodeService == null) {
            finishRefresh();
            return;
        }
        this.canRefresh = false;
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        LatLonPoint latLonPoint = new LatLonPoint(this.latitude, this.longitude);
        SearchPoiRequest searchPoiRequest = new SearchPoiRequest();
        searchPoiRequest.setByfoursquare(this.isOverseas);
        searchPoiRequest.setKeywords(this.query);
        searchPoiRequest.setPagesize(20);
        searchPoiRequest.setPagenum(this.nextPage);
        addLatLngByCity(latLonPoint, searchPoiRequest);
        geocodeService.searchpoi(microApplicationContext.getApplicationContext(), this.appKey, this.mLoadMoreListener, searchPoiRequest);
    }

    /* access modifiers changed from: private */
    public GeocodeService getGeocodeService() {
        this.amapGeocodeService.setGeoPoint(this.geoPoint);
        return this.amapGeocodeService;
    }

    /* access modifiers changed from: private */
    public void addLatLngByCity(LatLonPoint latLonPoint, SearchPoiRequest searchPoiRequest) {
        if (!TextUtils.isEmpty(this.city)) {
            searchPoiRequest.setCity(this.city);
        }
        if (TextUtils.isEmpty(this.city) || this.hasCustomLatlng) {
            searchPoiRequest.setLatlng(latLonPoint);
        } else {
            searchPoiRequest.setLatlng(INVALID_LATLONPOINT);
        }
    }

    /* access modifiers changed from: protected */
    public void doSearch(final String str) {
        this.mainUIHandler.post(new Runnable() {
            public void run() {
                TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                StringBuilder sb = new StringBuilder("doSearch: query=");
                sb.append(str);
                traceLogger.debug(H5PoiSearchActivity.TAG, sb.toString());
                GeocodeService access$1100 = H5PoiSearchActivity.this.getGeocodeService();
                if (access$1100 != null) {
                    H5PoiSearchActivity.this.canRefresh = false;
                    H5PoiSearchActivity.this.resetState();
                    H5PoiSearchActivity.this.footer.setVisibility(0);
                    MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
                    LatLonPoint latLonPoint = new LatLonPoint(H5PoiSearchActivity.this.latitude, H5PoiSearchActivity.this.longitude);
                    SearchPoiRequest searchPoiRequest = new SearchPoiRequest();
                    searchPoiRequest.setByfoursquare(H5PoiSearchActivity.this.isOverseas);
                    searchPoiRequest.setKeywords(str);
                    searchPoiRequest.setPagesize(20);
                    searchPoiRequest.setPagenum(0);
                    H5PoiSearchActivity.this.addLatLngByCity(latLonPoint, searchPoiRequest);
                    access$1100.searchpoi(microApplicationContext.getApplicationContext(), H5PoiSearchActivity.this.appKey, H5PoiSearchActivity.this.mLoadListener, searchPoiRequest);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void resetState() {
        this.nextPage = 0;
        this.hasMore = false;
    }

    /* access modifiers changed from: protected */
    public void updateList(final List<PoiItem> list, final boolean z) {
        this.mainUIHandler.post(new Runnable() {
            public void run() {
                if (H5PoiSearchActivity.this.getActivity() != null && !H5PoiSearchActivity.this.getActivity().isFinishing() && list != null) {
                    TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                    StringBuilder sb = new StringBuilder("updateList: append=");
                    sb.append(z);
                    traceLogger.debug(H5PoiSearchActivity.TAG, sb.toString());
                    if (!z) {
                        H5PoiSearchActivity.this.items.clear();
                    }
                    H5PoiSearchActivity.this.items.addAll(list);
                    H5PoiSearchActivity.this.listView.setEmptyView(H5PoiSearchActivity.this.emptyView);
                    H5PoiSearchActivity.this.adapter.setKeyword(H5PoiSearchActivity.this.query);
                    H5PoiSearchActivity.this.adapter.notifyDataSetChanged();
                    H5PoiSearchActivity.this.footer.setVisibility(8);
                }
            }
        });
    }

    private void showKeyboard(Context context, View view) {
        view.requestFocus();
        ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(view, 1);
    }

    /* access modifiers changed from: private */
    public void hideKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
