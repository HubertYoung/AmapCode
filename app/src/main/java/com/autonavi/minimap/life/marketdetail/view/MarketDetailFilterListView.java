package com.autonavi.minimap.life.marketdetail.view;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class MarketDetailFilterListView extends LinearLayout {
    public static final int TAB_CATEGORY = 1;
    public static final int TAB_FLOOR = 0;
    public static final int TAB_PREFERCIAL = 2;
    /* access modifiers changed from: private */
    public List<String> mCategoryList;
    /* access modifiers changed from: private */
    public int mCurrentTab;
    private FilterListViewAdapter mFilterListViewAdapter;
    /* access modifiers changed from: private */
    public List<String> mFloorList;
    private ListView mListView;
    /* access modifiers changed from: private */
    public a mMarketDetailFilterListViewListener;
    /* access modifiers changed from: private */
    public List<String> mPrefertialList;
    /* access modifiers changed from: private */
    public String mSelectedFilterCategory;
    /* access modifiers changed from: private */
    public String mSelectedFilterFloor;
    /* access modifiers changed from: private */
    public String mSelectedFilterPrefertial;

    public class FilterListViewAdapter extends BaseAdapter {
        private int mSelectedItem = -1;

        public class a {
            public TextView a;

            public a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public FilterListViewAdapter() {
        }

        public int getCount() {
            if (MarketDetailFilterListView.this.mCurrentTab == 0 && MarketDetailFilterListView.this.mFloorList != null) {
                return MarketDetailFilterListView.this.mFloorList.size();
            }
            if (MarketDetailFilterListView.this.mCurrentTab == 1 && MarketDetailFilterListView.this.mCategoryList != null) {
                return MarketDetailFilterListView.this.mCategoryList.size();
            }
            if (MarketDetailFilterListView.this.mCurrentTab != 2 || MarketDetailFilterListView.this.mPrefertialList == null) {
                return 0;
            }
            return MarketDetailFilterListView.this.mPrefertialList.size();
        }

        public Object getItem(int i) {
            if (MarketDetailFilterListView.this.mCurrentTab == 0 && MarketDetailFilterListView.this.mFloorList != null) {
                return MarketDetailFilterListView.this.mFloorList.get(i);
            }
            if (MarketDetailFilterListView.this.mCurrentTab == 1 && MarketDetailFilterListView.this.mCategoryList != null) {
                return MarketDetailFilterListView.this.mCategoryList.get(i);
            }
            if (MarketDetailFilterListView.this.mCurrentTab != 2 || MarketDetailFilterListView.this.mPrefertialList == null) {
                return null;
            }
            return MarketDetailFilterListView.this.mPrefertialList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(MarketDetailFilterListView.this.getContext()).inflate(R.layout.indoor_market_detail_filterlist_item, viewGroup, false);
                aVar = new a();
                aVar.a = (TextView) view.findViewById(R.id.textView);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            if (MarketDetailFilterListView.this.mCurrentTab == 0 && MarketDetailFilterListView.this.mFloorList != null) {
                aVar.a.setText((CharSequence) MarketDetailFilterListView.this.mFloorList.get(i));
            } else if (MarketDetailFilterListView.this.mCurrentTab == 1 && MarketDetailFilterListView.this.mCategoryList != null) {
                aVar.a.setText((CharSequence) MarketDetailFilterListView.this.mCategoryList.get(i));
            } else if (MarketDetailFilterListView.this.mCurrentTab == 2 && MarketDetailFilterListView.this.mPrefertialList != null) {
                aVar.a.setText((CharSequence) MarketDetailFilterListView.this.mPrefertialList.get(i));
            }
            if (this.mSelectedItem == i) {
                view.setBackgroundResource(R.drawable.groupbuy_filter_main_itembg_s);
                view.setPadding(0, 0, 0, 0);
            } else {
                view.setBackgroundResource(R.drawable.groupbuy_filter_main_itembg_selector);
                view.setPadding(0, 0, 0, 0);
            }
            return view;
        }

        public void setSelectedItem(int i) {
            this.mSelectedItem = i;
        }
    }

    public interface a {
        void a(String str, String str2, String str3, boolean z);

        void j();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public MarketDetailFilterListView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.indoor_market_detail_filterlist_layout, this, true);
        intiView();
    }

    public MarketDetailFilterListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.indoor_market_detail_filterlist_layout, this, true);
        intiView();
    }

    private void intiView() {
        this.mFloorList = new ArrayList();
        this.mCategoryList = new ArrayList();
        this.mPrefertialList = new ArrayList();
        this.mSelectedFilterFloor = "";
        this.mSelectedFilterCategory = "";
        this.mSelectedFilterPrefertial = "";
        this.mListView = (ListView) findViewById(R.id.listView);
        this.mListView.setChoiceMode(1);
        if (getResources().getConfiguration().orientation == 1) {
            this.mListView.measure(0, 0);
            this.mListView.getLayoutParams().height = ags.a(getContext()).height() / 2;
        } else {
            this.mListView.measure(0, 0);
            this.mListView.getLayoutParams().height = -1;
        }
        this.mFilterListViewAdapter = new FilterListViewAdapter();
        this.mListView.setAdapter(this.mFilterListViewAdapter);
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            /* JADX WARNING: Removed duplicated region for block: B:40:0x0111  */
            /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onItemClick(android.widget.AdapterView<?> r4, android.view.View r5, int r6, long r7) {
                /*
                    r3 = this;
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r4 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    r5 = 8
                    r4.setVisibility(r5)
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r4 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r4 = r4.mSelectedFilterFloor
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r5 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r5 = r5.mSelectedFilterCategory
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r7 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r7 = r7.mSelectedFilterPrefertial
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    int r8 = r8.mCurrentTab
                    r0 = 1
                    r1 = 0
                    if (r8 != 0) goto L_0x006b
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r8 = r8.mFloorList
                    java.lang.Object r8 = r8.get(r6)
                    if (r8 == 0) goto L_0x006b
                    if (r6 != 0) goto L_0x0041
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r8 = r8.mSelectedFilterFloor
                    java.lang.String r2 = ""
                    boolean r8 = r8.equals(r2)
                    if (r8 == 0) goto L_0x0041
                    goto L_0x0109
                L_0x0041:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r8 = r8.mSelectedFilterFloor
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r2 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r2 = r2.mFloorList
                    java.lang.Object r2 = r2.get(r6)
                    boolean r8 = r8.equals(r2)
                    if (r8 != 0) goto L_0x0109
                    if (r6 != 0) goto L_0x005c
                    java.lang.String r4 = ""
                    goto L_0x0068
                L_0x005c:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r4 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r4 = r4.mFloorList
                    java.lang.Object r4 = r4.get(r6)
                    java.lang.String r4 = (java.lang.String) r4
                L_0x0068:
                    r1 = 1
                    goto L_0x0109
                L_0x006b:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    int r8 = r8.mCurrentTab
                    if (r8 != r0) goto L_0x00b9
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r8 = r8.mCategoryList
                    java.lang.Object r8 = r8.get(r6)
                    if (r8 == 0) goto L_0x00b9
                    if (r6 != 0) goto L_0x0091
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r8 = r8.mSelectedFilterCategory
                    java.lang.String r2 = ""
                    boolean r8 = r8.equals(r2)
                    if (r8 == 0) goto L_0x0091
                    goto L_0x0109
                L_0x0091:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r8 = r8.mSelectedFilterCategory
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r2 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r2 = r2.mCategoryList
                    java.lang.Object r2 = r2.get(r6)
                    boolean r8 = r8.equals(r2)
                    if (r8 != 0) goto L_0x0109
                    if (r6 != 0) goto L_0x00ac
                    java.lang.String r5 = ""
                    goto L_0x0068
                L_0x00ac:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r5 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r5 = r5.mCategoryList
                    java.lang.Object r5 = r5.get(r6)
                    java.lang.String r5 = (java.lang.String) r5
                    goto L_0x0068
                L_0x00b9:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    int r8 = r8.mCurrentTab
                    r2 = 2
                    if (r8 != r2) goto L_0x0109
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r8 = r8.mPrefertialList
                    java.lang.Object r8 = r8.get(r6)
                    if (r8 == 0) goto L_0x0109
                    if (r6 != 0) goto L_0x00df
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r8 = r8.mSelectedFilterPrefertial
                    java.lang.String r2 = ""
                    boolean r8 = r8.equals(r2)
                    if (r8 == 0) goto L_0x00df
                    goto L_0x0109
                L_0x00df:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r8 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.lang.String r8 = r8.mSelectedFilterPrefertial
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r2 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r2 = r2.mPrefertialList
                    java.lang.Object r2 = r2.get(r6)
                    boolean r8 = r8.equals(r2)
                    if (r8 != 0) goto L_0x0109
                    if (r6 != 0) goto L_0x00fc
                    java.lang.String r6 = ""
                L_0x00f9:
                    r7 = r6
                    goto L_0x0068
                L_0x00fc:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r7 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    java.util.List r7 = r7.mPrefertialList
                    java.lang.Object r6 = r7.get(r6)
                    java.lang.String r6 = (java.lang.String) r6
                    goto L_0x00f9
                L_0x0109:
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r6 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView$a r6 = r6.mMarketDetailFilterListViewListener
                    if (r6 == 0) goto L_0x011a
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView r6 = com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.this
                    com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView$a r6 = r6.mMarketDetailFilterListViewListener
                    r6.a(r4, r5, r7, r1)
                L_0x011a:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.life.marketdetail.view.MarketDetailFilterListView.AnonymousClass1.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == 1) {
            this.mListView.measure(0, 0);
            this.mListView.getLayoutParams().height = ags.a(getContext()).height() / 2;
            return;
        }
        this.mListView.measure(0, 0);
        this.mListView.getLayoutParams().height = -1;
    }

    public void showListView(int i) {
        int i2 = 0;
        if (i == 0) {
            this.mCurrentTab = 0;
            setVisibility(0);
            if (!this.mSelectedFilterFloor.equals("")) {
                i2 = this.mFloorList.indexOf(this.mSelectedFilterFloor);
            }
            this.mFilterListViewAdapter.setSelectedItem(i2);
            this.mFilterListViewAdapter.notifyDataSetChanged();
        } else if (i == 1) {
            this.mCurrentTab = 1;
            setVisibility(0);
            if (!this.mSelectedFilterCategory.equals("")) {
                i2 = this.mCategoryList.indexOf(this.mSelectedFilterCategory);
            }
            this.mFilterListViewAdapter.setSelectedItem(i2);
            this.mFilterListViewAdapter.notifyDataSetChanged();
        } else {
            if (i == 2) {
                this.mCurrentTab = 2;
                setVisibility(0);
                if (!this.mSelectedFilterPrefertial.equals("")) {
                    i2 = this.mPrefertialList.indexOf(this.mSelectedFilterPrefertial);
                }
                this.mFilterListViewAdapter.setSelectedItem(i2);
                this.mFilterListViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        setVisibility(8);
        this.mMarketDetailFilterListViewListener.j();
        return true;
    }

    public void setMarketDetailFilterListViewListener(a aVar) {
        this.mMarketDetailFilterListViewListener = aVar;
    }

    public List<String> getFloorList() {
        return this.mFloorList;
    }

    public void setFloorList(List<String> list) {
        this.mFloorList = list;
    }

    public List<String> getCategoryList() {
        return this.mCategoryList;
    }

    public void setCategoryList(List<String> list) {
        this.mCategoryList = list;
    }

    public String getSelectedFilterFloor() {
        return this.mSelectedFilterFloor;
    }

    public void setSelectedFilterFloor(String str) {
        this.mSelectedFilterFloor = str;
    }

    public String getSelectedFilterCategory() {
        return this.mSelectedFilterCategory;
    }

    public void setSelectedFilterCategory(String str) {
        this.mSelectedFilterCategory = str;
    }

    public String getSelectedFilterPrefertial() {
        return this.mSelectedFilterPrefertial;
    }

    public void setSelectedFilterPrefertial(String str) {
        this.mSelectedFilterPrefertial = str;
    }

    public void setData(dpi dpi) {
        this.mFloorList = dpi.h;
        this.mCategoryList = dpi.g;
        this.mPrefertialList = dpi.i;
        if (this.mFloorList == null) {
            this.mFloorList = new ArrayList();
        }
        if (this.mFloorList.size() == 0 || !this.mFloorList.get(0).contains(getResources().getString(R.string.market_detail_all_floor))) {
            this.mFloorList.add(0, getResources().getString(R.string.market_detail_all_floor));
        }
        if (this.mCategoryList == null) {
            this.mCategoryList = new ArrayList();
        }
        if (this.mCategoryList.size() == 0 || !this.mCategoryList.get(0).contains(getResources().getString(R.string.market_detail_all_classify))) {
            this.mCategoryList.add(0, getResources().getString(R.string.market_detail_all_classify));
        }
        if (this.mPrefertialList == null) {
            this.mPrefertialList = new ArrayList();
        }
        if (this.mPrefertialList.size() == 0 || !this.mPrefertialList.get(0).contains(getResources().getString(R.string.market_detail_all_preferencial))) {
            this.mPrefertialList.add(0, getResources().getString(R.string.market_detail_all_preferencial));
        }
        this.mSelectedFilterFloor = dpi.d;
        this.mSelectedFilterCategory = dpi.e;
        this.mSelectedFilterPrefertial = dpi.f;
    }
}
