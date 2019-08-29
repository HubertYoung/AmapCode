package com.autonavi.minimap.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;

public class PoiDetailSlidingView extends FrameLayout {
    private static int sTransparentH;
    private boolean hasSimulateDispatchAction;
    private boolean isTouchDownTipView;
    private boolean isTouchable;
    /* access modifiers changed from: private */
    public AmapAjxView mAmapAjxView;
    private int mInitialTouchY;
    private ViewGroup mTipsContainer;
    private int mTouchSlop;

    public PoiDetailSlidingView(Context context) {
        this(context, null, 0);
    }

    public PoiDetailSlidingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PoiDetailSlidingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.poi_detail_sliding_view_layout, this);
        this.mAmapAjxView = (AmapAjxView) findViewById(R.id.ajx_view);
        this.mAmapAjxView.setAttributeListener(new AttributeListener() {
            public final boolean handleAttr(String str, Object obj) {
                int i = 0;
                if (!"POI_TOP".equalsIgnoreCase(str)) {
                    return false;
                }
                ModulePoi modulePoi = (ModulePoi) PoiDetailSlidingView.this.mAmapAjxView.getJsModule("poi");
                if (modulePoi != null) {
                    if (obj instanceof Float) {
                        i = Math.round(((Float) obj).floatValue());
                    }
                    modulePoi.topHeightChange(i);
                }
                return true;
            }
        });
        this.mTipsContainer = (ViewGroup) findViewById(R.id.tips_container);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (sTransparentH == 0) {
            sTransparentH = agn.a(context, 31.0f);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void setTouchable(boolean z) {
        this.isTouchable = z;
    }

    private void simulateDispatchAction(MotionEvent motionEvent, int i) {
        this.mAmapAjxView.mIsFromPoiSimulate = true;
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(i);
        dispatchTouchEvent(obtain);
    }
}
