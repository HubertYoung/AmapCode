package com.autonavi.minimap.life.common.widget.recyclerviewwrapper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class FixedGridLayoutManager extends GridLayoutManager {
    private int[] i = new int[2];
    private int j;

    public FixedGridLayoutManager(Context context, int i2) {
        super(context, i2);
        this.j = i2;
    }

    public void onMeasure(Recycler recycler, State state, int i2, int i3) {
        View view;
        int i4;
        int mode = MeasureSpec.getMode(i2);
        int mode2 = MeasureSpec.getMode(i3);
        int size = MeasureSpec.getSize(i2);
        int size2 = MeasureSpec.getSize(i3);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, 0);
        int[] iArr = this.i;
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
        int i5 = this.i[0];
        if (getItemCount() % this.j == 0) {
            i4 = getItemCount() / this.j;
        } else {
            i4 = (getItemCount() / this.j) + 1;
        }
        int i6 = i4 * this.i[1];
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            i5 = size;
        }
        if (!(mode2 == Integer.MIN_VALUE || mode2 == 1073741824)) {
            size2 = i6;
        }
        setMeasuredDimension(i5, size2);
    }
}
