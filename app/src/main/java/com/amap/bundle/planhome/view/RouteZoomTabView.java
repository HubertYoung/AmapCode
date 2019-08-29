package com.amap.bundle.planhome.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.R;
import com.autonavi.widget.gif.GifImageView;
import java.util.ArrayList;

public class RouteZoomTabView extends LinearLayout {
    /* access modifiers changed from: private */
    public int mCurrSelectedPosition;
    private int mLastSelectedPosition;
    /* access modifiers changed from: private */
    public a mOnTabSelectedListener;
    private HorizontalScrollView mRouteTabScrollView;
    private ArrayList<RouteType> mRouteTypeList;
    private final int mScreenWidth;
    private ObjectAnimator mScrollAnimator;
    private volatile int mScrollingPos;
    private OnClickListener mTabClickListener;

    static class TabViewContainer extends RelativeLayout {
        private View mBg;
        /* access modifiers changed from: private */
        public RelativeLayout mContainer;
        private boolean mHightLight;
        private GifImageView mImageView;
        private int mImgSize;
        /* access modifiers changed from: private */
        public int mIndex;
        /* access modifiers changed from: private */
        public RouteType mRouteType;
        private TextView mTabNameView;

        public TabViewContainer(Context context) {
            this(context, null);
        }

        public TabViewContainer(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public TabViewContainer(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.mImgSize = agn.a(getContext(), 23.0f);
            LayoutInflater.from(context).inflate(R.layout.route_input_tab_container_layout, this, true);
            setGravity(17);
            this.mImageView = (GifImageView) findViewById(R.id.route_input_conbine_img);
            this.mContainer = (RelativeLayout) findViewById(R.id.rl_tab_container);
            this.mTabNameView = (TextView) findViewById(R.id.route_input_conbine_text);
            this.mTabNameView.setSingleLine();
            this.mTabNameView.getPaint().setFakeBoldText(true);
            this.mBg = findViewById(R.id.route_input_conbine_bg);
        }

        private int getIconRes(RouteType routeType) {
            switch (routeType) {
                case TAXI:
                    return R.drawable.didi_gif;
                case FREERIDE:
                    return R.drawable.free_ride_gif;
                case CAR:
                    return R.drawable.drive_gif;
                case BUS:
                    return R.drawable.bus_gif;
                case RIDE:
                    return R.drawable.ride_gif;
                case ONFOOT:
                    return R.drawable.walk_gif;
                case TRAIN:
                    return R.drawable.train_gif;
                case COACH:
                    return R.drawable.coach_gif;
                case TRUCK:
                    return R.drawable.trunk_gif;
                case ETRIP:
                    return R.drawable.etrip_gif;
                case AIRTICKET:
                    return R.drawable.airticket_gif;
                case MOTOR:
                    return R.drawable.motor_gif;
                default:
                    return R.drawable.drive_gif;
            }
        }

        /* access modifiers changed from: private */
        public void setRouteType(RouteType routeType) {
            this.mRouteType = routeType;
            if (this.mImageView != null) {
                this.mImageView.setImageResource(getIconRes(routeType));
            }
        }

        public void setSelected(boolean z) {
            super.setSelected(z);
            setHightLight(z);
        }

        private void setHightLight(boolean z) {
            Resources resources;
            int i;
            int i2 = 0;
            boolean z2 = this.mHightLight != z;
            this.mHightLight = z;
            GifImageView gifImageView = this.mImageView;
            if (!z) {
                i2 = 8;
            }
            gifImageView.setVisibility(i2);
            if (this.mHightLight) {
                if (z2) {
                    this.mImageView.play();
                }
                this.mImageView.setAlpha(1.0f);
            } else {
                this.mImageView.stop();
                this.mImageView.setAlpha(0.0f);
            }
            this.mBg.setBackgroundResource(z ? R.drawable.route_input_tab_bg : R.drawable.route_input_tab_normal_bg);
            TextView textView = this.mTabNameView;
            if (z) {
                resources = getResources();
                i = R.color.f_c_1;
            } else {
                resources = getResources();
                i = R.color.f_c_3;
            }
            textView.setTextColor(resources.getColor(i));
        }

        public void setZoomPx(int i) {
            if (i == 0) {
                this.mImageView.setVisibility(8);
                return;
            }
            this.mImageView.setVisibility(0);
            LayoutParams layoutParams = this.mImageView.getLayoutParams();
            layoutParams.width = i;
            String name = RouteZoomTabView.class.getName();
            StringBuilder sb = new StringBuilder(" TabConbineView ---> setZoomPx ");
            sb.append(i);
            sb.append(" tab ");
            sb.append(this.mRouteType);
            sb.append(" getwidth ");
            sb.append(this.mImgSize);
            sb.append(" params.width ");
            sb.append(layoutParams.width);
            sb.append("targetLength ");
            sb.append(layoutParams.width);
            eao.e(name, sb.toString());
            this.mImageView.setLayoutParams(layoutParams);
            requestLayout();
            postInvalidate();
        }

        /* access modifiers changed from: private */
        public void setText(CharSequence charSequence) {
            this.mTabNameView.setText(charSequence);
        }
    }

    public interface a {
        void a(RouteType routeType);
    }

    public RouteZoomTabView(Context context) {
        this(context, null);
    }

    public RouteZoomTabView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteZoomTabView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mScreenWidth = ags.a(getContext()).width();
        this.mCurrSelectedPosition = -1;
        this.mRouteTypeList = new ArrayList<>();
        this.mScrollingPos = 0;
        init();
    }

    public void setScrollView(HorizontalScrollView horizontalScrollView) {
        if (horizontalScrollView != null) {
            this.mRouteTabScrollView = horizontalScrollView;
        }
    }

    public int getTabPos(RouteType routeType) {
        int i = this.mScrollingPos;
        int tabCenterPos = getTabCenterPos(getTabIndex(routeType));
        if (this.mRouteTabScrollView != null) {
            int measuredWidth = this.mRouteTabScrollView.getMeasuredWidth();
            if (i > getMeasuredWidth() - measuredWidth) {
                i = getMeasuredWidth() - measuredWidth;
            }
        }
        return tabCenterPos - i;
    }

    private int getTabCenterPos(int i) {
        if (i < 0 || i > getChildCount()) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += ((ViewGroup) getChildAt(i3)).getWidth();
        }
        return i2 + (getChildAt(i).getWidth() / 2);
    }

    public void scrollToTab(int i) {
        if (this.mRouteTabScrollView != null) {
            int tabCenterPos = getTabCenterPos(i) - (this.mScreenWidth / 2);
            if (tabCenterPos < 0) {
                tabCenterPos = 0;
            }
            int scrollX = this.mRouteTabScrollView.getScrollX();
            if (scrollX != tabCenterPos || this.mScrollingPos != tabCenterPos) {
                String name = getClass().getName();
                StringBuilder sb = new StringBuilder("from ");
                sb.append(scrollX);
                sb.append(" ScrollX -- 》 ");
                sb.append(tabCenterPos);
                eao.e(name, sb.toString());
                this.mScrollingPos = tabCenterPos;
                if (this.mScrollAnimator != null && this.mScrollAnimator.isRunning()) {
                    this.mScrollAnimator.cancel();
                }
                this.mScrollAnimator = ObjectAnimator.ofInt(this.mRouteTabScrollView, "ScrollX", new int[]{scrollX, tabCenterPos});
                this.mScrollAnimator.setDuration(400);
                this.mScrollAnimator.start();
            }
        }
    }

    private void init() {
        setWillNotDraw(false);
        setOrientation(0);
        this.mTabClickListener = new OnClickListener() {
            public final void onClick(View view) {
                TabViewContainer tabViewContainer = (TabViewContainer) view;
                int access$000 = tabViewContainer.mIndex;
                if (RouteZoomTabView.this.mOnTabSelectedListener != null) {
                    if (RouteZoomTabView.this.mCurrSelectedPosition == access$000) {
                        RouteZoomTabView.this.mOnTabSelectedListener;
                        tabViewContainer.mRouteType;
                        return;
                    }
                    RouteZoomTabView.this.mOnTabSelectedListener.a(tabViewContainer.mRouteType);
                }
            }
        };
    }

    public void setSelectTab(int i) {
        if (i >= 0 && i <= this.mRouteTypeList.size()) {
            this.mLastSelectedPosition = this.mCurrSelectedPosition;
            this.mCurrSelectedPosition = i;
            int childCount = getChildCount();
            int i2 = 0;
            while (i2 < childCount) {
                getChildAt(i2).setSelected(i == i2);
                if (i == i2) {
                    setIndicatorPosition(this.mLastSelectedPosition, this.mCurrSelectedPosition);
                }
                i2++;
            }
        }
    }

    public ViewGroup getTabViewGoup(RouteType routeType) {
        getChildCount();
        if (!this.mRouteTypeList.isEmpty()) {
            for (int i = 0; i < this.mRouteTypeList.size(); i++) {
                if (routeType == this.mRouteTypeList.get(i)) {
                    ViewGroup viewGroup = (ViewGroup) getChildAt(i);
                    if (viewGroup instanceof TabViewContainer) {
                        return ((TabViewContainer) viewGroup).mContainer;
                    }
                }
            }
        }
        return null;
    }

    public RouteType getCurrentType() {
        if (this.mRouteTypeList.size() <= 0 || this.mCurrSelectedPosition >= this.mRouteTypeList.size() || this.mCurrSelectedPosition < 0) {
            return RouteType.DEFAULT;
        }
        return this.mRouteTypeList.get(this.mCurrSelectedPosition);
    }

    public RouteType[] getCurrentTypes() {
        if (this.mRouteTypeList.size() <= 0) {
            return new RouteType[0];
        }
        return (RouteType[]) this.mRouteTypeList.toArray(new RouteType[this.mRouteTypeList.size()]);
    }

    private void setIndicatorPosition(int i, int i2) {
        if (i != i2) {
            if (i == -1) {
                i = i2;
            }
            final TabViewContainer tabViewContainer = (TabViewContainer) getChildAt(i);
            final TabViewContainer tabViewContainer2 = (TabViewContainer) getChildAt(i2);
            final int a2 = agn.a(getContext(), 23.0f);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 100.0f});
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ofFloat.setDuration(200);
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int floatValue = (int) (((float) a2) * (((Float) valueAnimator.getAnimatedValue()).floatValue() / 100.0f));
                    tabViewContainer.setZoomPx(a2 - floatValue);
                    tabViewContainer2.setZoomPx(floatValue);
                }
            });
            ofFloat.start();
        }
    }

    public void setOnTabSelectedListener(a aVar) {
        this.mOnTabSelectedListener = aVar;
    }

    public int getTabIndex(RouteType routeType) {
        if (this.mRouteTypeList.size() == 0) {
            return 0;
        }
        for (int i = 0; i < this.mRouteTypeList.size(); i++) {
            if (this.mRouteTypeList.get(i) == routeType) {
                return i;
            }
        }
        return -1;
    }

    public void clearTabs() {
        this.mRouteTypeList.clear();
        removeAllViews();
    }

    public void addTab(RouteType routeType, CharSequence charSequence) {
        this.mRouteTypeList.add(routeType);
        TabViewContainer tabViewContainer = new TabViewContainer(getContext());
        tabViewContainer.mIndex = this.mRouteTypeList.size() - 1;
        tabViewContainer.setRouteType(routeType);
        tabViewContainer.setFocusable(true);
        tabViewContainer.setOnClickListener(this.mTabClickListener);
        tabViewContainer.setText(charSequence);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.height = agn.a(getContext(), 46.0f);
        layoutParams.gravity = 16;
        addView(tabViewContainer, layoutParams);
    }

    public void addTab(RouteType routeType, CharSequence charSequence, boolean z) {
        if (routeType != null && !TextUtils.isEmpty(charSequence)) {
            int i = 0;
            while (i < this.mRouteTypeList.size()) {
                if (!routeType.equals(this.mRouteTypeList.get(i))) {
                    i++;
                } else {
                    return;
                }
            }
            addTab(routeType, charSequence);
            if (z) {
                setSelectTab(getTabIndex(routeType));
            }
        }
    }
}
