package com.autonavi.minimap.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.map.template.AbstractBaseAdapter;
import com.autonavi.minimap.R;

public class SimpleList extends LinearLayout {
    private static int InvalidDividerValue = Integer.MAX_VALUE;
    private AbstractBaseAdapter mAdapter;
    private DataSetObserver mDataSetObserver;
    private int mDivider = InvalidDividerValue;
    private float mDividerHeight = 0.0f;
    private int mDividerView = -1;
    private LayoutInflater mInflater;

    public SimpleList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInflater = LayoutInflater.from(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SimpleList);
        this.mDivider = obtainStyledAttributes.getColor(R.styleable.SimpleList_AmapDivider, InvalidDividerValue);
        this.mDividerHeight = obtainStyledAttributes.getDimension(R.styleable.SimpleList_slDividerHeight, 0.0f);
        this.mDividerView = obtainStyledAttributes.getResourceId(R.styleable.SimpleList_dividerView, -1);
        obtainStyledAttributes.recycle();
        this.mDataSetObserver = new DataSetObserver() {
            public void onChanged() {
                SimpleList.this.layoutList();
            }

            public void onInvalidated() {
                SimpleList.this.resetList();
            }
        };
    }

    public void setAdapter(AbstractBaseAdapter abstractBaseAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            resetList();
        }
        this.mAdapter = abstractBaseAdapter;
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
        }
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public final void resetList() {
        removeAllViewsInLayout();
        invalidate();
    }

    /* access modifiers changed from: private */
    public void layoutList() {
        View view;
        resetList();
        if (this.mAdapter != null) {
            int count = this.mAdapter.getCount();
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    if ((this.mDividerHeight > 0.0f || this.mDividerView > 0) && i > 0) {
                        if (this.mDividerView > 0) {
                            view = this.mInflater.inflate(this.mDividerView, null);
                        } else if (this.mDividerHeight > 0.0f) {
                            view = new View(getContext());
                            if (getOrientation() == 1) {
                                view.setLayoutParams(new LayoutParams(-1, Math.round(this.mDividerHeight)));
                            } else {
                                view.setLayoutParams(new LayoutParams(Math.round(this.mDividerHeight), -1));
                            }
                            if (this.mDivider != InvalidDividerValue) {
                                view.setBackgroundColor(this.mDivider);
                            }
                        }
                        addView(view);
                    }
                    addView(this.mAdapter.getView(i, null, this));
                }
            }
        }
    }
}
