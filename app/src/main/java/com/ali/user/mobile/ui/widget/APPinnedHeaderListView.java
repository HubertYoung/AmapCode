package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;

public class APPinnedHeaderListView extends APListView {
    private PinnedHeaderAdapter a;
    private View b;
    private boolean c;
    private int d;
    private int e;

    public interface PinnedHeaderAdapter {
    }

    public APPinnedHeaderListView(Context context) {
        super(context);
    }

    public APPinnedHeaderListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APPinnedHeaderListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setPinnedHeaderView(View view) {
        this.b = view;
        if (this.b != null) {
            setFadingEdgeLength(0);
        }
        requestLayout();
    }

    public void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.a = (PinnedHeaderAdapter) listAdapter;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.b != null) {
            measureChild(this.b, i, i2);
            this.d = this.b.getMeasuredWidth();
            this.e = this.b.getMeasuredHeight();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.b != null) {
            this.b.layout(0, 0, this.d, this.e);
            configureHeaderView(getFirstVisiblePosition());
        }
    }

    public void configureHeaderView(int i) {
        if (this.b != null) {
            this.c = false;
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        try {
            if (this.c && this.b != null) {
                drawChild(canvas, this.b, getDrawingTime());
            }
        } catch (Throwable unused) {
        }
    }
}
