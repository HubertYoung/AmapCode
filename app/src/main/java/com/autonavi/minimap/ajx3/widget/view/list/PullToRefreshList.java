package com.autonavi.minimap.ajx3.widget.view.list;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.OnPullListener;

@SuppressLint({"ViewConstructor"})
public class PullToRefreshList extends PullToRefreshBase<AjxList> implements IPageLifeCircleView, ViewExtension, OnPullListener {
    private boolean mAdapterUpdateDuringMeasure;
    private PullToRefreshListProperty mProperty;
    private AjxList mRecyclerView;

    public void onNewIntent() {
    }

    public void onPageResume() {
    }

    public void onPageStop() {
    }

    public void onPull(int i, int i2) {
    }

    public PullToRefreshList(@NonNull IAjxContext iAjxContext) {
        this(iAjxContext, 1);
    }

    public PullToRefreshList(@NonNull IAjxContext iAjxContext, int i) {
        super(iAjxContext);
        this.mRecyclerView = new AjxList(this, iAjxContext, i);
        this.mRecyclerView.setMotionEventSplittingEnabled(false);
        this.mProperty = new PullToRefreshListProperty(this, this.mRecyclerView, iAjxContext);
        init(false);
        this.mProperty.updateRefresh();
    }

    public boolean isUpdatingUI() {
        return this.mAdapterUpdateDuringMeasure;
    }

    public void askToUpdate() {
        this.mAdapterUpdateDuringMeasure = true;
    }

    public void updateFinished() {
        this.mAdapterUpdateDuringMeasure = false;
    }

    public void scrollBy(String str, int i, int i2, int i3, long j) {
        this.mProperty.scrollBy(str, i2, i3, j);
    }

    /* access modifiers changed from: protected */
    public AjxList createRefreshableView() {
        return this.mRecyclerView;
    }

    public boolean isReadyForPullEnd() {
        return this.mRecyclerView.isLastItemVisible();
    }

    public boolean isReadyForPullStart() {
        return this.mRecyclerView.isFirstItemVisible();
    }

    public void setAdapter(BaseListAdapter baseListAdapter) {
        this.mRecyclerView.setAdapter(baseListAdapter);
    }

    public BaseListAdapter getAdapter() {
        return this.mRecyclerView.getAdapter();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
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
        boolean z5;
        if (str.equals("scrollTop")) {
            this.mProperty.updateAttribute(str, obj);
            z5 = false;
        } else {
            z5 = z;
        }
        this.mProperty.updateAttribute(str, obj, z5, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public int computeVerticalScrollOffset() {
        return this.mRecyclerView.computeVerticalScrollOffset();
    }

    public void scrollBy(int i, int i2) {
        this.mRecyclerView.scrollBy(i, i2);
    }

    public int computeVerticalScrollRange() {
        return this.mRecyclerView.computeVerticalScrollRange();
    }

    public int computeVerticalScrollExtent() {
        return this.mRecyclerView.computeVerticalScrollExtent();
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public View findTouchChild(float f, float f2) {
        float scrollX = f + ((float) getScrollX());
        float scrollY = f2 + ((float) getScrollY());
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (scrollX >= ((float) childAt.getLeft()) && scrollX <= ((float) childAt.getRight()) && scrollY >= ((float) childAt.getTop()) && scrollY <= ((float) childAt.getBottom())) {
                return childAt;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        updateFinished();
        if (this.mProperty != null) {
            this.mProperty.onDetachedFromWindow();
        }
    }

    public void onPageDestroy() {
        updateFinished();
        if (this.mProperty != null) {
            this.mProperty.onPageDestroy();
        }
    }
}
