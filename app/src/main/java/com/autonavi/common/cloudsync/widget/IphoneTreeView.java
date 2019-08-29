package com.autonavi.common.cloudsync.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class IphoneTreeView extends ExpandableListView implements OnScrollListener {
    private static final int MAX_ALPHA = 255;
    private a mAdapter;
    private float mDownX;
    private float mDownY;
    private View mHeaderView;
    private int mHeaderViewHeight;
    private boolean mHeaderViewVisible;
    private int mHeaderViewWidth;
    private int mLastFirstVisibleItem;
    private int mOldState;

    public interface a {
        void configureTreeHeader(View view, int i, int i2, int i3);

        void doRelatedActions();

        int getHeadViewClickStatus(int i);

        int getTreeHeaderState(int i, int i2);

        void onHeadViewClick(int i, int i2);
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    public IphoneTreeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOldState = -1;
        this.mLastFirstVisibleItem = 0;
        registerListener();
    }

    public IphoneTreeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IphoneTreeView(Context context) {
        this(context, null);
    }

    public void setHeaderView(View view) {
        this.mHeaderView = view;
        if (this.mHeaderView != null) {
            view.setLayoutParams(new LayoutParams(-1, -2));
            setFadingEdgeLength(0);
        }
        requestLayout();
    }

    private void registerListener() {
        setOnScrollListener(this);
    }

    private void headerViewClick() {
        int packedPositionGroup = ExpandableListView.getPackedPositionGroup(getExpandableListPosition(getFirstVisiblePosition()));
        if (this.mAdapter.getHeadViewClickStatus(packedPositionGroup) == 1) {
            collapseGroup(packedPositionGroup);
            this.mAdapter.onHeadViewClick(packedPositionGroup, 0);
        } else {
            expandGroup(packedPositionGroup);
            this.mAdapter.onHeadViewClick(packedPositionGroup, 1);
        }
        setSelectedGroup(packedPositionGroup);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mHeaderViewVisible) {
            switch (motionEvent.getAction()) {
                case 0:
                    this.mDownX = motionEvent.getX();
                    this.mDownY = motionEvent.getY();
                    if (this.mDownX <= ((float) this.mHeaderViewWidth) && this.mDownY <= ((float) this.mHeaderViewHeight)) {
                        return true;
                    }
                case 1:
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    float abs = Math.abs(x - this.mDownX);
                    float abs2 = Math.abs(y - this.mDownY);
                    if (x <= ((float) this.mHeaderViewWidth) && y <= ((float) this.mHeaderViewHeight) && abs <= ((float) this.mHeaderViewWidth) && abs2 <= ((float) this.mHeaderViewHeight)) {
                        if (this.mHeaderView != null) {
                            headerViewClick();
                        }
                        return true;
                    }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setAdapter(ExpandableListAdapter expandableListAdapter) {
        super.setAdapter(expandableListAdapter);
        this.mAdapter = (a) expandableListAdapter;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mHeaderView != null) {
            measureChild(this.mHeaderView, i, i2);
            this.mHeaderViewWidth = this.mHeaderView.getMeasuredWidth();
            this.mHeaderViewHeight = this.mHeaderView.getMeasuredHeight();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        long expandableListPosition = getExpandableListPosition(getFirstVisiblePosition());
        int packedPositionGroup = ExpandableListView.getPackedPositionGroup(expandableListPosition);
        int packedPositionChild = ExpandableListView.getPackedPositionChild(expandableListPosition);
        if (this.mAdapter != null) {
            int treeHeaderState = this.mAdapter.getTreeHeaderState(packedPositionGroup, packedPositionChild);
            if (!(this.mHeaderView == null || treeHeaderState == this.mOldState)) {
                this.mOldState = treeHeaderState;
                this.mHeaderView.layout(0, 0, this.mHeaderViewWidth, this.mHeaderViewHeight);
            }
        }
        configureHeaderView(packedPositionGroup, packedPositionChild);
    }

    public void configureHeaderView(int i, int i2) {
        int i3;
        if (this.mHeaderView != null && this.mAdapter != null && ((ExpandableListAdapter) this.mAdapter).getGroupCount() != 0) {
            int i4 = 255;
            switch (this.mAdapter.getTreeHeaderState(i, i2)) {
                case 0:
                    this.mHeaderViewVisible = false;
                    return;
                case 1:
                    this.mAdapter.configureTreeHeader(this.mHeaderView, i, i2, 255);
                    if (this.mHeaderView.getTop() != 0) {
                        this.mHeaderView.layout(0, 0, this.mHeaderViewWidth, this.mHeaderViewHeight);
                    }
                    this.mHeaderViewVisible = true;
                    return;
                case 2:
                    int bottom = getChildAt(0).getBottom();
                    int height = this.mHeaderView.getHeight();
                    if (bottom < height) {
                        i3 = bottom - height;
                        i4 = ((height + i3) * 255) / height;
                    } else {
                        i3 = 0;
                    }
                    this.mAdapter.configureTreeHeader(this.mHeaderView, i, i2, i4);
                    if (this.mHeaderView.getTop() != i3) {
                        this.mHeaderView.layout(0, i3, this.mHeaderViewWidth, this.mHeaderViewHeight + i3);
                    }
                    this.mHeaderViewVisible = true;
                    break;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mHeaderViewVisible && this.mHeaderView != null) {
            drawChild(canvas, this.mHeaderView, getDrawingTime());
        }
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        long expandableListPosition = getExpandableListPosition(i);
        configureHeaderView(ExpandableListView.getPackedPositionGroup(expandableListPosition), ExpandableListView.getPackedPositionChild(expandableListPosition));
        if (!(this.mAdapter == null || this.mLastFirstVisibleItem == i)) {
            this.mAdapter.doRelatedActions();
        }
        this.mLastFirstVisibleItem = i;
    }
}
