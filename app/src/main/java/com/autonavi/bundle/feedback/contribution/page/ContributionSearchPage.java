package com.autonavi.bundle.feedback.contribution.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.datamodel.poi.POIBase;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.feedback.contribution.GetInputSuggestionResponse;
import com.autonavi.bundle.feedback.contribution.adapter.ContributionAdapter;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.minimap.widget.SearchEdit.OnItemEventListener;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.server.request.AosInputSuggestionParam;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import com.autonavi.widget.ui.TitleBar;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

@PageAction("amap.basemap.action.contribution_search_page")
public class ContributionSearchPage extends AbstractBasePage<avc> implements OnClickListener, OnItemClickListener, d<ListView> {
    public final int MODE_ALL = 0;
    private SearchListAdapter adapter;
    private defpackage.ahl.a asyncTask;
    /* access modifiers changed from: private */
    public ContributionAdapter historyAdapter;
    /* access modifiers changed from: private */
    public int historyCount;
    /* access modifiers changed from: private */
    public List<TipItem> historyTipItems;
    private RelativeLayout mErrorLayout;
    private TextView mErrorText;
    private LoadingLayout mFooterLayout;
    private GeoPoint mGeoPoint;
    private AosGetRequest mGetRequest;
    private View mHistoryHeaderView;
    /* access modifiers changed from: private */
    public ListView mHistoryListView;
    /* access modifiers changed from: private */
    public int mHistoryMaxSize = 10;
    /* access modifiers changed from: private */
    public int mHistoryType = 0;
    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            c cVar = (c) view.getTag();
            if (cVar != null && cVar.a != null) {
                PageBundle pageBundle = (PageBundle) ContributionSearchPage.this.mSearchBundle.clone();
                if (i != 0) {
                    i--;
                }
                POI poi = (POI) ContributionSearchPage.this.mPoiArrayList.get(i);
                if (pageBundle != null) {
                    pageBundle.putObject("points", poi);
                    if (!TextUtils.isEmpty(poi.getAddr())) {
                        pageBundle.putObject("address", poi.getAddr());
                        pageBundle.putObject("lines", poi.getAddr());
                    }
                }
                ContributionSearchPage.this.tipItemTurnTo(poi.getType(), poi.getName(), pageBundle);
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<POI> mPoiArrayList = new ArrayList<>();
    /* access modifiers changed from: private */
    public ael mPoiRequest;
    /* access modifiers changed from: private */
    public ProgressDlg mProgressDlg;
    /* access modifiers changed from: private */
    public PullToRefreshListView mPullToRefreshListView;
    private ListView mResultListView = null;
    /* access modifiers changed from: private */
    public PageBundle mSearchBundle;
    private Button mSearchButton;
    /* access modifiers changed from: private */
    public EditText mSearchEditText;
    private LinearLayout mSearchLayout;
    private aud mSearchResultData;
    /* access modifiers changed from: private */
    public boolean mSelectTop = true;
    /* access modifiers changed from: private */
    public int mSizeOfHistoryShow = Integer.MAX_VALUE;
    private SuggestListAdapter mSuggestListAdapter;
    private ListView mSuggestListView;
    private TextWatcher mTextWatcher = new TextWatcher() {
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void afterTextChanged(Editable editable) {
            String obj = editable.toString();
            if (!TextUtils.isEmpty(obj)) {
                ContributionSearchPage.this.startSuggestNetWork(obj);
            } else {
                ContributionSearchPage.this.startGetHistory();
            }
            ContributionSearchPage.this.updateSearchButtonState();
        }
    };
    private List<TipItem> mTipItemList;
    private TitleBar mTitleBar;
    private int mode = 0;
    int page = 0;
    private LinearLayout refresh_layout;
    AosResponseCallbackOnUi responseCallback = new AosResponseCallbackOnUi<GetInputSuggestionResponse>() {
        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        }

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            GetInputSuggestionResponse getInputSuggestionResponse = (GetInputSuggestionResponse) aosResponse;
            if (getInputSuggestionResponse != null) {
                try {
                    JSONObject jSONObject = getInputSuggestionResponse.k;
                    if (getInputSuggestionResponse.f == 1 && jSONObject != null) {
                        getInputSuggestionResponse.a = jSONObject.optInt("is_general_search");
                        JSONArray optJSONArray = jSONObject.optJSONArray("tip_list");
                        if (optJSONArray != null) {
                            int length = optJSONArray.length();
                            getInputSuggestionResponse.l = new ArrayList();
                            for (int i = 0; i < length; i++) {
                                JSONObject optJSONObject = optJSONArray.getJSONObject(i).optJSONObject("tip");
                                if (optJSONObject != null) {
                                    TipItem a2 = getInputSuggestionResponse.a(optJSONObject);
                                    a2.type = 1;
                                    if (!TextUtils.isEmpty(a2.name)) {
                                        getInputSuggestionResponse.l.add(a2);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                ContributionSearchPage.this.updateSuggestList(getInputSuggestionResponse.l);
                return;
            }
            ContributionSearchPage.this.hideHistory();
        }
    };

    class SearchListAdapter extends BaseAdapter {
        private c holder;
        ArrayList<POI> poiArrayList;

        public long getItemId(int i) {
            return (long) i;
        }

        public SearchListAdapter(ArrayList<POI> arrayList) {
            this.poiArrayList = arrayList;
        }

        public int getCount() {
            if (this.poiArrayList == null) {
                return 0;
            }
            return this.poiArrayList.size();
        }

        public Object getItem(int i) {
            return this.poiArrayList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            int i2;
            if (view == null) {
                view = LayoutInflater.from(ContributionSearchPage.this.getContext()).inflate(R.layout.feedback_search_item, null);
                this.holder = new c();
                this.holder.b = (TextView) view.findViewById(R.id.poi_name);
                this.holder.c = (TextView) view.findViewById(R.id.poi_address);
                this.holder.d = (ImageView) view.findViewById(R.id.icon);
                view.setTag(this.holder);
            } else {
                this.holder = (c) view.getTag();
            }
            this.holder.a = this.poiArrayList.get(i);
            if (ContributionSearchPage.this.isStation(this.holder.a.getType())) {
                i2 = R.drawable.gongjiao;
            } else {
                i2 = R.drawable.default_generalsearch_sugg_tqueryicon_normal;
            }
            this.holder.d.setImageResource(i2);
            this.holder.b.setText(this.poiArrayList.get(i).getName());
            this.holder.c.setText(this.poiArrayList.get(i).getAddr());
            view.findViewById(R.id.item_bottom_divider).setVisibility(0);
            return view;
        }
    }

    class SuggestListAdapter extends BaseAdapter {
        private a holder;
        private List<TipItem> tipItemList;

        class a {
            public TextView a;
            public TextView b;
            public ImageView c;

            a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public SuggestListAdapter(List<TipItem> list) {
            this.tipItemList = list;
        }

        public int getCount() {
            if (this.tipItemList == null) {
                return 0;
            }
            return this.tipItemList.size();
        }

        public Object getItem(int i) {
            if (this.tipItemList == null || this.tipItemList.size() < i) {
                return null;
            }
            return this.tipItemList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(ContributionSearchPage.this.getContext()).inflate(R.layout.feedback_search_item, null);
                this.holder = new a();
                this.holder.a = (TextView) view.findViewById(R.id.poi_name);
                this.holder.b = (TextView) view.findViewById(R.id.poi_address);
                this.holder.c = (ImageView) view.findViewById(R.id.icon);
                view.setTag(this.holder);
            } else {
                this.holder = (a) view.getTag();
            }
            this.holder.a.setText(this.tipItemList.get(i).name);
            StringBuilder sb = new StringBuilder();
            String str = this.tipItemList.get(i).district;
            String str2 = this.tipItemList.get(i).addr;
            if (!ContributionSearchPage.this.isRoad(this.tipItemList.get(i).newType)) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                }
                if (sb.length() > 0 && !TextUtils.isEmpty(str2)) {
                    sb.append("-");
                    sb.append(str2);
                }
            } else if (!TextUtils.isEmpty(str2)) {
                sb.append(str2);
            } else if (!TextUtils.isEmpty(str)) {
                sb.append(str);
            }
            this.holder.b.setText(sb.toString());
            this.holder.c.setImageResource(SearchUtils.processTipItemIcon(this.tipItemList.get(i)));
            return view;
        }
    }

    class a implements aeb<aud> {
        private a() {
        }

        /* synthetic */ a(ContributionSearchPage contributionSearchPage, byte b) {
            this();
        }

        public final /* synthetic */ void callback(Object obj) {
            ContributionSearchPage.this.page = ContributionSearchPage.this.mPoiRequest.d;
            ContributionSearchPage.this.updateListView((aud) obj);
            ContributionSearchPage.this.mPullToRefreshListView.onRefreshComplete();
        }

        public final void error(int i) {
            if (i == 1) {
                ToastHelper.showLongToast(ContributionSearchPage.this.getString(R.string.network_error_message));
            } else {
                ToastHelper.showLongToast(ContributionSearchPage.this.getString(R.string.no_result_please_retry_later));
            }
            ContributionSearchPage.this.mPullToRefreshListView.onRefreshComplete();
        }
    }

    class b implements aeb<aud> {
        private b() {
        }

        /* synthetic */ b(ContributionSearchPage contributionSearchPage, byte b) {
            this();
        }

        public final /* synthetic */ void callback(Object obj) {
            aud aud = (aud) obj;
            if (ContributionSearchPage.this.mProgressDlg != null && ContributionSearchPage.this.mProgressDlg.isShowing()) {
                ContributionSearchPage.this.mProgressDlg.dismiss();
            }
            ContributionSearchPage.this.updateListView(aud);
        }

        public final void error(int i) {
            if (ContributionSearchPage.this.mProgressDlg != null && ContributionSearchPage.this.mProgressDlg.isShowing()) {
                ContributionSearchPage.this.mProgressDlg.dismiss();
            }
            if (i == 1) {
                ToastHelper.showLongToast(ContributionSearchPage.this.getString(R.string.network_error_message));
            }
            if (!aaw.c(ContributionSearchPage.this.getContext())) {
                ToastHelper.showLongToast(ContributionSearchPage.this.getString(R.string.network_error_message));
            }
        }
    }

    static class c {
        public POI a;
        public TextView b;
        public TextView c;
        public ImageView d;

        c() {
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.contribution_search_layout);
        initView(getContentView());
        this.mSearchBundle = (PageBundle) getArguments().clone();
    }

    private void initView(View view) {
        this.mTitleBar = (TitleBar) view.findViewById(R.id.title);
        this.mTitleBar.setTitle(getString(R.string.map_contribution_error));
        this.mTitleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ContributionSearchPage.this.finish();
            }
        });
        this.mSearchEditText = (EditText) view.findViewById(R.id.search_text);
        this.mSuggestListView = (ListView) view.findViewById(R.id.suggest_list);
        this.mHistoryListView = (ListView) view.findViewById(R.id.history_list_view);
        this.mSearchLayout = (LinearLayout) view.findViewById(R.id.searchLayout);
        this.mErrorLayout = (RelativeLayout) view.findViewById(R.id.error_layout);
        this.refresh_layout = (LinearLayout) view.findViewById(R.id.refresh_layout);
        this.mErrorText = (TextView) view.findViewById(R.id.error_text);
        view.findViewById(R.id.contribution_add_btn).setOnClickListener(this);
        this.historyTipItems = new ArrayList();
        this.historyAdapter = new ContributionAdapter(getContext(), R.layout.contribution_search_history_list_item, this.historyTipItems);
        this.historyAdapter.setOnItemEventListener(new OnItemEventListener() {
            public final void onAddClicked(TipItem tipItem, int i) {
            }

            public final void onItemLongClicked(TipItem tipItem) {
            }

            public final void onRouteClicked(TipItem tipItem) {
            }

            public final void onItemClicked(TipItem tipItem, int i, boolean z) {
                PageBundle pageBundle = (PageBundle) ContributionSearchPage.this.mSearchBundle.clone();
                if (tipItem != null && !TextUtils.isEmpty(tipItem.name)) {
                    if (pageBundle != null) {
                        POIBase pOIBase = new POIBase();
                        pOIBase.setName(tipItem.name);
                        StringBuilder sb = new StringBuilder();
                        if (!TextUtils.isEmpty(tipItem.district)) {
                            sb.append(tipItem.district);
                            if (!TextUtils.isEmpty(tipItem.addr)) {
                                sb.append("-");
                            }
                        }
                        sb.append(tipItem.addr);
                        pOIBase.setAddr(sb.toString());
                        pOIBase.setId(tipItem.poiid);
                        pOIBase.setPoint(new GeoPoint(tipItem.x, tipItem.y));
                        pOIBase.setEndPoiExtension(tipItem.endPoiExtension);
                        pOIBase.setTransparent(tipItem.transparent);
                        pageBundle.putObject("points", pOIBase);
                        if (!TextUtils.isEmpty(tipItem.addr)) {
                            pageBundle.putObject("address", tipItem.addr);
                            pageBundle.putObject("lines", tipItem.addr);
                        }
                    }
                    ContributionSearchPage.this.tipItemTurnTo(tipItem.newType, tipItem.name, pageBundle);
                }
            }
        });
        this.mSearchEditText.addTextChangedListener(this.mTextWatcher);
        this.mSearchEditText.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6 && ContributionSearchPage.this.mSearchEditText.getText().toString().length() > 0) {
                    ContributionSearchPage.this.search(ContributionSearchPage.this.mSearchEditText.getText().toString());
                }
                return false;
            }
        });
        this.mSearchEditText.setFocusable(true);
        this.mSearchEditText.setFocusableInTouchMode(true);
        this.mSearchEditText.requestFocus();
        this.mHistoryHeaderView = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.contribution_search_header_layout, null);
        this.mHistoryListView.addHeaderView(this.mHistoryHeaderView, null, false);
        this.mSearchButton = (Button) view.findViewById(R.id.btn_search);
        this.mSearchButton.setOnClickListener(this);
        this.mTipItemList = new ArrayList();
        this.mSuggestListAdapter = new SuggestListAdapter(this.mTipItemList);
        this.mSuggestListView.setAdapter(this.mSuggestListAdapter);
        this.mSuggestListView.setOnItemClickListener(this);
        initPullToRefreshListView(view);
        this.mGeoPoint = LocationInstrument.getInstance().getLatestPosition();
        startGetHistory();
    }

    private void initPullToRefreshListView(View view) {
        this.mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.contribution_list);
        this.mPullToRefreshListView.setMode(Mode.BOTH);
        this.mPullToRefreshListView.setOnItemClickListener(this.mOnItemClickListener);
        this.mPullToRefreshListView.setOnRefreshListener((d<T>) this);
        this.mPullToRefreshListView.mFooterLoadingView.setVisibility(8);
        this.mPullToRefreshListView.setFootershowflag(false);
        this.mResultListView = (ListView) this.mPullToRefreshListView.getRefreshableView();
        this.mPullToRefreshListView.setMode(Mode.BOTH);
        this.mResultListView.setChoiceMode(1);
        this.mResultListView.setOverScrollMode(2);
        this.mPullToRefreshListView.setOverScrollMode(2);
        this.mPullToRefreshListView.setHeaderTextTextColor(getResources().getColor(R.color.black));
        this.mFooterLayout = this.mPullToRefreshListView.changeFooter();
        this.mPullToRefreshListView.mLvFooterLoadingFrame.removeView(this.mPullToRefreshListView.mFooterLoadingView);
        this.mFooterLayout.setVisibility(0);
        ((ListView) this.mPullToRefreshListView.getRefreshableView()).addFooterView(this.mFooterLayout, null, false);
    }

    /* access modifiers changed from: private */
    public void updateSuggestList(List<TipItem> list) {
        showSearchListview();
        hideHistory();
        this.mTipItemList.clear();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TipItem tipItem = list.get(i);
                if (filterData(tipItem)) {
                    this.mTipItemList.add(tipItem);
                }
            }
        }
        this.mSuggestListAdapter.notifyDataSetChanged();
    }

    private boolean filterData(TipItem tipItem) {
        boolean z = false;
        if (tipItem.poiid == null || tipItem.poiid.length() == 0) {
            return false;
        }
        int i = tipItem.dataType;
        if (i != 0) {
            switch (i) {
                case 2:
                case 3:
                    break;
            }
        }
        z = true;
        return z;
    }

    public void showHistory() {
        this.mSuggestListView.setVisibility(8);
        this.mHistoryListView.setVisibility(0);
        this.refresh_layout.setVisibility(8);
    }

    public void showSearchListview() {
        this.mSearchLayout.setVisibility(0);
        this.refresh_layout.setVisibility(8);
        this.mErrorLayout.setVisibility(8);
    }

    public void showErrorLayout() {
        this.mSearchLayout.setVisibility(8);
        this.refresh_layout.setVisibility(8);
        this.mErrorLayout.setVisibility(0);
    }

    public void errorLayoutText(boolean z) {
        if (isAlive()) {
            findViewById(R.id.contribution_add_btn).setVisibility(z ? 0 : 8);
            this.mErrorText.setText(z ? getString(R.string.search_no_result_displayed_message) : "请在搜索框中输入需要修改的地点名称");
        }
    }

    private void showRefreshListView() {
        this.refresh_layout.setVisibility(0);
        this.mSearchLayout.setVisibility(8);
        this.mErrorLayout.setVisibility(8);
    }

    public void hideHistory() {
        this.mSuggestListView.setVisibility(0);
        this.mHistoryListView.setVisibility(8);
        this.refresh_layout.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void startGetHistory() {
        if (this.asyncTask == null) {
            initDataAsync();
        }
        if (this.asyncTask != null) {
            ahl.b(this.asyncTask);
        }
    }

    public void initDataAsync() {
        this.asyncTask = new defpackage.ahl.a<List<TipItem>>() {
            public final /* synthetic */ void onFinished(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    list = new ArrayList();
                }
                ContributionSearchPage.this.historyTipItems.clear();
                ContributionSearchPage.this.historyCount = list.size();
                if (ContributionSearchPage.this.historyCount != 0) {
                    ContributionSearchPage.this.mHistoryListView.setBackgroundResource(R.drawable.border_bg);
                    for (int i = 0; i < ContributionSearchPage.this.historyCount; i++) {
                        if (ContributionSearchPage.this.historyTipItems.size() < ContributionSearchPage.this.mHistoryMaxSize) {
                            TipItem tipItem = (TipItem) list.get(i);
                            if (ContributionSearchPage.this.isRoad(tipItem.newType) || ContributionSearchPage.this.isStation(tipItem.newType) || ((!TextUtils.isEmpty(tipItem.poiid) && tipItem.iconinfo != 1) || tipItem.iconinfo == 2)) {
                                ContributionSearchPage.this.historyTipItems.add(list.get(i));
                            }
                        }
                    }
                    if (ContributionSearchPage.this.mHistoryListView.getAdapter() == null) {
                        ContributionSearchPage.this.mHistoryListView.setAdapter(ContributionSearchPage.this.historyAdapter);
                    }
                    ContributionSearchPage.this.historyAdapter.notifyDataSetChanged();
                    if (ContributionSearchPage.this.historyTipItems.size() > 0) {
                        ContributionSearchPage.this.showSearchListview();
                        ContributionSearchPage.this.showHistory();
                        if (ContributionSearchPage.this.mSelectTop) {
                            ContributionSearchPage.this.mHistoryListView.setSelection(0);
                            return;
                        }
                    }
                }
                ContributionSearchPage.this.showErrorLayout();
                ContributionSearchPage.this.errorLayoutText(false);
            }

            public final void onError(Throwable th) {
                ContributionSearchPage.this.mHistoryListView.setBackgroundResource(R.drawable.border_bg);
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                new ArrayList();
                return ContributionSearchPage.this.resizeTipItems(SearchHistoryHelper.getInstance(ContributionSearchPage.this.getContext()).getTipItems(ContributionSearchPage.this.mHistoryType), ContributionSearchPage.this.mSizeOfHistoryShow);
            }
        };
    }

    /* access modifiers changed from: private */
    public List<TipItem> resizeTipItems(List<TipItem> list, int i) {
        if (list == null || list.size() <= i) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(list.get(i2));
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void updateSearchButtonState() {
        if (this.mSearchButton != null && this.mSearchEditText != null) {
            this.mSearchButton.setVisibility(TextUtils.isEmpty(this.mSearchEditText.getText().toString()) ^ true ? 0 : 8);
        }
    }

    /* access modifiers changed from: protected */
    public avc createPresenter() {
        return new avc(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            search(this.mSearchEditText.getText().toString());
            return;
        }
        if (view.getId() == R.id.contribution_add_btn) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("page_id", 22);
            pageBundle.putInt("sourcepage", 34);
            col.a();
            startPage(Ajx3DialogPage.class, col.c(pageBundle, "entrylist"));
        }
    }

    public void search(String str) {
        if (this.mGetRequest != null) {
            yq.a();
            yq.a((AosRequest) this.mGetRequest);
        }
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showToast("查询内容不能为空");
        } else if (str.trim().length() == 0) {
            this.mSearchEditText.setText("");
            ToastHelper.showToast("查询内容不能为空");
        } else if (NetworkReachability.b()) {
            this.mPoiRequest = new ael(str, this.mGeoPoint);
            this.mPoiRequest.g = "2";
            ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
            if (iSearchService != null) {
                showProgressDialog(iSearchService.b(aew.a((aem) this.mPoiRequest), 2, new b(this, 0)), str);
            }
        } else {
            ToastHelper.showLongToast(getString(R.string.network_error_message));
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [android.widget.AdapterView<?>, android.widget.AdapterView] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onItemClick(android.widget.AdapterView<?> r5, android.view.View r6, int r7, long r8) {
        /*
            r4 = this;
            android.widget.ListView r6 = r4.mHistoryListView
            int r6 = r6.getVisibility()
            if (r6 != 0) goto L_0x0013
            android.widget.Adapter r5 = r5.getAdapter()
            java.lang.Object r5 = r5.getItem(r7)
            com.autonavi.bundle.entity.sugg.TipItem r5 = (com.autonavi.bundle.entity.sugg.TipItem) r5
            goto L_0x001b
        L_0x0013:
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r5 = r4.mTipItemList
            java.lang.Object r5 = r5.get(r7)
            com.autonavi.bundle.entity.sugg.TipItem r5 = (com.autonavi.bundle.entity.sugg.TipItem) r5
        L_0x001b:
            if (r5 == 0) goto L_0x00a1
            java.lang.String r6 = r5.name
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x00a1
            com.autonavi.common.PageBundle r6 = r4.mSearchBundle
            java.lang.Object r6 = r6.clone()
            com.autonavi.common.PageBundle r6 = (com.autonavi.common.PageBundle) r6
            if (r6 == 0) goto L_0x009a
            com.amap.bundle.datamodel.poi.POIBase r7 = new com.amap.bundle.datamodel.poi.POIBase
            r7.<init>()
            java.lang.String r8 = r5.name
            r7.setName(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = r5.district
            boolean r9 = android.text.TextUtils.isEmpty(r9)
            if (r9 != 0) goto L_0x0058
            java.lang.String r9 = r5.district
            r8.append(r9)
            java.lang.String r9 = r5.addr
            boolean r9 = android.text.TextUtils.isEmpty(r9)
            if (r9 != 0) goto L_0x0058
            java.lang.String r9 = "-"
            r8.append(r9)
        L_0x0058:
            java.lang.String r9 = r5.addr
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.setAddr(r8)
            java.lang.String r8 = r5.poiid
            r7.setId(r8)
            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint
            double r0 = r5.x
            double r2 = r5.y
            r8.<init>(r0, r2)
            r7.setPoint(r8)
            java.lang.String r8 = r5.endPoiExtension
            r7.setEndPoiExtension(r8)
            java.lang.String r8 = r5.transparent
            r7.setTransparent(r8)
            java.lang.String r8 = "points"
            r6.putObject(r8, r7)
            java.lang.String r7 = r5.addr
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x009a
            java.lang.String r7 = "address"
            java.lang.String r8 = r5.addr
            r6.putObject(r7, r8)
            java.lang.String r7 = "lines"
            java.lang.String r8 = r5.addr
            r6.putObject(r7, r8)
        L_0x009a:
            java.lang.String r7 = r5.newType
            java.lang.String r5 = r5.name
            r4.tipItemTurnTo(r7, r5, r6)
        L_0x00a1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.feedback.contribution.page.ContributionSearchPage.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
    }

    private void showProgressDialog(final adx adx, String str) {
        String str2;
        if (str == null || "".equals(str)) {
            str2 = getString(R.string.searching);
        } else {
            str2 = String.format(getString(R.string.issearching_with_param), new Object[]{str});
        }
        if (this.mProgressDlg == null) {
            this.mProgressDlg = new ProgressDlg(getActivity(), str2, "");
        }
        this.mProgressDlg.setMessage(str2);
        this.mProgressDlg.setCancelable(true);
        this.mProgressDlg.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (adx != null) {
                    adx.a();
                }
            }
        });
        this.mProgressDlg.show();
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        showPrePage();
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.mPullToRefreshListView.mFooterLoadingView.setVisibility(8);
        showNextPage();
    }

    /* access modifiers changed from: private */
    public void tipItemTurnTo(String str, String str2, PageBundle pageBundle) {
        pageBundle.putString("name", str2);
        pageBundle.putBoolean("boundary", false);
        if (isStation(str)) {
            pageBundle.putInt("page_id", 11);
            pageBundle.putString("page_action", "com.basemap.action.feedback_entry_list");
        } else if (isRoad(str)) {
            pageBundle.putInt("page_id", 13);
            pageBundle.putString("page_action", "com.basemap.action.feedback_entry_list");
        } else {
            pageBundle.putInt("page_id", 14);
            pageBundle.putString("page_action", "amap.basemap.action.feedback_poi_detail_normal");
        }
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            ((avc) this.mPresenter).a();
            iErrorReportStarter.startFeedback(pageBundle);
        }
    }

    private void showPrePage() {
        int i = this.page - 1;
        this.mPoiRequest.d = i;
        if (i <= 0) {
            updatePullList(this.mPoiRequest.d, avf.b(this.mSearchResultData));
        } else {
            startSearch();
        }
    }

    private void showNextPage() {
        int i = this.page + 1;
        this.mPoiRequest.d = i;
        if (i > avf.b(this.mSearchResultData)) {
            this.mPoiRequest.d = avf.b(this.mSearchResultData);
            updatePullList(this.mPoiRequest.d, avf.b(this.mSearchResultData));
            return;
        }
        startSearch();
    }

    /* access modifiers changed from: private */
    public void updateListView(aud aud) {
        int i;
        if (!(aud == null || aud.b == null || aud.b.d == null)) {
            this.mSearchResultData = aud;
            if (!(this.mSearchResultData == null || this.mSearchResultData.b == null || this.mSearchResultData.b.d == null)) {
                ArrayList<POI> a2 = avf.a(this.mSearchResultData);
                this.mPoiArrayList.clear();
                if (a2 != null) {
                    this.mPoiArrayList.addAll(a2);
                }
            }
        }
        if (this.mPoiArrayList.size() > 0) {
            if (this.mPoiRequest != null) {
                i = this.mPoiRequest.d;
            } else {
                i = 0;
            }
            this.page = i;
            if (this.adapter == null) {
                this.adapter = new SearchListAdapter(this.mPoiArrayList);
                this.mPullToRefreshListView.setAdapter(this.adapter);
            } else {
                this.adapter.notifyDataSetChanged();
            }
            ((ListView) this.mPullToRefreshListView.getRefreshableView()).setSelection(0);
            updatePullList(this.page, avf.b(this.mSearchResultData));
            showRefreshListView();
            return;
        }
        showErrorLayout();
        errorLayoutText(true);
    }

    public void updatePullList(int i, int i2) {
        if (i == 0) {
            this.mPullToRefreshListView.onRefreshComplete();
            return;
        }
        this.mPullToRefreshListView.mHeaderLoadingView.setRefreshingLabel(getString(R.string.isloading));
        this.mPullToRefreshListView.onRefreshComplete();
        this.mPullToRefreshListView.showImageFoot();
        this.mPullToRefreshListView.setMode(Mode.BOTH);
        if (i == 1) {
            this.mPullToRefreshListView.hideImageHead();
            this.mPullToRefreshListView.setNeedRefreshing(false);
            this.mPullToRefreshListView.setHeaderText(getString(R.string.first_page_no_last_apage), getString(R.string.first_page_no_last_apage), getString(R.string.isloading));
            this.mPullToRefreshListView.setFooterText(getString(R.string.first_page_pull_to_second_page), getString(R.string.release_to_next), getString(R.string.isloading));
        } else {
            this.mPullToRefreshListView.showImageHead();
            this.mPullToRefreshListView.setNeedRefreshing(true);
            this.mPullToRefreshListView.setHeaderText(String.format(getString(R.string.cur_page_pull_down_to_loading_next), new Object[]{Integer.valueOf(i)}), getString(R.string.release_to_last), getString(R.string.isloading));
            this.mPullToRefreshListView.setFooterText(String.format(getString(R.string.cur_page_pull_up_to_loading_next), new Object[]{Integer.valueOf(i)}), getString(R.string.release_to_next), getString(R.string.isloading));
        }
        if (i >= i2) {
            this.mPullToRefreshListView.setFooterText(String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(i)}), String.format(getString(R.string.current_page_no_next_page), new Object[]{Integer.valueOf(i)}), getString(R.string.isloading));
            this.mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
            if (this.adapter != null && this.adapter.getCount() > 0 && this.adapter.getCount() <= 10) {
                this.mPullToRefreshListView.hideImageFoot();
            }
        }
    }

    public boolean isStation(String str) {
        return "150500".equals(str) || "150600".equals(str) || "150700".equals(str) || "151200".equals(str) || "151300".equals(str);
    }

    public boolean isRoad(String str) {
        return "180200".equals(str) || "190301".equals(str) || "190302".equals(str) || "190303".equals(str) || "190304".equals(str) || "190305".equals(str) || "190306".equals(str) || "190307".equals(str) || "190308".equals(str) || "190309".equals(str) || "190310".equals(str);
    }

    public void clearFocus() {
        this.mTitleBar.setFocusable(true);
        this.mTitleBar.setFocusableInTouchMode(true);
        this.mTitleBar.requestFocus();
    }

    /* access modifiers changed from: private */
    public void startSuggestNetWork(String str) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        String valueOf = String.valueOf(latestPosition.getAdCode());
        String userLocInfo = AppManager.getInstance().getUserLocInfo();
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        AosInputSuggestionParam aosInputSuggestionParam = new AosInputSuggestionParam(str, valueOf, userLocInfo, sb.toString(), null, "poi|bus", true, DoNotUseTool.getMapView().H(), latestPosition.x, latestPosition.y);
        this.mGetRequest = aax.a(aosInputSuggestionParam);
        this.mGetRequest.addReqParam("version", "2.13");
        yq.a();
        yq.a((AosRequest) this.mGetRequest, (AosResponseCallback<T>) this.responseCallback);
    }

    private void startSearch() {
        ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
        if (iSearchService != null) {
            iSearchService.b(aew.a((aem) this.mPoiRequest), 2, new a(this, 0));
        }
    }
}
