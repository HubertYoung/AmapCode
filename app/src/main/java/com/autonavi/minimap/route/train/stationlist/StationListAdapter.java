package com.autonavi.minimap.route.train.stationlist;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.page.BaseStationListPage;
import java.util.ArrayList;
import java.util.List;

public class StationListAdapter extends BaseExpandableListAdapter implements com.autonavi.common.cloudsync.widget.IphoneTreeView.a {
    private static final int TYPE_COUNT = 3;
    private static final int TYPE_GRID = 1;
    private static final int TYPE_LINE = 0;
    public static final int TYPE_LINE_MORE = 2;
    private boolean isHasHistory;
    private List<ejq> mCategoryList = new ArrayList();
    private List<c> mHistoryList;
    private List<c> mHotList;
    private LayoutInflater mInflater;
    private boolean mIsSearch;
    /* access modifiers changed from: private */
    public OnChildClickListener mOnChildClickListener;
    private int mPaddingBig;
    private int mPaddingNormal;
    private BaseStationListPage mStationListPage;
    private StationManager mStationManager;

    static class a {
        public TextView a;
        public TextView b;
        public TextView c;

        a() {
        }
    }

    static class b {
        public TextView a;
        public View b;

        b() {
        }
    }

    static class c {
        Station a;
        Station b;
        Station c;

        c() {
        }
    }

    public long getChildId(int i, int i2) {
        return (long) i2;
    }

    public int getChildTypeCount() {
        return 3;
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public int getHeadViewClickStatus(int i) {
        return 0;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    public void onHeadViewClick(int i, int i2) {
    }

    public StationListAdapter(BaseStationListPage baseStationListPage, StationManager stationManager) {
        this.mStationListPage = baseStationListPage;
        this.mInflater = (LayoutInflater) this.mStationListPage.getContext().getSystemService("layout_inflater");
        this.mStationManager = stationManager;
        this.mPaddingBig = agn.a(this.mStationListPage.getContext(), 15.0f);
        this.mPaddingNormal = agn.a(this.mStationListPage.getContext(), 4.0f);
    }

    public void setLocalList(List<ejq> list, boolean z) {
        this.mCategoryList.clear();
        this.mCategoryList.addAll(list);
        this.mIsSearch = z;
        if (!z && list.size() > 1) {
            this.isHasHistory = list.get(1).a.compareTo("历史城市") == 0;
            if (this.isHasHistory) {
                this.mHistoryList = convertToGridList(list.get(1).b);
                if (list.size() > 2) {
                    this.mHotList = convertToGridList(list.get(2).b);
                }
            } else {
                this.mHotList = convertToGridList(list.get(1).b);
            }
        }
    }

    private List<c> convertToGridList(List<Station> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i += 3) {
            c cVar = new c();
            cVar.a = list.get(i);
            int i2 = i + 1;
            if (i2 < list.size()) {
                cVar.b = list.get(i2);
            }
            int i3 = i + 2;
            if (i3 < list.size()) {
                cVar.c = list.get(i3);
            }
            arrayList.add(cVar);
        }
        return arrayList;
    }

    public final List<ejq> getCategoryList() {
        return this.mCategoryList;
    }

    public Station getChild(int i, int i2) {
        List<ejq> categoryList = getCategoryList();
        if (categoryList == null || i >= categoryList.size()) {
            return null;
        }
        List<Station> list = categoryList.get(i).b;
        if (list == null || i2 >= list.size()) {
            return null;
        }
        return list.get(i2);
    }

    public int getChildrenCount(int i) {
        if (i < 0 || i >= this.mCategoryList.size()) {
            return 0;
        }
        String str = this.mCategoryList.get(i).a;
        if ("定位城市".equals(str)) {
            return 1;
        }
        if ("历史城市".equals(str)) {
            int size = this.mCategoryList.get(i).b.size();
            int i2 = size / 3;
            if (size % 3 != 0) {
                i2++;
            }
            return i2;
        } else if (!"热门城市".equals(str)) {
            return this.mCategoryList.get(i).b.size();
        } else {
            int size2 = this.mCategoryList.get(i).b.size();
            int i3 = size2 / 3;
            if (size2 % 3 != 0) {
                i3++;
            }
            return i3;
        }
    }

    public Object getGroup(int i) {
        return this.mCategoryList.get(i);
    }

    public int getGroupCount() {
        if (this.mCategoryList == null) {
            return 0;
        }
        return this.mCategoryList.size();
    }

    public View getChildView(final int i, final int i2, boolean z, View view, ViewGroup viewGroup) {
        b bVar;
        int childType = getChildType(i, i2);
        if (childType == 1) {
            if (!this.isHasHistory || i != 1) {
                return getGridView(i, i2, view, this.mHotList);
            }
            return getGridView(i, i2, view, this.mHistoryList);
        } else if (childType == 2) {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.station_list_child_line_more_item, null);
            }
            view.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    StationListAdapter.this.mOnChildClickListener.onChildClick(null, null, i, i2, 0);
                }
            });
            return view;
        } else if (childType != 0) {
            return view;
        } else {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.station_list_child_line_item, null);
                bVar = new b();
                bVar.a = (TextView) view.findViewById(R.id.name);
                bVar.b = view.findViewById(R.id.divider);
                view.setTag(bVar);
            } else {
                bVar = (b) view.getTag();
            }
            Station child = getChild(i, i2);
            if (bVar.a != null) {
                bVar.a.setText(child.name);
            }
            view.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    StationListAdapter.this.mOnChildClickListener.onChildClick(null, null, i, i2, 0);
                }
            });
            if (bVar.b != null) {
                if (z) {
                    bVar.b.setVisibility(8);
                } else {
                    bVar.b.setVisibility(0);
                }
            }
            return view;
        }
    }

    @NonNull
    public View getGridView(final int i, final int i2, View view, List<c> list) {
        a aVar;
        if (view == null) {
            view = this.mInflater.inflate(R.layout.station_list_child_item, null);
            aVar = new a();
            aVar.a = (TextView) view.findViewById(R.id.tv_station0);
            aVar.b = (TextView) view.findViewById(R.id.tv_station1);
            aVar.c = (TextView) view.findViewById(R.id.tv_station2);
            view.setTag(aVar);
            setPadding(i2, view, list);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.b.setVisibility(4);
        aVar.c.setVisibility(4);
        if (i2 >= list.size()) {
            return view;
        }
        c cVar = list.get(i2);
        aVar.a.setText(cVar.a.name);
        aVar.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                StationListAdapter.this.mOnChildClickListener.onChildClick(null, null, i, i2 * 3, 0);
            }
        });
        if (cVar.b != null) {
            aVar.b.setVisibility(0);
            aVar.b.setText(cVar.b.name);
            aVar.b.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    StationListAdapter.this.mOnChildClickListener.onChildClick(null, null, i, (i2 * 3) + 1, 0);
                }
            });
        }
        if (cVar.c != null) {
            aVar.c.setVisibility(0);
            aVar.c.setText(cVar.c.name);
            aVar.c.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    StationListAdapter.this.mOnChildClickListener.onChildClick(null, null, i, (i2 * 3) + 2, 0);
                }
            });
        }
        return view;
    }

    public void setPadding(int i, View view, List<c> list) {
        int i2 = this.mPaddingNormal;
        int i3 = this.mPaddingNormal;
        if (i == 0) {
            i2 = this.mPaddingBig;
        }
        if (i == list.size() - 1) {
            i3 = this.mPaddingBig;
        }
        view.setPadding(view.getPaddingLeft(), i2, view.getPaddingRight(), i3);
    }

    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.station_list_group_item, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(this.mCategoryList.get(i).a.toUpperCase());
        if (this.mIsSearch) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
        return view;
    }

    public int getTreeHeaderState(int i, int i2) {
        if (this.mIsSearch || i == -1) {
            return 0;
        }
        if (i2 == getChildrenCount(i) - 1) {
            return 2;
        }
        if (i2 != -1 || this.mStationListPage.b.isGroupExpanded(i)) {
            return 1;
        }
        return 0;
    }

    public void configureTreeHeader(View view, int i, int i2, int i3) {
        ((TextView) view.findViewById(R.id.indicator_text)).setText(this.mStationManager.e.get(i));
    }

    public void doRelatedActions() {
        this.mStationListPage.a(this.mStationListPage.getContext());
    }

    public int getChildType(int i, int i2) {
        if (i >= this.mCategoryList.size()) {
            return 3;
        }
        ejq ejq = this.mCategoryList.get(i);
        if (ejq == null) {
            return 3;
        }
        String str = ejq.a;
        if ("定位城市".equals(str)) {
            return 0;
        }
        if ("历史城市".equals(str) || "热门城市".equals(str)) {
            return 1;
        }
        if (!ejq.d || i2 < 5) {
            return 0;
        }
        return 2;
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }
}
