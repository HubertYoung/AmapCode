package com.autonavi.minimap.ajx3.widget.view.viewpager;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.HorizontalScrollerProperty;
import com.autonavi.minimap.ajx3.widget.property.ViewPagerProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"ViewConstructor"})
public class AjxViewPager extends ViewPager implements ViewExtension {
    private AjxPagerAdapter mAdapter;
    /* access modifiers changed from: private */
    public final IAjxContext mAjxContext;
    private boolean mIgnoreTouch = false;
    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        private int mLastPosition = -1;

        public void onPageSelected(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
            AjxViewPager ajxViewPager = AjxViewPager.this;
            ajxViewPager.mScrollLeft = (ajxViewPager.getWidth() * i) + i2;
            AjxViewPager.this.setAttribute(HorizontalScrollerProperty.SCROLL_LEFT, Float.valueOf(DimensionUtils.pixelToStandardUnit((float) AjxViewPager.this.mScrollLeft)), false, false, false, true);
            if (f == 0.0f && i2 == 0 && this.mLastPosition != i) {
                this.mLastPosition = i;
                AjxViewPager.this.mProperty.updateAttribute("currentPage", String.valueOf(i), false, true, false, false);
                AjxViewPager.this.mAjxContext.invokeJsEvent(new Builder().setEventName("onScrollEnd").setNodeId(AjxViewPager.this.mProperty.getNodeId()).addAttribute("currentPage", String.valueOf(i)).addContent("currentPage", String.valueOf(i)).build());
            }
        }

        public void onPageScrollStateChanged(int i) {
            AjxViewPager.this.mScrollState = i;
        }
    };
    /* access modifiers changed from: private */
    public final BaseProperty mProperty;
    /* access modifiers changed from: private */
    public int mScrollLeft;
    /* access modifiers changed from: private */
    public int mScrollState = 0;
    private boolean mScrollable = true;

    public AjxViewPager(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        this.mProperty = new ViewPagerProperty(this, iAjxContext);
        this.mAdapter = new AjxPagerAdapter(iAjxContext.getDomTree());
        setOnPageChangeListener(this.mOnPageChangeListener);
        setOffscreenPageLimit(1);
        setOverScrollMode(2);
    }

    public void updateOverflow(boolean z) {
        setClipChildren(z);
    }

    public void initPage(List<AjxDomNode> list) {
        this.mAdapter.initPage(list);
        setAdapter(this.mAdapter);
        Object attribute = this.mProperty.getAttribute("currentPage");
        if (attribute instanceof String) {
            setCurrentItem(StringUtils.parseInt((String) attribute), false);
        }
    }

    public void addPage(int i, AjxDomNode ajxDomNode) {
        this.mAdapter.addPage(i, ajxDomNode);
        this.mAdapter.notifyDataSetChanged();
    }

    public void removePage(int i) {
        this.mAdapter.removePage(i);
        this.mAdapter.notifyDataSetChanged();
        ArrayList arrayList = new ArrayList();
        if (this.mAdapter.getData() == null || this.mAdapter.getData().size() <= 0) {
            initPage(arrayList);
            return;
        }
        for (AjxDomNode next : this.mAdapter.getData()) {
            if (next != null) {
                next.destroyView();
                arrayList.add(next);
            }
        }
        initPage(arrayList);
    }

    public int getPositionByNodeId(long j) {
        return this.mAdapter.getPositionByNodeId(j);
    }

    public int getScrollLeft() {
        return this.mScrollLeft;
    }

    public void setIgnoreTouch(boolean z) {
        this.mIgnoreTouch = z;
    }

    public boolean needEatVerticalTouch() {
        return this.mScrollState != 0;
    }

    public void setScrollable(boolean z) {
        this.mScrollable = z;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mScrollState == 2) {
            return false;
        }
        if ((getProperty() == null || getProperty().couldHandleTouch()) && this.mScrollable && !this.mIgnoreTouch) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIgnoreTouch) {
            return false;
        }
        if ((getProperty() == null || getProperty().couldHandleTouch()) && this.mScrollable) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public View findTouchChild(float f, float f2) {
        AjxDomNode positionData = this.mAdapter.getPositionData(getCurrentItem());
        if (positionData != null) {
            return this.mAjxContext.getDomTree().findViewByNodeId(positionData.getId());
        }
        return null;
    }

    public LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }
}
