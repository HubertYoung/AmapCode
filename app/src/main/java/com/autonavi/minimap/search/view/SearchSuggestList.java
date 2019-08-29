package com.autonavi.minimap.search.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.widget.SearchEdit;
import com.autonavi.minimap.widget.SearchEdit.OnItemEventListener;
import com.autonavi.minimap.widget.SearchSuggestAdapter;
import com.autonavi.minimap.widget.SearchSuggestAdapter.TopListSchemaCallback;
import com.autonavi.sdk.log.util.LogConstant;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

@SuppressFBWarnings({"UWF_NULL_FIELD"})
public class SearchSuggestList implements OnClickListener, OnScrollListener, TopListSchemaCallback {
    /* access modifiers changed from: private */
    public int FROM_PAGE;
    private defpackage.ahl.a asyncTask;
    Handler handlerNetwork = new Handler(new Callback() {
        public final boolean handleMessage(Message message) {
            if (AMapPageUtil.getPageContext() == null) {
                return false;
            }
            if (!TextUtils.isEmpty(SearchSuggestList.this.mKeyWord)) {
                if (SearchSuggestList.this.mSuggestAdapter != null) {
                    SearchSuggestList.this.mSuggestAdapter.setKeyWord(SearchSuggestList.this.mKeyWord);
                }
                SearchSuggestList.this.mSearchEdit.setClearButtonVisibility(false);
                int i = 1;
                SearchSuggestList.this.mSearchEdit.setProgressBarVisibility(true);
                if (SearchSuggestList.this.FROM_PAGE != 10049) {
                    i = 2;
                }
                SuggestionParam suggestionParam = new SuggestionParam();
                suggestionParam.adcode = SearchSuggestList.this.mAdcode;
                suggestionParam.keyWord = SearchSuggestList.this.mKeyWord;
                suggestionParam.category = SearchSuggestList.this.mCategory;
                suggestionParam.suggestType = SearchSuggestList.this.mSuggestType;
                suggestionParam.mCenter = SearchSuggestList.this.mCenter;
                ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
                if (iSearchService != null) {
                    SearchSuggestList.this.mCancelable = iSearchService.a(suggestionParam, i, (aeb<aux>) new a<aux>());
                }
            } else {
                SearchSuggestList.this.mSearchEdit.setClearButtonVisibility(false);
                SearchSuggestList.this.mSearchEdit.setProgressBarVisibility(false);
            }
            return false;
        }
    });
    /* access modifiers changed from: private */
    public long mAdcode;
    /* access modifiers changed from: private */
    public adx mCancelable;
    /* access modifiers changed from: private */
    public String mCategory = null;
    /* access modifiers changed from: private */
    public GeoPoint mCenter;
    private Context mContext;
    private LinearLayout mFooterLayout;
    /* access modifiers changed from: private */
    public int mHisType;
    /* access modifiers changed from: private */
    public boolean mIsActive = true;
    /* access modifiers changed from: private */
    public String mKeyWord;
    private OnClickListener mOnSearchMoreBtnClickListener;
    private bid mPageContext;
    /* access modifiers changed from: private */
    public SearchEdit mSearchEdit;
    /* access modifiers changed from: private */
    public int mSearchType = 0;
    private boolean mShowRoute;
    /* access modifiers changed from: private */
    public SearchSuggestAdapter mSuggestAdapter;
    /* access modifiers changed from: private */
    public ListView mSuggestListView;
    /* access modifiers changed from: private */
    public List<TipItem> mSuggestTipItems;
    /* access modifiers changed from: private */
    public String mSuggestType = "poi|bus|busline";

    public class a implements aeb<aux> {
        public final void error(int i) {
        }

        public a() {
        }

        public final /* synthetic */ void callback(Object obj) {
            aux aux = (aux) obj;
            if (SearchSuggestList.this.mIsActive) {
                SearchSuggestList.this.mSearchEdit.setProgressBarVisibility(false);
                SearchSuggestList.this.mKeyWord = SearchSuggestList.this.mSearchEdit.getTextFromEditSearch();
                if (!TextUtils.isEmpty(SearchSuggestList.this.mKeyWord)) {
                    SearchSuggestList.this.mSearchEdit.setClearButtonVisibility(true);
                } else {
                    SearchSuggestList.this.mSearchEdit.setClearButtonVisibility(false);
                }
                SearchSuggestList.this.mSearchType = aux.a;
                SearchSuggestList.this.processSuggestNetWorkData(aux.b);
            }
        }
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    public SearchSuggestList(Context context, SearchEdit searchEdit, ListView listView, int i, String str, boolean z) {
        this.mSuggestListView = listView;
        this.mContext = context;
        this.mSearchEdit = searchEdit;
        this.FROM_PAGE = i;
        this.mKeyWord = str;
        this.mShowRoute = z;
        init();
    }

    public void setPageContext(bid bid) {
        this.mPageContext = bid;
    }

    public void initPosSearch(GeoPoint geoPoint, long j, int i, String str, int i2) {
        this.mCenter = geoPoint;
        this.mAdcode = j;
        this.mHisType = i;
        this.mSuggestType = str;
        this.FROM_PAGE = i2;
    }

    private void init() {
        if (this.mContext != null && this.mSuggestListView != null) {
            this.mSuggestListView.setOnScrollListener(this);
            this.mSuggestTipItems = new ArrayList();
            SearchSuggestAdapter searchSuggestAdapter = new SearchSuggestAdapter(this.mContext, this.mSuggestTipItems, this.FROM_PAGE, this.mKeyWord, this.mShowRoute);
            this.mSuggestAdapter = searchSuggestAdapter;
            this.mSuggestAdapter.setTopListSchemaCallback(this);
            if (this.FROM_PAGE == 11102 || this.FROM_PAGE == 10300 || this.FROM_PAGE == 10049) {
                this.mFooterLayout = (LinearLayout) LayoutInflater.from(this.mContext).inflate(R.layout.search_sugg_search_more_footer_layout, null);
                this.mFooterLayout.setOnClickListener(this);
                this.mSuggestListView.addFooterView(this.mFooterLayout, null, false);
            }
            this.mSuggestListView.setAdapter(this.mSuggestAdapter);
        }
    }

    public void setOnItemEventListener(OnItemEventListener onItemEventListener) {
        if (this.mSuggestAdapter != null) {
            this.mSuggestAdapter.setOnItemEventListener(onItemEventListener);
        }
    }

    public void setCenterPoint(GeoPoint geoPoint) {
        this.mCenter = geoPoint;
    }

    public void setHisType(int i) {
        this.mHisType = i;
    }

    public void setAdcode(long j) {
        this.mAdcode = j;
    }

    public void setInputSuggestType(String str) {
        this.mSuggestType = str;
    }

    public void setFROM_PAGE(int i) {
        this.FROM_PAGE = i;
    }

    public defpackage.ahl.a getAsyncTask() {
        return this.asyncTask;
    }

    public void setAsyncTask(defpackage.ahl.a aVar) {
        this.asyncTask = aVar;
    }

    public void showSuggest(String str) {
        this.mSuggestListView.setVisibility(8);
        this.mSuggestListView.setBackgroundResource(0);
        this.mKeyWord = str;
        startSuggestNetWork();
    }

    public void clearSuggData() {
        if (this.mSuggestTipItems != null) {
            this.mSuggestTipItems.clear();
        }
        this.mSuggestAdapter.notifyDataSetChanged();
    }

    public void startSuggestNetWork() {
        cancelSuggestNetWork();
        if (this.handlerNetwork != null) {
            this.handlerNetwork.removeMessages(0);
            this.handlerNetwork.sendEmptyMessageDelayed(0, 100);
        }
    }

    public void setOnSearchMoreClickListener(OnClickListener onClickListener) {
        this.mOnSearchMoreBtnClickListener = onClickListener;
    }

    public void cancelSuggestNetWork() {
        if (this.mSearchEdit != null) {
            this.mSearchEdit.setProgressBarVisibility(false);
            if (this.mSearchEdit.getText().length() > 0) {
                this.mSearchEdit.setClearButtonVisibility(true);
            } else {
                this.mSearchEdit.setClearButtonVisibility(false);
            }
        }
        if (this.mCancelable != null) {
            this.mCancelable.a();
        }
        if (this.handlerNetwork != null) {
            this.handlerNetwork.removeCallbacksAndMessages(null);
        }
    }

    public void showInputSuggest() {
        this.mSearchEdit.showHistory();
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (this.mSuggestListView != null && i == 1) {
            View focusedChild = this.mSuggestListView.getFocusedChild();
            if (focusedChild != null) {
                focusedChild.clearFocus();
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.search_more_root && this.mOnSearchMoreBtnClickListener != null) {
            this.mOnSearchMoreBtnClickListener.onClick(view);
        }
    }

    private boolean existsPoiEmpty(List<TipItem> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.isEmpty(list.get(i).poiid)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void logOnRecommendWordResponse(List<TipItem> list) {
        if (list != null && list.size() != 0 && !SearchSuggestAdapter.isChildNodeIconType(new ArrayList(list)) && existsPoiEmpty(list)) {
            LogManager.actionLogV25("P00004", LogConstant.MORE_CAR_OWNER_SHOP, new SimpleEntry(TrafficUtil.KEYWORD, this.mKeyWord));
        }
    }

    public void processSuggestNetWorkData(final List<TipItem> list) {
        if (this.asyncTask != null && !this.asyncTask.isStopped()) {
            this.asyncTask.cancel();
        }
        this.asyncTask = ahl.b(new defpackage.ahl.a<List<TipItem>>() {
            public final /* synthetic */ void onFinished(Object obj) {
                List<TipItem> list = (List) obj;
                SearchSuggestList.this.mSuggestTipItems.clear();
                if (list == null || list.isEmpty()) {
                    SearchSuggestList.this.mSuggestListView.setBackgroundResource(0);
                    SearchSuggestList.this.mSuggestListView.setVisibility(8);
                } else {
                    SearchSuggestList.this.mSuggestListView.setBackgroundResource(R.drawable.border_bg);
                    for (TipItem add : list) {
                        SearchSuggestList.this.mSuggestTipItems.add(add);
                    }
                    if (SearchSuggestList.this.hasRectSearch()) {
                        TipItem tipItem = new TipItem();
                        tipItem.name = SearchSuggestList.this.mKeyWord;
                        tipItem.type = 3;
                        tipItem.isRectSearch = true;
                        SearchSuggestList.this.mSuggestTipItems.add(0, tipItem);
                    }
                    SearchSuggestList.this.logOnRecommendWordResponse(SearchSuggestList.this.mSuggestTipItems);
                    if (SearchSuggestList.this.mCancelable != null) {
                        if (SearchSuggestList.this.mCancelable.b()) {
                            SearchSuggestList.this.mSuggestListView.setVisibility(8);
                        } else {
                            SearchSuggestList.this.mSuggestListView.setVisibility(0);
                        }
                    }
                }
                SearchSuggestList.this.mSuggestAdapter.notifyDataSetChanged();
                SearchSuggestList.this.mSuggestListView.setSelection(0);
            }

            public final void onError(Throwable th) {
                SearchSuggestList.this.mSuggestListView.setBackgroundResource(R.drawable.border_bg);
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                return afb.a(list, SearchHistoryHelper.getInstance().getTipItems(SearchSuggestList.this.mHisType), SearchSuggestList.this.mKeyWord);
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean hasRectSearch() {
        return this.mSearchType == 1 && !this.mKeyWord.isEmpty() && this.FROM_PAGE == 11102;
    }

    public void onDestroy() {
        this.mIsActive = false;
        if (this.mSuggestListView != null) {
            this.mSuggestListView.setOnScrollListener(null);
            this.mSuggestListView.setOnTouchListener(null);
        }
        if (this.handlerNetwork != null) {
            this.handlerNetwork.removeCallbacksAndMessages(null);
            this.handlerNetwork = null;
        }
        cancelSuggestNetWork();
        if (this.mSearchEdit != null) {
            this.mSearchEdit = null;
        }
        if (this.mPageContext != null) {
            this.mPageContext = null;
        }
        if (this.mSuggestAdapter != null) {
            this.mSuggestAdapter.setTopListSchemaCallback(null);
        }
        this.mOnSearchMoreBtnClickListener = null;
    }

    public void start(Intent intent) {
        if (this.mPageContext != null && intent != null) {
            this.mPageContext.startScheme(intent);
        }
    }

    public void cancelTask() {
        if (this.asyncTask != null && !this.asyncTask.isStopped()) {
            this.asyncTask.cancel();
            this.asyncTask = null;
        }
    }
}
