package com.autonavi.minimap.route.bus.realtimebus.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusAroundAdapter;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.c;
import java.text.DecimalFormat;
import java.util.List;

public class RealBusPositionArroundPanelView extends RelativeLayout {
    private static final int DEFAULT_STATUS_BAR_HEIGHT = 50;
    private static final int TRIGER_SLIDING_THRESHOLD = 50;
    /* access modifiers changed from: private */
    public boolean isFirstLayout = true;
    private ImageView listview_status_img;
    private TextView listview_status_loadingtext;
    private TextView listview_status_text;
    private RTBusAroundAdapter mAdapter;
    /* access modifiers changed from: private */
    public float mClosePoint;
    private ListStatus mCurrentListStatus;
    /* access modifiers changed from: private */
    public float mExpendPoint;
    /* access modifiers changed from: private */
    public View mListShadow;
    private ListView mListView;
    /* access modifiers changed from: private */
    public dvp mPageInteraction;
    private OnClickListener mRefreshClickHandler = new OnClickListener() {
        public final void onClick(View view) {
            RealBusPositionArroundPanelView.this.doRefresh();
        }
    };
    /* access modifiers changed from: private */
    public SlidingUpPanelLayout mSlidingLayout;
    private RelativeLayout mStatusClickArea;
    private RelativeLayout mStatusLayout;
    /* access modifiers changed from: private */
    public View mTitleBar;
    private View rt_bus_list_toggle;
    private ProgressBar rt_list_refresh_progress;
    private ImageView static_icon;
    private TextView station_distance;
    private TextView station_name;

    public enum ListStatus {
        LS_SUCCESS,
        LS_LOADING,
        LS_ERROR,
        LS_EMPTY
    }

    public RealBusPositionArroundPanelView(Context context) {
        super(context);
    }

    public RealBusPositionArroundPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RealBusPositionArroundPanelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSlidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        this.mTitleBar = findViewById(R.id.title_bar);
        this.rt_bus_list_toggle = findViewById(R.id.rt_bus_list_toggle);
        this.static_icon = (ImageView) findViewById(R.id.static_icon);
        this.station_name = (TextView) findViewById(R.id.station_name);
        this.station_distance = (TextView) findViewById(R.id.station_distance);
        this.mListView = (ListView) findViewById(R.id.realtimebus_around_listview);
        this.mListShadow = findViewById(R.id.rt_list_shadow);
        this.mStatusLayout = (RelativeLayout) findViewById(R.id.realtimebus_around_listview_status);
        this.mStatusClickArea = (RelativeLayout) findViewById(R.id.listview_status_click_area);
        this.listview_status_img = (ImageView) findViewById(R.id.listview_status_img);
        this.listview_status_text = (TextView) findViewById(R.id.listview_status_text);
        this.rt_list_refresh_progress = (ProgressBar) findViewById(R.id.rt_list_refresh_progress);
        this.listview_status_loadingtext = (TextView) findViewById(R.id.listview_status_loadingtext);
        init();
        initPanelLayout();
        setListViewStatus(ListStatus.LS_LOADING);
    }

    public void setIsSupportRTBus(boolean z) {
        if (this.mAdapter != null) {
            this.mAdapter.setIsSupportRTBus(z);
        }
    }

    private void init() {
        NoDBClickUtil.a(this.mTitleBar, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (RealBusPositionArroundPanelView.this.mSlidingLayout != null) {
                    if (RealBusPositionArroundPanelView.this.mSlidingLayout.getPanelState() == PanelState.EXPANDED) {
                        RealBusPositionArroundPanelView.this.mSlidingLayout.setPanelState(PanelState.ANCHORED);
                    } else if (RealBusPositionArroundPanelView.this.mSlidingLayout.getPanelState() == PanelState.ANCHORED) {
                        RealBusPositionArroundPanelView.this.mSlidingLayout.setPanelState(PanelState.EXPANDED);
                    }
                }
            }
        });
        this.mSlidingLayout.setDragView(this.mTitleBar);
        this.mSlidingLayout.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                RealBusPositionArroundPanelView.this.adjustListHight();
                if (RealBusPositionArroundPanelView.this.isFirstLayout) {
                    RealBusPositionArroundPanelView.this.isFirstLayout = false;
                    RealBusPositionArroundPanelView.this.mSlidingLayout.setPanelState(PanelState.ANCHORED);
                }
            }
        });
        this.mAdapter = new RTBusAroundAdapter(getContext());
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnScrollListener(new OnScrollListener() {
            private int mScrollState;

            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            }

            private void checkBlockTouchEvent(int i) {
                this.mScrollState = i;
                if (this.mScrollState != 0) {
                    RealBusPositionArroundPanelView.this.mSlidingLayout.setDragView(RealBusPositionArroundPanelView.this.mTitleBar);
                    RealBusPositionArroundPanelView.this.setScrollAtTop(false);
                    return;
                }
                RealBusPositionArroundPanelView.this.setScrollAtTop(RealBusPositionArroundPanelView.this.isListViewSrollTop());
            }

            public void onScrollStateChanged(AbsListView absListView, int i) {
                checkBlockTouchEvent(i);
            }
        });
        this.mSlidingLayout.addPanelSlideListener(new c() {
            public final void onPanelSlide(View view, float f) {
                if (RealBusPositionArroundPanelView.this.mPageInteraction != null) {
                    RealBusPositionArroundPanelView.this.mPageInteraction.a(RealBusPositionArroundPanelView.this.mSlidingLayout.getSlideOffsetHeight());
                }
                RealBusPositionArroundPanelView.this.setStatusLayout();
            }

            public final void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
                RealBusPositionArroundPanelView.this.adjustListHight();
                RealBusPositionArroundPanelView.this.mListShadow.setVisibility(panelState2 == PanelState.EXPANDED ? 8 : 0);
                if (panelState2 == PanelState.EXPANDED) {
                    RealBusPositionArroundPanelView.this.mSlidingLayout.setDragView(RealBusPositionArroundPanelView.this.mTitleBar);
                    RealBusPositionArroundPanelView.this.setScrollAtTop(RealBusPositionArroundPanelView.this.isListViewSrollTop());
                    RealBusPositionArroundPanelView.this.mSlidingLayout.setExpandPoint(RealBusPositionArroundPanelView.this.mClosePoint);
                } else if (panelState2 == PanelState.COLLAPSED) {
                    RealBusPositionArroundPanelView.this.collapsePanel();
                } else {
                    if (panelState2 == PanelState.ANCHORED) {
                        RealBusPositionArroundPanelView.this.mSlidingLayout.setDragView(RealBusPositionArroundPanelView.this.mTitleBar);
                        RealBusPositionArroundPanelView.this.setScrollAtTop(RealBusPositionArroundPanelView.this.isListViewSrollTop());
                        RealBusPositionArroundPanelView.this.mSlidingLayout.setExpandPoint(RealBusPositionArroundPanelView.this.mExpendPoint);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean isListViewSrollTop() {
        ListView listView = this.mListView;
        return listView.getChildCount() > 0 && listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() >= listView.getPaddingTop();
    }

    /* access modifiers changed from: private */
    public void setScrollAtTop(boolean z) {
        if (!z || !(this.mSlidingLayout.getPanelState() == PanelState.EXPANDED || this.mSlidingLayout.getPanelState() == PanelState.ANCHORED)) {
            this.mSlidingLayout.setScrollAtTop(false, (View) null);
        } else {
            this.mSlidingLayout.setScrollAtTop(true, (View) this.mListView);
        }
    }

    private void initPanelLayout() {
        int i;
        int height = ((WindowManager) getContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getHeight();
        if (getContext() instanceof Activity) {
            Rect rect = new Rect();
            ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            i = Math.min(50, height - rect.height());
        } else {
            i = 50;
        }
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.selectpoi_top_title_height);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.rt_page_list_dragview_titlebar_height);
        int i2 = (height - dimensionPixelSize) - i;
        int i3 = (dimensionPixelSize + i2) / 2;
        this.mSlidingLayout.setAnchorHeight(i3);
        int i4 = i3 - dimensionPixelSize2;
        this.mSlidingLayout.setPanelHeight(i4);
        float f = (float) (i2 - i4);
        this.mExpendPoint = ((float) (dimensionPixelSize2 + 50)) / f;
        this.mClosePoint = (f - 50.0f) / f;
        this.mSlidingLayout.setExpandPoint(this.mExpendPoint);
        setStatusLayout();
    }

    public void setData(List<dyi> list, boolean z) {
        if (this.mAdapter != null) {
            this.mAdapter.setData(list, z);
        }
        showStation(z ? 0 : -1);
    }

    public void setAroundRetryTimes(int i) {
        if (this.mAdapter != null) {
            this.mAdapter.putExtra((String) RTBusAroundAdapter.EXT_RT_BUS_RETRY_TIMES, i);
        }
    }

    public void onListItemDataChanged() {
        showStation(-1);
    }

    public void showStation(int i) {
        int currentStationIndex = this.mAdapter != null ? this.mAdapter.getCurrentStationIndex() : 0;
        if (i < 0) {
            i = currentStationIndex;
        }
        if (i < 0) {
            i = 0;
        }
        dyi showStation = this.mAdapter != null ? this.mAdapter.showStation(i) : null;
        if (!(this.mListView == null || i == currentStationIndex)) {
            this.mListView.setSelection(0);
        }
        updateStation(showStation);
        updateListStatus();
    }

    public void setRTBusPageInteraction(dvp dvp) {
        this.mPageInteraction = dvp;
        if (this.mAdapter != null) {
            this.mAdapter.setListItemInteraction(dvp);
        }
    }

    private void updateStation(dyi dyi) {
        String str;
        if (dyi != null) {
            this.static_icon.setVisibility(0);
            this.station_name.setText(dyi.b);
            float a = cfe.a(LocationInstrument.getInstance().getLatestPosition().getLongitude(), LocationInstrument.getInstance().getLatestPosition().getLatitude(), dyi.d, dyi.c);
            TextView textView = this.station_distance;
            float f = a / 1000.0f;
            if (f < 1.0f) {
                StringBuilder sb = new StringBuilder();
                sb.append((int) a);
                sb.append("米");
                str = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(new DecimalFormat("#.0").format((double) f));
                sb2.append("公里");
                str = sb2.toString();
            }
            textView.setText(str);
            return;
        }
        this.static_icon.setVisibility(4);
        this.station_name.setText(null);
        this.station_distance.setText(null);
    }

    /* access modifiers changed from: private */
    public boolean collapsePanel() {
        if (this.mSlidingLayout == null || (this.mSlidingLayout.getPanelState() != PanelState.EXPANDED && this.mSlidingLayout.getPanelState() != PanelState.COLLAPSED)) {
            return false;
        }
        this.mSlidingLayout.setPanelState(PanelState.ANCHORED);
        return true;
    }

    public boolean onBackPressed() {
        return collapsePanel();
    }

    /* access modifiers changed from: private */
    public void adjustListHight() {
        PanelState panelState = this.mSlidingLayout.getPanelState();
        if (panelState == PanelState.ANCHORED) {
            setListViewHeight(this.mSlidingLayout.getSlideOffsetHeight() - this.mTitleBar.getHeight());
        } else if (panelState == PanelState.DRAGGING) {
            setListViewHeight(this.mSlidingLayout.getHeight());
            setListViewHeight(this.mSlidingLayout.getHeight());
        } else {
            if (panelState == PanelState.EXPANDED) {
                setListViewHeight(this.mSlidingLayout.getHeight());
            }
        }
    }

    private void setListViewHeight(int i) {
        LayoutParams layoutParams = this.mListView.getLayoutParams();
        if (layoutParams.height != i) {
            layoutParams.height = i;
            this.mListView.setLayoutParams(layoutParams);
        }
    }

    /* access modifiers changed from: private */
    public void setStatusLayout() {
        int slideOffsetHeight = this.mSlidingLayout.getSlideOffsetHeight() - this.mTitleBar.getHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mStatusLayout.getLayoutParams();
        if (layoutParams.height != slideOffsetHeight) {
            layoutParams.height = slideOffsetHeight;
            this.mStatusLayout.setLayoutParams(layoutParams);
        }
    }

    public void doRefresh() {
        updateStation(null);
        if (this.mCurrentListStatus != ListStatus.LS_LOADING) {
            if (this.mCurrentListStatus == ListStatus.LS_ERROR || this.mCurrentListStatus == ListStatus.LS_EMPTY) {
                setListViewStatus(ListStatus.LS_LOADING);
            }
            if (this.mPageInteraction != null) {
                this.mPageInteraction.a();
            }
        }
    }

    private void updateListStatus() {
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            setListViewStatus(ListStatus.LS_EMPTY);
            return;
        }
        setListViewStatus(ListStatus.LS_SUCCESS);
        ebr.a(true).postDelayed(new Runnable() {
            public final void run() {
                RealBusPositionArroundPanelView.this.setScrollAtTop(RealBusPositionArroundPanelView.this.isListViewSrollTop());
            }
        }, 200);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setListViewStatus(com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView.ListStatus r5) {
        /*
            r4 = this;
            com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView$ListStatus r0 = r4.mCurrentListStatus
            if (r0 != r5) goto L_0x0005
            return
        L_0x0005:
            r0 = 1
            r4.mCurrentListStatus = r5
            int[] r1 = com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView.AnonymousClass7.a
            int r5 = r5.ordinal()
            r5 = r1[r5]
            r1 = 0
            r2 = 8
            r3 = 0
            switch(r5) {
                case 1: goto L_0x00c5;
                case 2: goto L_0x0078;
                case 3: goto L_0x0028;
                default: goto L_0x0017;
            }
        L_0x0017:
            android.widget.ListView r5 = r4.mListView
            r5.setVisibility(r3)
            android.widget.RelativeLayout r5 = r4.mStatusLayout
            r5.setVisibility(r2)
            android.widget.RelativeLayout r5 = r4.mStatusClickArea
            r5.setOnClickListener(r1)
            goto L_0x00e9
        L_0x0028:
            com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusAroundAdapter r5 = r4.mAdapter
            int r5 = r5.getCount()
            if (r5 != 0) goto L_0x00e9
            android.widget.ListView r5 = r4.mListView
            r5.setVisibility(r2)
            android.widget.RelativeLayout r5 = r4.mStatusLayout
            r5.setVisibility(r3)
            android.widget.ProgressBar r5 = r4.rt_list_refresh_progress
            r5.setVisibility(r2)
            android.widget.TextView r5 = r4.listview_status_loadingtext
            r5.setVisibility(r2)
            android.widget.RelativeLayout r5 = r4.mStatusClickArea
            android.view.View$OnClickListener r0 = r4.mRefreshClickHandler
            r5.setOnClickListener(r0)
            android.widget.ImageView r5 = r4.listview_status_img
            r5.setVisibility(r3)
            android.widget.TextView r5 = r4.listview_status_text
            r5.setVisibility(r3)
            android.widget.ImageView r5 = r4.listview_status_img
            android.content.Context r0 = r4.getContext()
            android.content.res.Resources r0 = r0.getResources()
            int r1 = com.autonavi.minimap.R.drawable.rt_list_data_empty
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r5.setImageDrawable(r0)
            android.widget.TextView r5 = r4.listview_status_text
            android.content.Context r0 = r4.getContext()
            int r1 = com.autonavi.minimap.R.string.route_rt_no_around_bus_data
            java.lang.String r0 = r0.getString(r1)
            r5.setText(r0)
            goto L_0x00e8
        L_0x0078:
            android.widget.ListView r5 = r4.mListView
            r5.setVisibility(r2)
            android.widget.RelativeLayout r5 = r4.mStatusLayout
            r5.setVisibility(r3)
            android.widget.ProgressBar r5 = r4.rt_list_refresh_progress
            r5.setVisibility(r2)
            android.widget.TextView r5 = r4.listview_status_loadingtext
            r5.setVisibility(r2)
            android.widget.RelativeLayout r5 = r4.mStatusClickArea
            android.view.View$OnClickListener r0 = r4.mRefreshClickHandler
            r5.setOnClickListener(r0)
            android.widget.ImageView r5 = r4.listview_status_img
            r5.setVisibility(r3)
            android.widget.TextView r5 = r4.listview_status_text
            r5.setVisibility(r3)
            android.widget.ImageView r5 = r4.listview_status_img
            android.content.Context r0 = r4.getContext()
            android.content.res.Resources r0 = r0.getResources()
            int r1 = com.autonavi.minimap.R.drawable.rt_list_data_error
            android.graphics.drawable.Drawable r0 = r0.getDrawable(r1)
            r5.setImageDrawable(r0)
            android.widget.TextView r5 = r4.listview_status_text
            android.content.Context r0 = r4.getContext()
            int r1 = com.autonavi.minimap.R.string.route_rt_load_fail
            java.lang.String r0 = r0.getString(r1)
            r5.setText(r0)
            com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusAroundAdapter r5 = r4.mAdapter
            r5.clearData()
            goto L_0x00e8
        L_0x00c5:
            android.widget.ListView r5 = r4.mListView
            r5.setVisibility(r2)
            android.widget.RelativeLayout r5 = r4.mStatusLayout
            r5.setVisibility(r3)
            android.widget.ProgressBar r5 = r4.rt_list_refresh_progress
            r5.setVisibility(r3)
            android.widget.TextView r5 = r4.listview_status_loadingtext
            r5.setVisibility(r3)
            android.widget.RelativeLayout r5 = r4.mStatusClickArea
            r5.setOnClickListener(r1)
            android.widget.ImageView r5 = r4.listview_status_img
            r5.setVisibility(r2)
            android.widget.TextView r5 = r4.listview_status_text
            r5.setVisibility(r2)
        L_0x00e8:
            r0 = 0
        L_0x00e9:
            android.view.View r5 = r4.rt_bus_list_toggle
            if (r0 == 0) goto L_0x00ee
            goto L_0x00ef
        L_0x00ee:
            r3 = 4
        L_0x00ef:
            r5.setVisibility(r3)
            com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout r5 = r4.mSlidingLayout
            r5.setDragEnabled(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView.setListViewStatus(com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView$ListStatus):void");
    }
}
