package com.autonavi.minimap.search.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.SearchEdit.OnItemEventListener;
import com.autonavi.minimap.widget.SearchHistoryAdapter;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchHistoryList implements OnClickListener, OnTouchListener, OnScrollListener {
    public static final int MODE_ALL = 0;
    public static final int MODE_CITY = 1;
    public static final String SP_KEY_current_display_history_count = "current_display_history_count";
    public static final String SP_KEY_max_display_history_count = "max_display_history_count";
    public static final String SP_NAME_search_history = "search_history";
    /* access modifiers changed from: private */
    public int FROM_PAGE;
    private defpackage.ahl.a asyncTask;
    /* access modifiers changed from: private */
    public String cityCode;
    private View clearBtn;
    /* access modifiers changed from: private */
    public Context context;
    private View displayMoreBtn;
    /* access modifiers changed from: private */
    public SearchHistoryAdapter historyAdapter;
    /* access modifiers changed from: private */
    public int historyCount;
    /* access modifiers changed from: private */
    public View historyFooter;
    /* access modifiers changed from: private */
    public ListView historyListView;
    /* access modifiers changed from: private */
    public List<TipItem> historyTipItems;
    /* access modifiers changed from: private */
    public long mAdcode;
    /* access modifiers changed from: private */
    public List<TipItem> mAllHistoryList;
    private GeoPoint mCenter;
    private a mDeleteBtnListener;
    /* access modifiers changed from: private */
    public int mHistoryType = 0;
    /* access modifiers changed from: private */
    public boolean mSelectTop = true;
    /* access modifiers changed from: private */
    public int mSizeOfHistoryShow = Integer.MAX_VALUE;
    /* access modifiers changed from: private */
    public int mode = 0;
    /* access modifiers changed from: private */
    public View noHistoryTip;
    private TextView noHistoryTipText;

    public interface a {
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
    }

    public SearchHistoryList(Context context2, ListView listView, int i, int i2) {
        this.historyListView = listView;
        this.historyListView.setMotionEventSplittingEnabled(false);
        this.context = context2;
        this.FROM_PAGE = i;
        this.mHistoryType = i2;
        init();
    }

    private void init() {
        if (this.context != null && this.historyListView != null) {
            this.historyListView.setOnScrollListener(this);
            this.historyTipItems = new ArrayList();
            this.historyAdapter = new SearchHistoryAdapter(this.context, R.layout.search_history_list_view_item, this.historyTipItems, this.FROM_PAGE);
            this.historyListView.setOnTouchListener(this);
        }
    }

    public void setFromPage(int i) {
        this.FROM_PAGE = i;
    }

    public void setMode(int i) {
        this.mode = i;
    }

    public void setCityCode(String str) {
        this.cityCode = str;
    }

    public void setCenterPoint(GeoPoint geoPoint) {
        this.mCenter = geoPoint;
    }

    public void setAdcode(long j) {
        this.mAdcode = j;
    }

    /* access modifiers changed from: private */
    public void initHistoryFooter() {
        if (this.historyFooter != null) {
            this.historyFooter.setVisibility(8);
        } else if (this.context != null && this.historyListView != null) {
            if (this.noHistoryTip != null) {
                this.historyListView.removeFooterView(this.noHistoryTip);
                this.noHistoryTip = null;
            }
            this.historyFooter = LayoutInflater.from(this.context).inflate(R.layout.search_history_list_footer_view, null);
            this.historyFooter.setVisibility(8);
            if (this.mode == 1) {
                ((TextView) this.historyFooter.findViewById(R.id.delete_all_history_btn)).setText(String.format(this.context.getResources().getString(R.string.v4_del_his_footer_clear_history), new Object[]{getCurCity()}));
            }
            this.historyListView.setBackgroundResource(R.drawable.border_bg);
            this.historyListView.addFooterView(this.historyFooter, null, false);
            this.clearBtn = this.historyFooter.findViewById(R.id.delete_all_history_btn);
            this.displayMoreBtn = this.historyFooter.findViewById(R.id.display_more_history_btn);
            this.historyFooter.setVisibility(8);
            this.displayMoreBtn.setVisibility(8);
            this.clearBtn.setOnClickListener(this);
            this.displayMoreBtn.setOnClickListener(this);
        }
    }

    public void setOnItemEventListener(OnItemEventListener onItemEventListener) {
        if (this.historyAdapter != null) {
            this.historyAdapter.setOnItemEventListener(onItemEventListener);
        }
    }

    public void setKeyWords(String str) {
        this.historyAdapter.setKeyWords(str);
    }

    public void showHistory() {
        this.historyListView.setVisibility(8);
        loadHistoryData();
    }

    public void getHistoryCount() {
        aho.a(new Runnable() {
            public final void run() {
                List list;
                switch (SearchHistoryList.this.mode) {
                    case 0:
                        list = SearchHistoryList.this.resizeTipItems(SearchHistoryHelper.getInstance(SearchHistoryList.this.context).getTipItems(SearchHistoryList.this.mHistoryType), SearchHistoryList.this.mSizeOfHistoryShow);
                        break;
                    case 1:
                        list = SearchHistoryList.this.resizeTipItems(SearchHistoryHelper.getInstance(SearchHistoryList.this.context).getTipItems(SearchHistoryList.this.cityCode, SearchHistoryList.this.mHistoryType), SearchHistoryList.this.mSizeOfHistoryShow);
                        break;
                    default:
                        list = null;
                        break;
                }
                int intValue = new MapSharePreference((String) "search_history").getIntValue(SearchHistoryList.SP_KEY_max_display_history_count, 20);
                if (list == null) {
                    list = new ArrayList();
                }
                if (list.size() < intValue) {
                    intValue = list.size();
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("itemName", intValue);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00004", LogConstant.MAIN_MIUI_TIPS_TIP_DIALOG, jSONObject);
            }
        });
    }

    private void loadHistoryData() {
        if (this.historyAdapter != null) {
            this.historyAdapter.setHaveHeader(this.historyListView.getHeaderViewsCount() != 0);
        }
        if (this.asyncTask == null) {
            initDataAsync();
        }
        if (this.asyncTask != null && !this.asyncTask.isStopped()) {
            this.asyncTask.cancel();
        }
        if (this.asyncTask != null) {
            ahl.b(this.asyncTask);
        }
    }

    public void showHistory(boolean z) {
        this.mSelectTop = z;
        this.historyListView.setVisibility(8);
        this.historyListView.setBackgroundResource(0);
        loadHistoryData();
    }

    /* access modifiers changed from: private */
    public void showNoHistoryTip() {
        if (this.noHistoryTip != null) {
            this.noHistoryTip.setVisibility(8);
        } else if (this.context != null) {
            if (this.historyFooter != null) {
                this.historyListView.removeFooterView(this.historyFooter);
                this.historyFooter = null;
            }
            LayoutInflater from = LayoutInflater.from(this.context);
            if (this.FROM_PAGE == 10077) {
                this.noHistoryTip = from.inflate(R.layout.search_no_history_tip_realbus_view, null);
            } else {
                this.noHistoryTip = from.inflate(R.layout.search_no_history_tip_view, null);
                this.noHistoryTipText = (TextView) this.noHistoryTip.findViewById(R.id.no_history_tip);
                initNoHistoryTipText();
            }
            this.noHistoryTip.setVisibility(8);
            this.historyListView.addFooterView(this.noHistoryTip, null, false);
            this.historyListView.setBackgroundResource(0);
        }
    }

    public void setHistoryAmount(int i) {
        this.mSizeOfHistoryShow = i;
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

    public void initDataAsync() {
        this.asyncTask = new defpackage.ahl.a<List<TipItem>>() {
            public final /* synthetic */ void onFinished(Object obj) {
                List list = (List) obj;
                if (list == null) {
                    list = new ArrayList();
                }
                SearchHistoryList.this.historyTipItems.clear();
                SearchHistoryList.this.historyCount = list.size();
                SearchHistoryList.this.mAllHistoryList = list;
                if (SearchHistoryList.this.historyCount == 0) {
                    SearchHistoryList.this.showNoHistoryTip();
                } else {
                    SearchHistoryList.this.initHistoryFooter();
                    SearchHistoryList.this.historyListView.setBackgroundResource(R.drawable.border_bg);
                    if (SearchHistoryList.this.mode == 0) {
                        SearchHistoryList.this.initDisplayMoreBtn();
                    }
                    for (int i = 0; i < SearchHistoryList.this.historyCount; i++) {
                        SearchHistoryList.this.historyTipItems.add(list.get(i));
                    }
                }
                if (SearchHistoryList.this.historyListView.getAdapter() == null) {
                    SearchHistoryList.this.historyListView.setAdapter(SearchHistoryList.this.historyAdapter);
                }
                SearchHistoryList.this.historyAdapter.notifyDataSetChanged();
                SearchHistoryList.this.historyListView.setVisibility(0);
                if (SearchHistoryList.this.historyFooter != null) {
                    SearchHistoryList.this.historyFooter.setVisibility(0);
                } else if (SearchHistoryList.this.noHistoryTip != null) {
                    SearchHistoryList.this.noHistoryTip.setVisibility(0);
                }
                if (SearchHistoryList.this.mSelectTop) {
                    SearchHistoryList.this.historyListView.setSelection(0);
                }
            }

            public final void onError(Throwable th) {
                SearchHistoryList.this.historyListView.setBackgroundResource(R.drawable.border_bg);
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                ArrayList arrayList = new ArrayList();
                switch (SearchHistoryList.this.mode) {
                    case 0:
                        return SearchHistoryList.this.resizeTipItems(SearchHistoryHelper.getInstance(SearchHistoryList.this.context).getTipItems(SearchHistoryList.this.mHistoryType), SearchHistoryList.this.mSizeOfHistoryShow);
                    case 1:
                        return SearchHistoryList.this.resizeTipItems(SearchHistoryHelper.getInstance(SearchHistoryList.this.context).getTipItems(SearchHistoryList.this.cityCode, SearchHistoryList.this.mHistoryType), SearchHistoryList.this.mSizeOfHistoryShow);
                    default:
                        return arrayList;
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void initDisplayMoreBtn() {
        int intValue = new MapSharePreference((String) "search_history").getIntValue(SP_KEY_max_display_history_count, 20);
        if (this.historyCount <= 20 || this.historyCount <= intValue) {
            this.displayMoreBtn.setVisibility(8);
            return;
        }
        this.historyCount = intValue;
        this.displayMoreBtn.setVisibility(0);
    }

    private void displayMoreHistory() {
        MapSharePreference mapSharePreference = new MapSharePreference((String) "search_history");
        int intValue = mapSharePreference.getIntValue(SP_KEY_max_display_history_count, 20);
        this.historyCount = this.mAllHistoryList.size();
        if (this.historyCount <= intValue) {
            this.displayMoreBtn.setVisibility(8);
        } else {
            this.historyCount = intValue;
            this.displayMoreBtn.setVisibility(0);
        }
        for (int intValue2 = mapSharePreference.getIntValue(SP_KEY_current_display_history_count, 20); intValue2 < this.historyCount; intValue2++) {
            this.historyTipItems.add(this.mAllHistoryList.get(intValue2));
        }
        this.historyAdapter.notifyDataSetChanged();
    }

    public defpackage.ahl.a getAsyncTask() {
        return this.asyncTask;
    }

    public void setAsyncTask(defpackage.ahl.a aVar) {
        this.asyncTask = aVar;
    }

    public void deleteHistory(TipItem tipItem) {
        if (tipItem != null && this.context != null) {
            this.mSelectTop = false;
            SearchHistoryHelper.getInstance(this.context).deleteItem(tipItem);
            loadHistoryData();
        }
    }

    /* access modifiers changed from: private */
    public void deleteAllHistory() {
        new Thread("SearchHistoryListThread") {
            public final void run() {
                if (SearchHistoryList.this.mode == 0) {
                    SearchHistoryHelper.getInstance(SearchHistoryList.this.context).deleteRecordByHistoryType(SearchHistoryList.this.mHistoryType);
                    return;
                }
                if (SearchHistoryList.this.mode == 1) {
                    SearchHistoryHelper instance = SearchHistoryHelper.getInstance(SearchHistoryList.this.context);
                    StringBuilder sb = new StringBuilder();
                    sb.append(SearchHistoryList.this.mAdcode);
                    instance.deleteRecordByHistoryTypeAndAdCode(sb.toString(), SearchHistoryList.this.mHistoryType);
                }
            }
        }.start();
        this.historyTipItems.clear();
        showNoHistoryTip();
        if (this.noHistoryTip != null) {
            this.noHistoryTip.setVisibility(0);
        }
        this.historyAdapter.notifyDataSetChanged();
    }

    private String getCurCity() {
        lj ljVar;
        if (this.mCenter != null) {
            ljVar = li.a().b(this.mCenter.x, this.mCenter.y);
        } else if (this.mAdcode != 0) {
            ljVar = li.a().a((int) this.mAdcode);
        } else {
            ljVar = null;
        }
        return ljVar != null ? ljVar.a : "";
    }

    public void onClick(View view) {
        if (view.equals(this.displayMoreBtn)) {
            displayMoreHistoryLogManager();
            MapSharePreference mapSharePreference = new MapSharePreference((String) "search_history");
            int intValue = mapSharePreference.getIntValue(SP_KEY_max_display_history_count, 20);
            mapSharePreference.putIntValue(SP_KEY_current_display_history_count, intValue);
            mapSharePreference.putIntValue(SP_KEY_max_display_history_count, intValue + 20);
            displayMoreHistory();
            return;
        }
        if (view.equals(this.clearBtn)) {
            LogManager.actionLogV2(LogConstant.SEARCH_HISTORY_CLEAR, "B001");
            final bid pageContext = AMapPageUtil.getPageContext();
            KeyboardUtil.hideInputMethod(pageContext.getActivity());
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(pageContext.getActivity());
            aVar.a(R.string.clean_history_);
            aVar.a(R.string.del_now, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                    if (SearchHistoryList.this.FROM_PAGE == 11102) {
                        LogManager.actionLogV2("P00004", "B006");
                    } else if (SearchHistoryList.this.FROM_PAGE == 10049) {
                        LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B003");
                    } else if (SearchHistoryList.this.FROM_PAGE == 10300) {
                        LogManager.actionLogV2("P00003", "B003");
                    }
                    SearchHistoryList.this.deleteAllHistory();
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

    private void hideInputMethod() {
        if (!(this.context == null || this.historyListView == null)) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.context.getSystemService("input_method");
            try {
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(this.historyListView.getApplicationWindowToken(), 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initNoHistoryTipText() {
        String str;
        String str2;
        if (this.context != null && this.noHistoryTip != null) {
            Resources resources = this.context.getResources();
            int i = this.FROM_PAGE;
            if (i != 10045) {
                if (i == 10049) {
                    String string = resources.getString(R.string.category_arround_no_history_tip);
                    str2 = String.format(string, new Object[]{"“该点”周边", ""});
                    if (needTwoLineDisplay(this.noHistoryTipText, str2)) {
                        str = String.format(string, new Object[]{"“该点”周边", "\n"});
                        this.noHistoryTipText.setText(str);
                    }
                    str = str2;
                    this.noHistoryTipText.setText(str);
                } else if (i != 10062) {
                    if (i == 10077) {
                        String string2 = resources.getString(R.string.bus_favorite_no_history_tip);
                        str2 = String.format(string2, new Object[]{"公交线路", ""});
                        if (needTwoLineDisplay(this.noHistoryTipText, str2)) {
                            str = String.format(string2, new Object[]{"公交线路", "\n"});
                            this.noHistoryTipText.setText(str);
                        }
                        str = str2;
                        this.noHistoryTipText.setText(str);
                    } else if (i == 10114) {
                        String string3 = resources.getString(R.string.bus_favorite_no_history_tip);
                        str2 = String.format(string3, new Object[]{"公交及地铁线路、站点", ""});
                        if (needTwoLineDisplay(this.noHistoryTipText, str2)) {
                            str = String.format(string3, new Object[]{"公交及地铁线路、站点", "\n"});
                            this.noHistoryTipText.setText(str);
                        }
                        str = str2;
                        this.noHistoryTipText.setText(str);
                    } else if (i != 10300) {
                        if (i == 11102) {
                            String string4 = resources.getString(R.string.home_route_navigation_no_history_tip);
                            str2 = String.format(string4, new Object[]{""});
                            if (needTwoLineDisplay(this.noHistoryTipText, str2)) {
                                str = String.format(string4, new Object[]{"\n"});
                            }
                            str = str2;
                        } else if (i != 12400) {
                            str = resources.getString(R.string.default_no_history_tip);
                        }
                        this.noHistoryTipText.setText(str);
                    } else {
                        String string5 = resources.getString(R.string.category_arround_no_history_tip);
                        str2 = String.format(string5, new Object[]{"“我的位置”附近", ""});
                        if (needTwoLineDisplay(this.noHistoryTipText, str2)) {
                            str = String.format(string5, new Object[]{"“我的位置”附近", "\n"});
                            this.noHistoryTipText.setText(str);
                        }
                        str = str2;
                        this.noHistoryTipText.setText(str);
                    }
                }
            }
            String string6 = resources.getString(R.string.bus_favorite_no_history_tip);
            str2 = String.format(string6, new Object[]{"地点", ""});
            if (needTwoLineDisplay(this.noHistoryTipText, str2)) {
                str = String.format(string6, new Object[]{"地点", "\n"});
                this.noHistoryTipText.setText(str);
            }
            str = str2;
            this.noHistoryTipText.setText(str);
        }
    }

    private void displayMoreHistoryLogManager() {
        if (this.FROM_PAGE == 11102) {
            LogManager.actionLogV2("P00004", "B028");
        } else if (this.FROM_PAGE == 10300) {
            LogManager.actionLogV2("P00003", "B011");
        } else if (this.FROM_PAGE == 10049) {
            LogManager.actionLogV2(LogConstant.SEARCH_FROM_ARROUND_SEARCH, "B014");
        } else {
            if (this.FROM_PAGE != 12400 && this.FROM_PAGE == 10062) {
                LogManager.actionLogV2(LogConstant.PAGE_ID_MAIN_NAVI, "B014");
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            hideInputMethod();
        }
        return false;
    }

    private boolean needTwoLineDisplay(TextView textView, String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        Point point = new Point();
        ((WindowManager) this.context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getSize(point);
        if (textView.getPaint().measureText(str) > ((float) point.x) - ((float) agn.a(this.context, 92.0f))) {
            return true;
        }
        return false;
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (this.historyListView != null && i == 1) {
            View focusedChild = this.historyListView.getFocusedChild();
            if (focusedChild != null) {
                focusedChild.clearFocus();
            }
        }
    }

    public void setOnDeleteAllRecordBtnListener(a aVar) {
        this.mDeleteBtnListener = aVar;
    }

    public void cancelTask() {
        if (this.asyncTask != null && !this.asyncTask.isStopped()) {
            this.asyncTask.cancel();
            this.asyncTask = null;
        }
    }
}
