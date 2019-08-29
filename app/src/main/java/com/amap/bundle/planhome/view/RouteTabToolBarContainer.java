package com.amap.bundle.planhome.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.planhome.view.RouteToolboxView.c;
import com.amap.bundle.planhome.view.RouteToolboxView.e;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.draggable.OnItemClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteTabToolBarContainer extends RelativeLayout implements c, OnItemClickListener {
    RouteType mCurrentType;
    private AnimatorListener mDisAnimationListener;
    /* access modifiers changed from: private */
    public boolean mIsDoingAnimation;
    private boolean mIsShow;
    a mListener;
    View mMask;
    List<e> mRouteList;
    Map<RouteType, e> mRouteMap;
    private AnimatorListener mShowAnimationListener;
    RouteToolboxView mToolbarView;

    public interface a {
        void a(RouteType routeType);

        void a(RouteType[] routeTypeArr);
    }

    public RouteTabToolBarContainer(Context context) {
        this(context, null);
    }

    public RouteTabToolBarContainer(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteTabToolBarContainer(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsDoingAnimation = false;
        LayoutInflater.from(context).inflate(R.layout.plan_route_tab_tool_bar_container_layout, this, true);
        this.mMask = findViewById(R.id.route_widget_mask);
        this.mToolbarView = (RouteToolboxView) findViewById(R.id.route_input_tool_bar);
        this.mRouteMap = new HashMap();
        initListener();
    }

    public void setMarginTop(int i) {
        LayoutParams layoutParams = (LayoutParams) this.mToolbarView.getLayoutParams();
        if (i > 0) {
            layoutParams.topMargin = i;
        }
        requestLayout();
    }

    public void setVisibility(int i) {
        if (!this.mIsDoingAnimation) {
            this.mIsShow = i == 0;
            if (this.mIsShow) {
                showAnimation(this.mShowAnimationListener);
                super.setVisibility(i);
                return;
            }
            dismissAnimation(this.mDisAnimationListener);
        }
    }

    /* access modifiers changed from: private */
    public void setGone() {
        super.setVisibility(8);
    }

    private void showAnimation(AnimatorListener animatorListener) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_x_offset);
        FastOutSlowInInterpolator fastOutSlowInInterpolator = new FastOutSlowInInterpolator();
        float f = (float) dimensionPixelSize;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mToolbarView, "translationX", new float[]{(((float) agn.a(getContext(), 244.0f)) / 2.0f) - f, 0.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mToolbarView, "translationY", new float[]{((-((float) agn.a(getContext(), 298.0f))) / 2.0f) + f, 0.0f});
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mToolbarView, "scaleX", new float[]{0.0f, 1.0f});
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mToolbarView, "scaleY", new float[]{0.0f, 1.0f});
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.mToolbarView, "alpha", new float[]{0.0f, 1.0f});
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this.mMask, "alpha", new float[]{0.0f, 1.0f});
        ofFloat.setDuration(300);
        ofFloat2.setDuration(300);
        ofFloat3.setDuration(300);
        ofFloat4.setDuration(300);
        ofFloat5.setDuration(300);
        ofFloat6.setDuration(300);
        ofFloat.setInterpolator(fastOutSlowInInterpolator);
        ofFloat2.setInterpolator(fastOutSlowInInterpolator);
        ofFloat3.setInterpolator(fastOutSlowInInterpolator);
        ofFloat4.setInterpolator(fastOutSlowInInterpolator);
        ofFloat5.setInterpolator(fastOutSlowInInterpolator);
        ofFloat6.setInterpolator(fastOutSlowInInterpolator);
        ArrayList arrayList = new ArrayList();
        arrayList.add(ofFloat);
        arrayList.add(ofFloat2);
        arrayList.add(ofFloat3);
        arrayList.add(ofFloat4);
        arrayList.add(ofFloat5);
        arrayList.add(ofFloat6);
        AnimatorSet animatorSet = new AnimatorSet();
        ofFloat.addListener(animatorListener);
        animatorSet.playTogether(arrayList);
        animatorSet.setInterpolator(fastOutSlowInInterpolator);
        animatorSet.start();
    }

    private void dismissAnimation(AnimatorListener animatorListener) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_x_offset);
        float f = (float) dimensionPixelSize;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mToolbarView, "translationX", new float[]{0.0f, (((float) agn.a(getContext(), 244.0f)) / 2.0f) - f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mToolbarView, "translationY", new float[]{((-((float) agn.a(getContext(), 298.0f))) / 2.0f) + f});
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mToolbarView, "scaleX", new float[]{1.0f, 0.0f});
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mToolbarView, "scaleY", new float[]{1.0f, 0.0f});
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.mToolbarView, "alpha", new float[]{1.0f, 0.0f});
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this.mMask, "alpha", new float[]{1.0f, 0.0f});
        ofFloat.addListener(animatorListener);
        ofFloat.setDuration(300);
        ofFloat2.setDuration(300);
        ofFloat3.setDuration(300);
        ofFloat5.setDuration(300);
        ofFloat6.setDuration(300);
        ArrayList arrayList = new ArrayList();
        arrayList.add(ofFloat);
        arrayList.add(ofFloat2);
        arrayList.add(ofFloat3);
        arrayList.add(ofFloat4);
        arrayList.add(ofFloat5);
        arrayList.add(ofFloat6);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    public void setOnTabStatusListener(a aVar) {
        this.mListener = aVar;
    }

    private void initListener() {
        this.mShowAnimationListener = new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
                RouteTabToolBarContainer.this.mIsDoingAnimation = true;
            }

            public final void onAnimationEnd(Animator animator) {
                RouteTabToolBarContainer.this.mIsDoingAnimation = false;
            }
        };
        this.mDisAnimationListener = new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
                RouteTabToolBarContainer.this.mIsDoingAnimation = true;
            }

            public final void onAnimationEnd(Animator animator) {
                RouteTabToolBarContainer.this.setGone();
                RouteTabToolBarContainer.this.mIsDoingAnimation = false;
            }
        };
        this.mMask.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RouteTabToolBarContainer.this.setVisibility(8);
            }
        });
        this.mToolbarView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RouteTabToolBarContainer.this.setVisibility(8);
            }
        });
        this.mToolbarView.setOnItemDragedChangedListener(this);
        this.mToolbarView.setOnItemClickListener(this);
    }

    public void updateRouteTypes(RouteType[] routeTypeArr) {
        if (routeTypeArr != null && routeTypeArr.length != 0) {
            this.mRouteList = new ArrayList();
            for (RouteType routeType : routeTypeArr) {
                e eVar = this.mRouteMap.get(routeType);
                if (eVar == null) {
                    eVar = getRouteItem(routeType);
                    this.mRouteMap.put(routeType, eVar);
                }
                if (eVar != null) {
                    eVar.d = false;
                    this.mRouteList.add(eVar);
                }
            }
            if (this.mToolbarView != null) {
                this.mToolbarView.updateData(this.mRouteList);
            }
        }
    }

    public void setTabHighlight(RouteType routeType, boolean z) {
        if (routeType != null) {
            if (z) {
                this.mCurrentType = routeType;
            }
            e eVar = this.mRouteMap.get(routeType);
            if (!(this.mToolbarView == null || eVar == null)) {
                this.mToolbarView.setTabSelected(eVar, z);
            }
        }
    }

    private e getRouteItem(RouteType routeType) {
        e eVar = null;
        if (routeType == null) {
            return null;
        }
        switch (routeType) {
            case TAXI:
                eVar = new e(routeType, "打车", R.drawable.route_toolbox_item_didi_selector);
                break;
            case FREERIDE:
                eVar = new e(routeType, "顺风车", R.drawable.free_ride_toolbox_item_selector);
                break;
            case CAR:
                eVar = new e(routeType, "驾车", R.drawable.route_toolbox_item_drive_selector);
                break;
            case BUS:
                eVar = new e(routeType, "公交", R.drawable.route_toolbox_item_bus_selector);
                break;
            case RIDE:
                eVar = new e(routeType, "骑行", R.drawable.route_toolbox_item_ride_selector);
                break;
            case ONFOOT:
                eVar = new e(routeType, "步行", R.drawable.route_toolbox_item_walk_selector);
                break;
            case TRAIN:
                eVar = new e(routeType, "火车", R.drawable.route_toolbox_item_train_selector);
                break;
            case COACH:
                eVar = new e(routeType, "客车", R.drawable.route_toolbox_item_coach_selector);
                break;
            case TRUCK:
                eVar = new e(routeType, "货车", R.drawable.route_toolbox_item_trunk_selector);
                break;
            case ETRIP:
                eVar = new e(routeType, "易行", R.drawable.route_toolbox_item_etrip_selector);
                break;
            case AIRTICKET:
                eVar = new e(routeType, "飞机", R.drawable.route_toolbox_item_airticket_selector);
                break;
            case MOTOR:
                eVar = new e(routeType, "摩托车", R.drawable.route_toolbox_item_motor_selector);
                break;
        }
        return eVar;
    }

    public void onItemChanged(ViewHolder viewHolder, int i, int i2) {
        this.mRouteList.remove(i);
        this.mRouteList.add(i2, this.mRouteList.get(i));
        this.mToolbarView.updateData(this.mRouteList);
        setTabHighlight(this.mCurrentType, true);
        RouteType[] routeTypeArr = new RouteType[this.mRouteList.size()];
        for (int i3 = 0; i3 < this.mRouteList.size(); i3++) {
            e eVar = this.mRouteList.get(i3);
            if (eVar != null) {
                routeTypeArr[i3] = eVar.a;
            }
        }
        if (this.mListener != null) {
            this.mListener.a(routeTypeArr);
        }
        String name = getClass().getName();
        StringBuilder sb = new StringBuilder("onItemChanged ");
        sb.append(i);
        sb.append("to ");
        sb.append(i2);
        eao.e(name, sb.toString());
    }

    public void onItemClick(ViewHolder viewHolder, int i) {
        if (this.mToolbarView != null) {
            this.mToolbarView.resetSelected();
            if (this.mRouteList != null && i < this.mRouteList.size() && i >= 0) {
                e eVar = this.mRouteList.get(i);
                if (eVar != null) {
                    setTabHighlight(eVar.a, true);
                    setVisibility(8);
                    if (this.mListener != null) {
                        this.mListener.a(eVar.a);
                    }
                }
            }
        }
        eao.e(getClass().getName(), "onItemClick ".concat(String.valueOf(i)));
    }

    public void onItemLongClick(ViewHolder viewHolder, int i) {
        eao.e(getClass().getName(), "onItemLongClick ".concat(String.valueOf(i)));
    }
}
