package com.autonavi.minimap.ajx3.widget.view.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import com.autonavi.minimap.ajx3.widget.view.list.sticky.StickyRecyclerSectionsAdapter;
import com.uc.webview.export.extension.UCCore;

public abstract class BaseListAdapter<T extends ViewHolder> extends Adapter<T> implements StickyRecyclerSectionsAdapter {
    private ListItemAttachChangeListener itemViewListener;
    private int mAttached = -1;
    private int mDetached = -1;

    interface ListItemAttachChangeListener {
        void onItemAttachChange(int i, int i2);
    }

    public abstract int getCellTotalHeight();

    public abstract int getCellTotalHeight(int i);

    public abstract int getScrollToPosition(float f, boolean z);

    public abstract int getScrollToPosition(long j);

    public abstract int getTargetScrollOffsetY(long j);

    public abstract void removeSectionCache();

    /* access modifiers changed from: 0000 */
    public void measureView(RecyclerView recyclerView, View view) {
        int i;
        int i2;
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new LayoutParams(-2, -2));
        }
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == 1) {
                i2 = MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), UCCore.VERIFY_POLICY_QUICK);
                i = MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 0);
            } else {
                i2 = MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 0);
                i = MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), UCCore.VERIFY_POLICY_QUICK);
            }
            view.measure(ViewGroup.getChildMeasureSpec(i2, recyclerView.getPaddingLeft() + recyclerView.getPaddingRight(), view.getLayoutParams().width), ViewGroup.getChildMeasureSpec(i, recyclerView.getPaddingTop() + recyclerView.getPaddingBottom(), view.getLayoutParams().height));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
    }

    public void onViewAttachedToWindow(T t) {
        super.onViewAttachedToWindow(t);
        this.mAttached = t.getAdapterPosition();
        postChange();
    }

    public void onViewDetachedFromWindow(T t) {
        super.onViewDetachedFromWindow(t);
        this.mDetached = t.getAdapterPosition();
        postChange();
    }

    private void postChange() {
        if (this.itemViewListener != null) {
            this.itemViewListener.onItemAttachChange(this.mAttached, this.mDetached);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setListItemAttachChangeListener(ListItemAttachChangeListener listItemAttachChangeListener) {
        this.itemViewListener = listItemAttachChangeListener;
    }

    public int getAttached() {
        return this.mAttached;
    }

    public int getDetached() {
        return this.mDetached;
    }
}
