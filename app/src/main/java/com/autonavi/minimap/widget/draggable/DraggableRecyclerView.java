package com.autonavi.minimap.widget.draggable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class DraggableRecyclerView extends RecyclerView {
    private final float TOLERANCE = ((float) dp2px(getContext(), 20.0f));
    private DragHandler mDragHandler = null;
    private boolean mDraggable = true;
    /* access modifiers changed from: private */
    public ItemClickHandler mItemClickHandler;
    private ItemTouchHelper mItemTouchHelper = null;
    private OnItemDragedListener mOnItemDragedListener;
    private boolean mOutsideDraggable = false;

    public DraggableRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public DraggableRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public DraggableRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(final Context context) {
        this.mItemClickHandler = new ItemClickHandler(this);
        addOnItemTouchListener(new OnItemTouchListener() {
            private GestureDetector mGestureDetector = new GestureDetector(context, DraggableRecyclerView.this.mItemClickHandler);

            public void onRequestDisallowInterceptTouchEvent(boolean z) {
            }

            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                this.mGestureDetector.onTouchEvent(motionEvent);
                return false;
            }

            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                this.mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    public void setOutsideDraggable(boolean z) {
        this.mOutsideDraggable = z;
    }

    public boolean isDraggable() {
        return this.mDraggable;
    }

    public void setDraggable(boolean z) {
        this.mDraggable = z;
        if (this.mItemTouchHelper != null) {
            if (z) {
                this.mItemTouchHelper.a((RecyclerView) this);
                return;
            }
            this.mItemTouchHelper.a((RecyclerView) null);
        }
    }

    public boolean isOutsideDraggable() {
        return this.mOutsideDraggable;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mOutsideDraggable) {
            return super.onTouchEvent(motionEvent);
        }
        View findChildViewUnder = findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (findChildViewUnder == null) {
            return super.onTouchEvent(motionEvent);
        }
        float x = findChildViewUnder.getX();
        float width = ((float) findChildViewUnder.getWidth()) + x;
        float y = findChildViewUnder.getY();
        float height = ((float) findChildViewUnder.getHeight()) + y;
        float width2 = (float) getWidth();
        float height2 = (float) getHeight();
        if (x < (-this.TOLERANCE) || width > width2 + this.TOLERANCE || y < (-this.TOLERANCE) || height > height2 + this.TOLERANCE) {
            motionEvent.setAction(3);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setAdapter(Adapter adapter) {
        if (adapter instanceof DraggableListAdapter) {
            super.setAdapter(adapter);
            this.mDragHandler = new DragHandler((DraggableListAdapter) adapter);
            this.mDragHandler.setOnItemDragedListener(this.mOnItemDragedListener);
            this.mItemTouchHelper = new ItemTouchHelper(this.mDragHandler);
            if (this.mDraggable) {
                this.mItemTouchHelper.a((RecyclerView) this);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Please use DraggableListAdapter");
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickHandler.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemDragedListener(OnItemDragedListener onItemDragedListener) {
        this.mOnItemDragedListener = onItemDragedListener;
        if (this.mDragHandler != null) {
            this.mDragHandler.setOnItemDragedListener(this.mOnItemDragedListener);
        }
    }

    public int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
