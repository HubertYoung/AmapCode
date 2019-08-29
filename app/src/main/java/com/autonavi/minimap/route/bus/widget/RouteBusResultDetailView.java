package com.autonavi.minimap.route.bus.widget;

import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.utils.UserReport;
import com.autonavi.minimap.route.bus.extbus.util.ViewPagerUtil.SimplePagerAdapter;
import com.autonavi.minimap.route.bus.localbus.adapter.RouteBusDetailAdapter;
import com.autonavi.minimap.route.bus.localbus.view.CirclePageIndicator;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.c;
import java.util.List;

public class RouteBusResultDetailView extends FrameLayout implements c {
    private static final int ANIMATION_DURATION = 400;
    private OnClickListener avoidDoubleClickListener = new OnClickListener() {
        public final void onClick(View view) {
            if (RouteBusResultDetailView.this.mBusResultDetailListener != null) {
                int id = view.getId();
                if (id == R.id.title_btn_left) {
                    RouteBusResultDetailView.this.mBusResultDetailListener.e();
                    return;
                }
                if (id == R.id.view_pager_item) {
                    RouteBusResultDetailView.this.mIsClicked = true;
                    RouteBusResultDetailView.this.toggleSlidingPanel();
                }
            }
        }
    };
    private ListView mBusPathListView;
    /* access modifiers changed from: private */
    public a mBusResultDetailListener;
    private CirclePageIndicator mIndicator;
    private LayoutInflater mInflater;
    /* access modifiers changed from: private */
    public boolean mIsClicked;
    private boolean mIsLocalBus;
    private OnPageChangeListener mOnPagerChangeListener = new OnPageChangeListener() {
        private int b = -1;
        private int c;
        private boolean d;

        public final void onPageScrolled(int i, float f, int i2) {
        }

        public final void onPageSelected(int i) {
            this.c = i;
            if (!this.d) {
                a();
            }
        }

        public final void onPageScrollStateChanged(int i) {
            this.d = true;
            if (i == 0) {
                this.d = false;
                a();
            }
        }

        private void a() {
            if (!(RouteBusResultDetailView.this.mPagerAdapter.getCount() == 1 || this.b == this.c)) {
                this.b = this.c;
                if (RouteBusResultDetailView.this.mBusResultDetailListener != null) {
                    RouteBusResultDetailView.this.mBusResultDetailListener.a(this.c);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public PagerAdapter mPagerAdapter;
    private b mSlidingListener;
    /* access modifiers changed from: private */
    public SlidingUpPanelLayout mSlidingUpPanelLayout;
    private long mStartTime;
    private View mSummaryDividerView;
    private View mSummaryView;
    private ViewPager mViewPager;

    public interface a {
        void a(int i);

        void e();
    }

    public interface b {
    }

    public boolean isClickPageItem() {
        return this.mIsClicked;
    }

    public RouteBusResultDetailView(Context context) {
        super(context);
    }

    public RouteBusResultDetailView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RouteBusResultDetailView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mInflater = LayoutInflater.from(getContext());
        View findViewById = findViewById(R.id.title_btn_left);
        if (findViewById != null && euk.a()) {
            findViewById.setPadding(findViewById.getPaddingLeft(), findViewById.getPaddingTop() + euk.a(getContext()), findViewById.getPaddingRight(), findViewById.getPaddingBottom());
        }
        NoDBClickUtil.a(findViewById, this.avoidDoubleClickListener);
        this.mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_view);
        this.mSlidingUpPanelLayout.addPanelSlideListener(this);
        this.mSummaryView = findViewById(R.id.summary_view);
        this.mViewPager = (ViewPager) findViewById(R.id.header_viewpager);
        this.mIndicator = (CirclePageIndicator) findViewById(R.id.header_indicator);
        this.mIndicator.setOnPageChangeListener(this.mOnPagerChangeListener);
        this.mSummaryDividerView = findViewById(R.id.summary_divider);
        this.mBusPathListView = (ListView) findViewById(R.id.bus_detail_List);
        this.mBusPathListView.setOnScrollListener(new OnScrollListener() {
            private int mScrollState;

            public void onScrollStateChanged(AbsListView absListView, int i) {
                this.mScrollState = i;
                RouteBusResultDetailView.this.mSlidingUpPanelLayout.requestLayout();
                if (this.mScrollState != 0) {
                    RouteBusResultDetailView.this.setScrollAtTop(false);
                } else {
                    RouteBusResultDetailView.this.setScrollAtTop(RouteBusResultDetailView.this.isListViewSrollTop());
                }
            }

            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                RouteBusResultDetailView.this.mSlidingUpPanelLayout.requestLayout();
            }
        });
    }

    public boolean isListViewSrollTop() {
        ListView listView = this.mBusPathListView;
        if (listView == null || listView.getChildCount() <= 0 || listView.getFirstVisiblePosition() != 0 || listView.getChildAt(0).getTop() < listView.getPaddingTop()) {
            return false;
        }
        return true;
    }

    public void setScrollAtTop(boolean z) {
        if (!z || !(this.mSlidingUpPanelLayout.getPanelState() == PanelState.EXPANDED || this.mSlidingUpPanelLayout.getPanelState() == PanelState.ANCHORED)) {
            this.mSlidingUpPanelLayout.setScrollAtTop(false, (View) null);
        } else {
            this.mSlidingUpPanelLayout.setScrollAtTop(z, (View) this.mBusPathListView);
        }
    }

    public void setBusResultDetailListener(a aVar) {
        this.mBusResultDetailListener = aVar;
    }

    public void setSlidingListener(b bVar) {
        this.mSlidingListener = bVar;
    }

    public void setPagerViews(List<View> list) {
        if (list != null && !list.isEmpty()) {
            if (this.mPagerAdapter == null) {
                this.mPagerAdapter = new SimplePagerAdapter(list);
                this.mViewPager.setAdapter(this.mPagerAdapter);
                this.mIndicator.setViewPager(this.mViewPager);
            }
            this.mIndicator.setSnap(true);
            if (list.size() == 1) {
                this.mIndicator.setVisibility(8);
            } else {
                this.mIndicator.setVisibility(0);
            }
        }
    }

    public void setSummaryDividerMargin(int i) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mSummaryDividerView.getLayoutParams();
        marginLayoutParams.leftMargin = i;
        marginLayoutParams.rightMargin = i;
    }

    public ListView getListView() {
        return this.mBusPathListView;
    }

    public void setListViewAdapter(RouteBusDetailAdapter routeBusDetailAdapter) {
        if (routeBusDetailAdapter != null) {
            this.mBusPathListView.setAdapter(routeBusDetailAdapter);
        }
    }

    public void setListViewHeader(int i) {
        if (this.mBusPathListView.getHeaderViewsCount() <= 0) {
            Context context = getContext();
            View view = new View(context);
            view.setBackgroundColor(context.getResources().getColor(R.color.bg_ea));
            LayoutParams layoutParams = new LayoutParams(-1, 1);
            layoutParams.bottomMargin = i;
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.bg_f5));
            linearLayout.addView(view, layoutParams);
            this.mBusPathListView.addHeaderView(linearLayout);
        }
    }

    public void clearListViewStatus(RouteBusDetailAdapter routeBusDetailAdapter) {
        this.mBusPathListView.setSelection(0);
        if (routeBusDetailAdapter != null) {
            routeBusDetailAdapter.clearExpandStations();
        }
    }

    public void setListViewFooter(int i) {
        if (this.mBusPathListView.getFooterViewsCount() <= 0) {
            Context context = getContext();
            View view = new View(context);
            view.setBackgroundColor(context.getResources().getColor(R.color.bg_ea));
            LayoutParams layoutParams = new LayoutParams(-1, 1);
            layoutParams.bottomMargin = i;
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.bg_f5));
            linearLayout.addView(view, layoutParams);
            this.mBusPathListView.addFooterView(linearLayout);
        }
    }

    public void setCurrentPagerItem(int i) {
        if (i >= 0 && i < this.mPagerAdapter.getCount()) {
            this.mViewPager.setCurrentItem(i, false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateCurrentPagerItemView(defpackage.dvv r3, int r4) {
        /*
            r2 = this;
            if (r3 == 0) goto L_0x003b
            if (r4 < 0) goto L_0x003b
            android.support.v4.view.PagerAdapter r0 = r2.mPagerAdapter
            int r0 = r0.getCount()
            if (r4 < r0) goto L_0x000d
            goto L_0x003b
        L_0x000d:
            android.support.v4.view.PagerAdapter r0 = r2.mPagerAdapter
            boolean r1 = r0 instanceof com.autonavi.minimap.route.bus.extbus.util.ViewPagerUtil.SimplePagerAdapter
            if (r1 == 0) goto L_0x0026
            com.autonavi.minimap.route.bus.extbus.util.ViewPagerUtil$SimplePagerAdapter r0 = (com.autonavi.minimap.route.bus.extbus.util.ViewPagerUtil.SimplePagerAdapter) r0
            java.util.List<android.view.View> r0 = r0.a
            if (r0 == 0) goto L_0x0026
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0026
            java.lang.Object r4 = r0.get(r4)
            android.view.View r4 = (android.view.View) r4
            goto L_0x0027
        L_0x0026:
            r4 = 0
        L_0x0027:
            if (r4 == 0) goto L_0x003a
            int r0 = com.autonavi.minimap.R.id.main_des
            android.view.View r4 = r4.findViewById(r0)
            android.widget.TextView r4 = (android.widget.TextView) r4
            if (r4 == 0) goto L_0x003a
            java.lang.String r3 = r3.c
            int r0 = com.autonavi.minimap.R.drawable.bus_result_item_main_des_next
            defpackage.axs.a(r4, r3, r0)
        L_0x003a:
            return
        L_0x003b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.bus.widget.RouteBusResultDetailView.updateCurrentPagerItemView(dvv, int):void");
    }

    public View createPagerItemView(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("mainDesText or subDesText is null");
        }
        View inflate = this.mInflater.inflate(R.layout.route_fragment_bus_result_map_footer, null);
        NoDBClickUtil.a(inflate, this.avoidDoubleClickListener);
        axs.a((TextView) inflate.findViewById(R.id.main_des), str, R.drawable.bus_result_item_main_des_next);
        ((TextView) inflate.findViewById(R.id.sub_des)).setText(axs.a(getContext(), str2, R.drawable.bus_result_item_sub_des_point));
        return inflate;
    }

    public void startInAnimation(AnimatorListener animatorListener) {
        View findViewById = findViewById(R.id.animation_top_view);
        View findViewById2 = findViewById(R.id.animation_bottom_view);
        if (!(findViewById == null || findViewById2 == null || !euk.a())) {
            int a2 = euk.a(getContext());
            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.height += a2;
            findViewById.setLayoutParams(layoutParams);
            findViewById2.setPadding(findViewById2.getPaddingLeft(), findViewById2.getPaddingTop() + a2, findViewById2.getPaddingRight(), findViewById2.getPaddingBottom());
        }
        if (findViewById != null) {
            if (findViewById.getHeight() == 0) {
                findViewById.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(findViewById, animatorListener) {
                    final /* synthetic */ View a;
                    final /* synthetic */ long b = 400;
                    final /* synthetic */ AnimatorListener c;

                    {
                        this.a = r3;
                        this.c = r4;
                    }

                    public final boolean onPreDraw() {
                        this.a.getViewTreeObserver().removeOnPreDrawListener(this);
                        dwi.a(this.a, this.b, this.c, "translationY", (float) (-this.a.getHeight()), 0.0f);
                        return false;
                    }
                });
            } else {
                dwi.a(findViewById, 400, animatorListener, "translationY", (float) (-findViewById.getHeight()), 0.0f);
            }
        }
        if (findViewById2 != null) {
            if (findViewById2.getHeight() == 0) {
                findViewById2.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(findViewById2, animatorListener) {
                    final /* synthetic */ View a;
                    final /* synthetic */ long b = 400;
                    final /* synthetic */ AnimatorListener c;

                    {
                        this.a = r3;
                        this.c = r4;
                    }

                    public final boolean onPreDraw() {
                        this.a.getViewTreeObserver().removeOnPreDrawListener(this);
                        dwi.a(this.a, this.b, this.c, "translationY", (float) this.a.getHeight(), 0.0f);
                        return false;
                    }
                });
                return;
            }
            dwi.a(findViewById2, 400, animatorListener, "translationY", (float) findViewById2.getHeight(), 0.0f);
        }
    }

    public void toggleSlidingPanel() {
        setSlidingPanelState(!(this.mSlidingUpPanelLayout.getPanelState() == PanelState.EXPANDED));
    }

    public void setSlidingPanelState(boolean z) {
        if (z) {
            setSlidingPanelState(PanelState.EXPANDED);
        } else {
            setSlidingPanelState(PanelState.COLLAPSED);
        }
    }

    private void setSlidingPanelState(PanelState panelState) {
        this.mSlidingUpPanelLayout.setPanelHeight(this.mSummaryView.getHeight());
        if (this.mSlidingUpPanelLayout.getPanelState() != panelState) {
            this.mSlidingUpPanelLayout.setPanelState(panelState);
        }
    }

    public PanelState getSlidingPanelState() {
        if (this.mSlidingUpPanelLayout != null) {
            return this.mSlidingUpPanelLayout.getPanelState();
        }
        return PanelState.EXPANDED;
    }

    public void onPanelSlide(View view, float f) {
        this.mSlidingUpPanelLayout.setPanelHeight(this.mSummaryView.getHeight());
        if (this.mSlidingListener != null) {
            this.mSlidingUpPanelLayout.getHeight();
            this.mSlidingUpPanelLayout.getSlideOffsetHeight();
        }
    }

    public void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
        if (this.mStartTime != 0 && panelState == PanelState.COLLAPSED && panelState2 == PanelState.EXPANDED) {
            if (this.mIsLocalBus) {
                dwj.a("P00019", UserReport.BTN_OFFLINEDATA_DOWNLOADMGR_MAPENGINEMD5ERROR, this.mStartTime, System.currentTimeMillis());
            } else {
                dwj.a("P00262", "B002", this.mStartTime, System.currentTimeMillis());
            }
            this.mStartTime = 0;
        }
    }

    public void setActionLogStartTime(long j, boolean z) {
        this.mStartTime = j;
        this.mIsLocalBus = z;
    }

    public void startOutAnimation(View view, AnimatorListener animatorListener) {
        if (!(view == null || view == null)) {
            if (view.getWidth() == 0) {
                view.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(view, animatorListener) {
                    final /* synthetic */ View a;
                    final /* synthetic */ long b = 400;
                    final /* synthetic */ AnimatorListener c;

                    {
                        this.a = r3;
                        this.c = r4;
                    }

                    public final boolean onPreDraw() {
                        this.a.getViewTreeObserver().removeOnPreDrawListener(this);
                        dwi.a(this.a, this.b, this.c, "translationX", 0.0f, (float) this.a.getWidth());
                        return false;
                    }
                });
                return;
            }
            dwi.a(view, 400, animatorListener, "translationX", 0.0f, (float) view.getWidth());
        }
    }
}
