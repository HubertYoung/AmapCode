package com.autonavi.minimap.route.ride.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class RideHistoryLayoutManager extends LinearLayoutManager {
    private int[] a = new int[2];

    public RideHistoryLayoutManager(Context context) {
        super(context);
    }

    public void onMeasure(Recycler recycler, State state, int i, int i2) {
        View view;
        int i3;
        super.onMeasure(recycler, state, i, i2);
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
        int[] iArr = this.a;
        try {
            view = recycler.b(0);
        } catch (Exception unused) {
            view = null;
        }
        if (view != null) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            view.measure(ViewGroup.getChildMeasureSpec(makeMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width), ViewGroup.getChildMeasureSpec(makeMeasureSpec2, getPaddingTop() + getPaddingBottom(), layoutParams.height));
            iArr[0] = view.getMeasuredWidth();
            iArr[1] = view.getMeasuredHeight();
            recycler.a(view);
        }
        int i4 = this.a[0];
        if (getOrientation() == 0) {
            i3 = 1;
        } else {
            i3 = getItemCount();
        }
        int i5 = i3 * this.a[1];
        if (getOrientation() == 0) {
            if (mode == Integer.MIN_VALUE || mode == 1073741824) {
                i4 = size;
            }
        } else if (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) {
            i5 = size2;
        }
        setMeasuredDimension(i4, i5);
    }
}
