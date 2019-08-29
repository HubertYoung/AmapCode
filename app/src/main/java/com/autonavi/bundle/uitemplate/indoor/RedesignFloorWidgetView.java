package com.autonavi.bundle.uitemplate.indoor;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class RedesignFloorWidgetView extends RelativeLayout implements bed {
    private View mFloorArrowDown;
    private View mFloorArrowUp;
    /* access modifiers changed from: private */
    public ListView mFloorList;
    /* access modifiers changed from: private */
    public FloorListAdapter mFloorListAdapter;
    private int mFloorPosOffset = -1;
    bdz mLastSelectedItem = null;
    private boolean mLastVisible = false;
    agl<bee> mListeners = new agl<>();
    /* access modifiers changed from: private */
    public int mMaxCellCount = 4;
    OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            int headerViewsCount = i - RedesignFloorWidgetView.this.mFloorList.getHeaderViewsCount();
            if (headerViewsCount >= 0 && headerViewsCount < RedesignFloorWidgetView.this.mFloorListAdapter.getCount()) {
                RedesignFloorWidgetView.this.setCurrentFloor((bdz) RedesignFloorWidgetView.this.mFloorListAdapter.getItem(headerViewsCount));
            }
        }
    };
    OnScrollListener mOnScrollListener = new OnScrollListener() {
        public void onScrollStateChanged(AbsListView absListView, int i) {
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            RedesignFloorWidgetView.this.checkArrowShow();
        }
    };
    /* access modifiers changed from: private */
    public View mRootView;
    private RedesignFloorWidgetViewLayout mRootViewLayout;

    class FloorListAdapter extends AbstractViewHolderBaseAdapter<bdz, a> {
        Context mContext;

        public FloorListAdapter(Context context) {
            this.mContext = context;
        }

        public View onCreateView(ViewGroup viewGroup, int i) {
            return LayoutInflater.from(this.mContext).inflate(R.layout.redesign_floor_item, viewGroup, false);
        }

        public a onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
            return new a(view);
        }

        public void onBindViewHolderWithData(a aVar, bdz bdz, int i, int i2) {
            if (bdz != null) {
                aVar.a = bdz;
                if (bdz != null) {
                    aVar.c.setText(bdz.a());
                    aVar.c.setSelected(bdz.c());
                    aVar.d.setSelected(bdz.c());
                }
            }
        }
    }

    public class a extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
        bdz a;
        View b;
        TextView c;
        View d;

        public a(View view) {
            super(view);
            this.b = view;
            this.c = (TextView) view.findViewById(R.id.floor_item_text);
            this.d = view.findViewById(R.id.floor_item_bg);
        }
    }

    public RedesignFloorWidgetView(Context context) {
        super(context);
        init(context);
    }

    public RedesignFloorWidgetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.redesign_floor_widget_view, this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootViewLayout = (RedesignFloorWidgetViewLayout) findViewById(R.id.floor_root_layout);
        this.mRootViewLayout.setOwner(new com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetViewLayout.a() {
            public final int a() {
                return RedesignFloorWidgetView.this.mMaxCellCount;
            }

            public final int b() {
                return RedesignFloorWidgetView.this.mFloorListAdapter.getCount();
            }

            public final View c() {
                return RedesignFloorWidgetView.this.mRootView;
            }
        });
        this.mRootView = findViewById(R.id.floor_root);
        this.mFloorArrowUp = findViewById(R.id.floor_arrow_up);
        this.mFloorArrowDown = findViewById(R.id.floor_arrow_down);
        this.mFloorList = (ListView) findViewById(R.id.floor_list);
        View inflate = View.inflate(getContext(), R.layout.redesign_floor_header, null);
        View inflate2 = View.inflate(getContext(), R.layout.redesign_floor_header, null);
        this.mFloorList.addHeaderView(inflate);
        this.mFloorList.addFooterView(inflate2);
        this.mFloorListAdapter = new FloorListAdapter(getContext());
        this.mFloorList.setAdapter(this.mFloorListAdapter);
        this.mFloorList.setOnItemClickListener(this.mOnItemClickListener);
        this.mFloorList.setOnScrollListener(this.mOnScrollListener);
    }

    public void setListData(List<bdz> list) {
        if (this.mLastSelectedItem != null) {
            this.mLastSelectedItem.a(false);
            this.mLastSelectedItem = null;
        }
        this.mFloorListAdapter.setDataAndChangeDataSet(list);
        measure(0, 0);
    }

    public void setMaxCellCount(int i) {
        if (i != this.mMaxCellCount) {
            this.mMaxCellCount = i;
            requestLayout();
        }
    }

    public boolean setCurrentFloorByName(String str) {
        bdz floorByName = getFloorByName(str);
        boolean z = floorByName != null;
        setCurrentFloor(floorByName);
        return z;
    }

    public boolean setCurrentFloorByNumber(int i) {
        bdz floorByNumber = getFloorByNumber(i);
        boolean z = floorByNumber != null;
        setCurrentFloor(floorByNumber);
        return z;
    }

    /* access modifiers changed from: private */
    public void setCurrentFloor(final bdz bdz) {
        if (bdz != null) {
            if (this.mLastSelectedItem == null || this.mLastSelectedItem != bdz) {
                if (this.mLastSelectedItem != null) {
                    this.mLastSelectedItem.a(false);
                }
                final bdz bdz2 = this.mLastSelectedItem;
                bdz.a(true);
                this.mLastSelectedItem = bdz;
                this.mFloorListAdapter.notifyDataSetChanged();
                this.mListeners.a((defpackage.agl.a<T>) new defpackage.agl.a<bee>() {
                    public final /* synthetic */ void onNotify(Object obj) {
                        ((bee) obj).a(bdz, bdz2);
                    }
                });
            }
            int firstVisiblePosition = this.mFloorList.getFirstVisiblePosition();
            int lastVisiblePosition = this.mFloorList.getLastVisiblePosition();
            int posOfFloor = getPosOfFloor(bdz);
            if (posOfFloor < 0) {
                return;
            }
            if (posOfFloor < firstVisiblePosition || posOfFloor > lastVisiblePosition) {
                int headerViewsCount = (posOfFloor - this.mFloorList.getHeaderViewsCount()) + this.mFloorPosOffset;
                if (headerViewsCount < 0) {
                    headerViewsCount = 0;
                }
                this.mFloorList.setSelection(headerViewsCount);
            }
        }
    }

    private int getPosOfFloor(bdz bdz) {
        List data = this.mFloorListAdapter.getData();
        if (data == null) {
            return -1;
        }
        int indexOf = data.indexOf(bdz);
        return indexOf >= 0 ? indexOf + this.mFloorList.getHeaderViewsCount() : indexOf;
    }

    public void resetPosByCurrentFloor() {
        bdz currentFloor = getCurrentFloor();
        if (currentFloor != null) {
            int posOfFloor = getPosOfFloor(currentFloor);
            if (posOfFloor >= 0) {
                int headerViewsCount = (posOfFloor - this.mFloorList.getHeaderViewsCount()) + this.mFloorPosOffset;
                if (headerViewsCount < 0) {
                    headerViewsCount = 0;
                }
                this.mFloorList.setSelection(headerViewsCount);
            }
        }
    }

    public bdz getFloorByName(String str) {
        if (!TextUtils.isEmpty(str)) {
            List data = this.mFloorListAdapter.getData();
            if (data != null) {
                for (bdz bdz : new ArrayList(data)) {
                    if (str.equals(bdz.a())) {
                        return bdz;
                    }
                }
            }
        }
        return null;
    }

    public bdz getFloorByNumber(int i) {
        List data = this.mFloorListAdapter.getData();
        if (data != null) {
            for (bdz bdz : new ArrayList(data)) {
                if (bdz.b() == i) {
                    return bdz;
                }
            }
        }
        return null;
    }

    public bdz getCurrentFloor() {
        return this.mLastSelectedItem;
    }

    public void setVisible(final boolean z) {
        try {
            if (this.mLastVisible != z) {
                final boolean z2 = this.mLastVisible;
                this.mLastVisible = z;
                setVisibility(z ? 0 : 8);
                if (z) {
                    invalidate();
                }
                Stub.getMapWidgetManager().requestContainerLayout();
                this.mListeners.a((defpackage.agl.a<T>) new defpackage.agl.a<bee>() {
                    public final /* synthetic */ void onNotify(Object obj) {
                        ((bee) obj).a(z, z2);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public void addListener(bee bee) {
        this.mListeners.a(bee);
    }

    public void removeListener(bee bee) {
        this.mListeners.b(bee);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void checkArrowShow() {
        /*
            r6 = this;
            android.widget.ListView r0 = r6.mFloorList
            int r0 = r0.getFirstVisiblePosition()
            r1 = 4
            r2 = 0
            if (r0 == 0) goto L_0x000c
            r0 = 0
            goto L_0x000d
        L_0x000c:
            r0 = 4
        L_0x000d:
            android.view.View r3 = r6.mFloorArrowUp
            int r3 = r3.getVisibility()
            r4 = 1
            if (r3 == r0) goto L_0x001f
            android.view.View r3 = r6.mFloorArrowUp
            r3.setVisibility(r0)
            if (r0 != 0) goto L_0x001f
            r0 = 1
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetView$FloorListAdapter r3 = r6.mFloorListAdapter
            int r3 = r3.getCount()
            android.widget.ListView r5 = r6.mFloorList
            int r5 = r5.getHeaderViewsCount()
            int r3 = r3 + r5
            android.widget.ListView r5 = r6.mFloorList
            int r5 = r5.getFooterViewsCount()
            int r3 = r3 + r5
            android.widget.ListView r5 = r6.mFloorList
            int r5 = r5.getLastVisiblePosition()
            int r3 = r3 - r4
            if (r5 == r3) goto L_0x003e
            r1 = 0
        L_0x003e:
            android.view.View r2 = r6.mFloorArrowDown
            int r2 = r2.getVisibility()
            if (r2 == r1) goto L_0x004e
            android.view.View r2 = r6.mFloorArrowDown
            r2.setVisibility(r1)
            if (r1 != 0) goto L_0x004e
            r0 = 1
        L_0x004e:
            if (r0 == 0) goto L_0x0053
            r6.requestLayout()
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetView.checkArrowShow():void");
    }
}
