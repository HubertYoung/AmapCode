package com.autonavi.minimap.search.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.SearchEdit.OnItemEventListener;
import com.autonavi.minimap.widget.SearchHistoryAdapter;
import com.autonavi.minimap.widget.SearchSuggestAdapter;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import java.util.ArrayList;
import java.util.List;

public class SearchCallBackComponentWrapper implements OnClickListener, OnScrollListener {
    private int FROM_PAGE = 0;
    /* access modifiers changed from: private */
    public int HistoryType = 0;
    private final defpackage.ahl.a aHistoryTask = new defpackage.ahl.a<List<TipItem>>() {
        public final /* synthetic */ void onFinished(Object obj) {
            List list = (List) obj;
            if (list == null) {
                list = new ArrayList();
            }
            SearchCallBackComponentWrapper.this.allHistoryTipItems = list;
            ArrayList arrayList = new ArrayList();
            int size = list.size();
            if (list.size() == 0) {
                SearchCallBackComponentWrapper.this.component.addNoHistoryTip();
                SearchCallBackComponentWrapper.this.mHistoryView.setBackgroundResource(0);
            } else {
                SearchCallBackComponentWrapper.this.mHistoryView.setBackgroundResource(R.drawable.border_bg);
                int intValue = SearchCallBackComponentWrapper.this.searchHistorySharedPref.getIntValue(SearchHistoryList.SP_KEY_max_display_history_count, 20);
                if (size <= 3 || size < intValue) {
                    SearchCallBackComponentWrapper.this.component.addHistoryFooter(false);
                    arrayList.addAll(list);
                } else {
                    SearchCallBackComponentWrapper.this.component.addHistoryFooter(true);
                    arrayList.addAll(SearchCallBackComponentWrapper.this.allHistoryTipItems.subList(0, intValue));
                }
            }
            SearchCallBackComponentWrapper.this.historyTipItems.clear();
            SearchCallBackComponentWrapper.this.historyTipItems.addAll(arrayList);
            SearchCallBackComponentWrapper.this.historyAdapter.notifyDataSetChanged();
            SearchCallBackComponentWrapper.this.mHistoryView.setVisibility(0);
            SearchCallBackComponentWrapper.this.mHistoryView.setSelection(0);
        }

        public final void onError(Throwable th) {
            SearchCallBackComponentWrapper.this.mHistoryView.setBackgroundResource(R.drawable.border_bg);
        }

        public final /* synthetic */ Object doBackground() throws Exception {
            return SearchHistoryHelper.getInstance(SearchCallBackComponentWrapper.this.context).getTipItems(SearchCallBackComponentWrapper.this.HistoryType);
        }
    };
    private defpackage.ahl.a aSuggTask;
    /* access modifiers changed from: private */
    public List<TipItem> allHistoryTipItems = new ArrayList();
    /* access modifiers changed from: private */
    public SearchCallBackComponent component;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public SearchHistoryAdapter historyAdapter;
    /* access modifiers changed from: private */
    public List<TipItem> historyTipItems = new ArrayList();
    /* access modifiers changed from: private */
    public adx mCancelable;
    /* access modifiers changed from: private */
    public ListView mHistoryView;
    /* access modifiers changed from: private */
    public ListView mSuggView;
    /* access modifiers changed from: private */
    public SearchSuggestAdapter mSuggestAdapter;
    /* access modifiers changed from: private */
    public List<TipItem> mSuggestTipItems = new ArrayList();
    MapSharePreference searchHistorySharedPref = new MapSharePreference((String) "search_history");
    /* access modifiers changed from: private */
    public a suggData = new a();

    public enum Type {
        ALL,
        HISTORY,
        SUGG
    }

    public static class a {
        GeoPoint a;
        long b;
        int c;
        String d;
        String e = "poi|bus|busline";
        public String f;
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    public SearchCallBackComponentWrapper(Context context2, bid bid, SearchCallBackComponent searchCallBackComponent) {
        this.context = context2;
        this.component = searchCallBackComponent;
        this.component.setOnClickListener(this);
        this.mSuggView = this.component.getmSuggListView();
        this.mHistoryView = this.component.getmHistoryListView();
        this.mSuggView.setOnScrollListener(this);
        this.historyAdapter = new SearchHistoryAdapter(context2, R.layout.search_history_list_view_item, this.historyTipItems, 0);
        SearchSuggestAdapter searchSuggestAdapter = new SearchSuggestAdapter(context2, this.mSuggestTipItems, this.FROM_PAGE, "", false);
        this.mSuggestAdapter = searchSuggestAdapter;
        this.component.setHistoryAdapter(this.historyAdapter);
        this.component.setSuggAdapter(this.mSuggestAdapter);
    }

    public void initSuggParam(GeoPoint geoPoint, long j, int i, String str, int i2) {
        this.suggData.a = geoPoint;
        this.suggData.b = j;
        this.suggData.c = i;
        this.suggData.e = str;
        this.FROM_PAGE = i2;
    }

    public void hiddenSugg() {
        if (this.mSuggestTipItems != null) {
            this.mSuggestTipItems.clear();
        }
        this.mSuggestAdapter.notifyDataSetChanged();
        if (this.mSuggView != null) {
            this.mSuggView.setVisibility(8);
        }
    }

    public void hiddenHistory() {
        if (this.mHistoryView != null) {
            this.mHistoryView.setVisibility(8);
        }
    }

    public void showSugg(final String str, final Callback callback) {
        this.aHistoryTask.cancel();
        if (this.mSuggestTipItems != null) {
            this.mSuggestTipItems.clear();
        }
        this.mSuggestAdapter.notifyDataSetChanged();
        this.suggData.f = str;
        this.mHistoryView.setVisibility(8);
        this.mSuggView.setVisibility(8);
        if (!TextUtils.isEmpty(str)) {
            if (this.mSuggestAdapter != null) {
                this.mSuggestAdapter.setKeyWord(str);
            }
            ISearchService iSearchService = (ISearchService) defpackage.esb.a.a.a(ISearchService.class);
            if (iSearchService != null) {
                int i = this.FROM_PAGE == 10049 ? 1 : 2;
                SuggestionParam suggestionParam = new SuggestionParam();
                suggestionParam.adcode = this.suggData.b;
                suggestionParam.keyWord = str;
                suggestionParam.category = this.suggData.d;
                suggestionParam.suggestType = this.suggData.e;
                suggestionParam.mCenter = this.suggData.a;
                this.mCancelable = iSearchService.a(suggestionParam, i, (aeb<aux>) new aeb<aux>() {
                    public final /* synthetic */ void callback(Object obj) {
                        aux aux = (aux) obj;
                        if (callback != null) {
                            callback.callback(null);
                        }
                        SearchCallBackComponentWrapper.this.suggData.f = str;
                        SearchCallBackComponentWrapper.this.suggData.c = aux.a;
                        SearchCallBackComponentWrapper.this.processSuggestNetWorkData(aux.b);
                    }

                    public final void error(int i) {
                        AMapLog.w("SearchCallBackComponentWrapper", String.valueOf(i));
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void processSuggestNetWorkData(final List<TipItem> list) {
        if (this.aSuggTask != null && !this.aSuggTask.isStopped()) {
            this.aSuggTask.cancel();
        }
        this.aSuggTask = ahl.b(new defpackage.ahl.a<List<TipItem>>() {
            public final /* synthetic */ void onFinished(Object obj) {
                List<TipItem> list = (List) obj;
                ArrayList arrayList = new ArrayList();
                if (list == null || list.isEmpty()) {
                    SearchCallBackComponentWrapper.this.mSuggView.setBackgroundResource(0);
                    SearchCallBackComponentWrapper.this.mSuggView.setVisibility(0);
                } else {
                    SearchCallBackComponentWrapper.this.mSuggView.setBackgroundResource(R.drawable.border_bg);
                    for (TipItem add : list) {
                        arrayList.add(add);
                    }
                    if (SearchCallBackComponentWrapper.this.mCancelable != null) {
                        if (SearchCallBackComponentWrapper.this.mCancelable.b()) {
                            SearchCallBackComponentWrapper.this.mSuggView.setVisibility(8);
                        } else {
                            SearchCallBackComponentWrapper.this.mSuggView.setVisibility(0);
                        }
                    }
                }
                SearchCallBackComponentWrapper.this.mSuggestTipItems.addAll(arrayList);
                SearchCallBackComponentWrapper.this.mSuggestAdapter.notifyDataSetChanged();
                SearchCallBackComponentWrapper.this.mSuggView.setSelection(0);
            }

            public final void onError(Throwable th) {
                SearchCallBackComponentWrapper.this.mSuggView.setBackgroundResource(R.drawable.border_bg);
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                if (SearchCallBackComponentWrapper.this.mCancelable != null) {
                    return afb.a(list, SearchHistoryHelper.getInstance().getTipItems(SearchCallBackComponentWrapper.this.suggData.c), SearchCallBackComponentWrapper.this.suggData.f);
                }
                return null;
            }
        });
    }

    public void showHistory() {
        boolean z = false;
        this.mHistoryView.setBackgroundResource(0);
        this.mSuggView.setVisibility(8);
        this.mHistoryView.setVisibility(8);
        if (this.historyAdapter != null) {
            SearchHistoryAdapter searchHistoryAdapter = this.historyAdapter;
            if (this.mHistoryView.getHeaderViewsCount() != 0) {
                z = true;
            }
            searchHistoryAdapter.setHaveHeader(z);
        }
        if (this.aHistoryTask != null && !this.aHistoryTask.isStopped()) {
            this.aHistoryTask.cancel();
        }
        if (this.mCancelable != null) {
            this.mCancelable.a();
        }
        if (this.aHistoryTask != null) {
            ahl.b(this.aHistoryTask);
        }
    }

    public void setHomeCompanyListener(OnClickListener onClickListener) {
        if (this.component != null) {
            this.component.setHomeCompanyListener(onClickListener);
        }
    }

    public void setPositionListener(OnClickListener onClickListener) {
        if (this.component != null) {
            this.component.setPositionListener(onClickListener);
        }
    }

    public void setFavoriteListener(OnClickListener onClickListener) {
        if (this.component != null) {
            this.component.setFavoriteListener(onClickListener);
        }
    }

    public void setPointListener(OnClickListener onClickListener) {
        if (this.component != null) {
            this.component.setPointListener(onClickListener);
        }
    }

    public void setHistoryOnTouchListener(OnTouchListener onTouchListener) {
        if (this.component != null) {
            this.component.setOnTouchListener(onTouchListener);
        }
    }

    public void setSuggOnTouchListener(OnTouchListener onTouchListener) {
        if (this.component != null) {
            this.component.setOnTouchListener(onTouchListener);
        }
    }

    public void setHistoryOnItemEventListener(OnItemEventListener onItemEventListener) {
        if (this.historyAdapter != null) {
            this.historyAdapter.setOnItemEventListener(onItemEventListener);
        }
    }

    public void setSuggOnItemEventListener(OnItemEventListener onItemEventListener) {
        if (this.component != null) {
            this.mSuggestAdapter.setOnItemEventListener(onItemEventListener);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.display_more_history_btn) {
            int intValue = this.searchHistorySharedPref.getIntValue(SearchHistoryList.SP_KEY_max_display_history_count, 20) + 20;
            this.searchHistorySharedPref.putIntValue(SearchHistoryList.SP_KEY_max_display_history_count, intValue);
            if (this.allHistoryTipItems.size() <= intValue) {
                this.component.addHistoryFooter(false);
            } else {
                this.component.addHistoryFooter(true);
            }
            this.historyTipItems.clear();
            if (intValue > this.allHistoryTipItems.size()) {
                this.historyTipItems.addAll(this.allHistoryTipItems);
            } else {
                this.historyTipItems.addAll(this.allHistoryTipItems.subList(0, intValue));
            }
            this.historyAdapter.notifyDataSetChanged();
            return;
        }
        if (view.getId() == R.id.delete_all_history_btn) {
            LogManager.actionLogV2(LogConstant.SEARCH_HISTORY_CLEAR, "B001");
            final bid pageContext = AMapPageUtil.getPageContext();
            KeyboardUtil.hideInputMethod(pageContext.getActivity());
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar.a(R.string.clean_history_);
            aVar.a(R.string.del_now, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    ahm.a(new Runnable() {
                        public final void run() {
                            SearchHistoryHelper.getInstance(SearchCallBackComponentWrapper.this.context).deleteRecordByHistoryType(0);
                        }
                    });
                    SearchCallBackComponentWrapper.this.historyTipItems.clear();
                    SearchCallBackComponentWrapper.this.historyAdapter.notifyDataSetChanged();
                    SearchCallBackComponentWrapper.this.component.addNoHistoryTip();
                    SearchCallBackComponentWrapper.this.mHistoryView.setBackgroundResource(0);
                }
            });
            aVar.b(R.string.cancel, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    LogManager.actionLogV2(LogConstant.SEARCH_HISTORY_CLEAR, "B002");
                }
            });
            aVar.a(true);
            AlertView a2 = aVar.a();
            pageContext.showViewLayer(a2);
            a2.startAnimation();
        }
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (this.mSuggView != null && i == 1) {
            View focusedChild = this.mSuggView.getFocusedChild();
            if (focusedChild != null) {
                focusedChild.clearFocus();
            }
        }
    }
}
